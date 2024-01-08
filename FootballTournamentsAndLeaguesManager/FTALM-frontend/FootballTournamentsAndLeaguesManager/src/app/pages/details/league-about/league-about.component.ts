import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { TeamService } from 'src/app/services/teamService/team.service';
import { League } from 'src/app/models/League/league';
import { Team } from 'src/app/models/Team/team';
import { Match } from 'src/app/models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';
import { LeagueStanding } from '../../../models/LeagueStanding/leagueStanding';
import { LeagueStandingService } from '../../../services/leagueStandingService/league-standing.service';

@Component({
  selector: 'app-league-about',
  templateUrl: './league-about.component.html',
  styleUrls: ['./league-about.component.css']
})
export class LeagueAboutComponent {
  private static BLANK_TEAM_NAME: string = 'Blank Team';
  leagueId!: number;
  objectName: string = 'league';
  leagueCanBeStarted: boolean = false;
  leagueCanBeDeletedWithWarning: boolean = false;
  teams: Team[] = [];

  constructor(private route: ActivatedRoute, private dialog: MatDialog, private matchService: MatchService,
    private leagueService: LeagueService, private teamService: TeamService, private leagueStandingService: LeagueStandingService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.leagueId = params['leagueId'];
    });

    this.leagueService.getLeagueStatus(this.leagueId).subscribe(status => {
      if(status === Status.NOT_STARTED || status === Status.FINISHED)
        this.leagueCanBeDeletedWithWarning = false;
      else
        this.leagueCanBeDeletedWithWarning = true;
    });

    this.leagueService.getNumberOfTeams(this.leagueId).subscribe(result1 => {
      this.teamService.countTeamsByLeagueId(this.leagueId).subscribe(result2 => {
        if (result1 === result2)
          this.leagueCanBeStarted = true;
        else
          this.leagueCanBeStarted = true; //false; ONLY TEMPORARY!!!!!!!!!!!!!
      });
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.leagueId,
      objectName: this.objectName,
      warning: this.leagueCanBeDeletedWithWarning
    }
    });
  }

  startLeague() {

    // update league status to IN_PROGRESS
    const updatedStatus: League = { status: Status.IN_PROGRESS };
    this.leagueService.updateLeagueStatusByLeagueId(this.leagueId, updatedStatus).subscribe();

    //TODO: league can only be started once!!
    this.leagueCanBeStarted = false; // check but for now not working due to testing (result1 != result2)

    this.teamService.findAllTeamsInLeagueByLeagueId(this.leagueId).subscribe((teams: Team[]) => {

      // shuffle teams
      this.teams = this.shuffle([...teams]);

      // create league fixture
      var matches: Match[] = this.generateRoundRobinSchedule();
      this.matchService.createMatches(matches).subscribe();

      // create league standings
      var leagueStanding = this.createLeagueStanding(this.leagueId, this.teams);
      this.leagueStandingService.addLeagueStanding(leagueStanding).subscribe();

    });
  }

  createLeagueStanding(leagueId: number, teams: Team[]): LeagueStanding[] {
    let leagueStanding: LeagueStanding[] = [];
    let indexOfBlankTeam = teams.findIndex(team => team.id === null && team.name === LeagueAboutComponent.BLANK_TEAM_NAME);
    
    if(indexOfBlankTeam > -1)
      teams.splice(indexOfBlankTeam, 1);

    teams.forEach(team => {
      leagueStanding.push({
        league: {id: leagueId},
        team: {id: team ? (team.id || 0) : 0},
        matches: 0,
        points: 0,
        goalsFor: 0,
        goalsAgainst: 0,
        wins: 0,
        draws: 0,
        losses: 0
      });
    });

    return leagueStanding;
  }

  generateRoundRobinSchedule(): Match[] {
    
    if(this.teams.length % 2 !== 0)
      this.teams.push({id: null, name: LeagueAboutComponent.BLANK_TEAM_NAME});

    let numberOfRounds = this.teams.length - 1;
    let matchesPerRound = this.teams.length / 2;
    let alternate = true;
    let offsetArray = this.generateOffsetArray();

    let firstHalfSeasonFixtures = this.generateFixtures(0, numberOfRounds, matchesPerRound, alternate, offsetArray);
    let secondHalfSeasonFixtures = this.generateFixtures(this.teams.length - 1, numberOfRounds, matchesPerRound, alternate, offsetArray);
    let allFixtures = firstHalfSeasonFixtures.concat(secondHalfSeasonFixtures);

    return this.outputFixtures(allFixtures);
  }

  generateFixtures(roundNoOffset: number, numberOfRounds: number, matchesPerRound: number, alternate: boolean, offsetArray: number[]) {
    let fixtures = [];

    for (let roundNo = 1; roundNo <= numberOfRounds; roundNo++) {
      alternate = !alternate;

      let homes = this.getHomes(roundNo, this.teams.length, offsetArray, matchesPerRound);
      let aways = this.getAways(roundNo, this.teams.length, offsetArray, matchesPerRound);

      for (let matchIndex = 0; matchIndex < matchesPerRound; matchIndex++) {
        let homeOpponentId = homes[matchIndex];
        let awayOpponentId = aways[matchIndex];
        let homeOpponent = this.teams[homeOpponentId];
        let awayOpponent = this.teams[awayOpponentId];

        if (homeOpponentId === awayOpponentId) {
          console.error('Teams cannot play themselves');
        }

        if (alternate) {
          fixtures.push({
            roundNo: roundNo + roundNoOffset,
            matchNo: matchIndex,
            homeOpponentId: homeOpponentId,
            awayOpponentId: awayOpponentId,
            homeOpponent: homeOpponent,
            awayOpponent: awayOpponent,
          });
        } else {
          fixtures.push({
            roundNo: roundNo + roundNoOffset,
            matchNo: matchIndex,
            homeOpponentId: homeOpponentId,
            awayOpponentId: awayOpponentId,
            homeOpponent: awayOpponent,
            awayOpponent: homeOpponent,
          });
        }
      }
    }

    return fixtures;
  }

  outputFixtures(fixtures: any[]): Match[] {
    let roundNo = 0;
    var match: Match;
    var matches: Match[] = [];

    for (let fixtureId = 0; fixtureId < fixtures.length; fixtureId++) {
      let fixture = fixtures[fixtureId];

      if (fixture.roundNo > roundNo) {
        roundNo = fixture.roundNo;
      }

      if(fixtureId < fixtures.length/2){
        match = {
          homeTeamId: fixture.awayOpponent.id,
          awayTeamId: fixture.homeOpponent.id,
          league: {id: this.leagueId},
          homeTeamScore: null,
          awayTeamScore: null,
          matchweek: roundNo,
          round: null,
          matchProtocolCreated: false
        }
      }
      else {
        match = {
          homeTeamId: fixture.homeOpponent.id,
          awayTeamId: fixture.awayOpponent.id,
          league: {id: this.leagueId},
          homeTeamScore: null,
          awayTeamScore: null,
          matchweek: roundNo,
          round: null,
          matchProtocolCreated: false
        }
      }

      matches.push(match);
    }

    return matches;
  }

  generateOffsetArray() {
    let offsetArray = [];

    for (let i = 1; i < this.teams.length; i++) {
      offsetArray.push(i);
    }

    offsetArray = offsetArray.concat(offsetArray);
    return offsetArray;
  }

  getHomes(roundNo: number, teamsCount: number, offsetArray: number[], matchesPerRound: number) {
    let offset = teamsCount - roundNo;
    let homes = offsetArray.slice(offset, offset + matchesPerRound - 1);

    return [0, ...homes];
  }

  getAways(roundNo: number, teamsCount: number, offsetArray: number[], matchesPerRound: number) {
    let offset = teamsCount - roundNo + matchesPerRound - 1;
    let aways = offsetArray.slice(offset, offset + matchesPerRound);

    return aways.reverse();
  }

  shuffle(teams: Team[]): Team[] {
    for (let i = teams.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [teams[i], teams[j]] = [teams[j], teams[i]];
    }

    return teams;
  }
}

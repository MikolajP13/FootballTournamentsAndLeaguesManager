import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { Tournament, Type } from 'src/app/models/Tournament/tournament';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { Team } from 'src/app/models/Team/team';
import { StandingsUtils } from 'src/app/utils/StandingUtils';
import { TournamentStandingService } from 'src/app/services/tournamentStandingService/tournament-standing.service';
import { TournamentStanding } from 'src/app/models/TournamentStanding/tournamentStanding';
import { Match } from '../../../models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';

@Component({
  selector: 'app-tournament-about',
  templateUrl: './tournament-about.component.html',
  styleUrls: ['./tournament-about.component.css']
})
export class TournamentAboutComponent {
  tournamentId!: number;
  objectName: string = 'tournament';
  tournament!: Tournament;
  teams!: Team[];
  tournamentCanBeStarted: boolean = false;
  tournamentCanBeDeletedWithWarning: boolean = false;

  constructor(private route: ActivatedRoute, private dialog: MatDialog, private teamService: TeamService, private matchService: MatchService,
    private tournamentService: TournamentService, private tournamentStandingService: TournamentStandingService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.tournamentId = params['tournamentId'];
    });

    this.tournamentService.findTournamentById(this.tournamentId).subscribe(tournament => {
      this.tournament = tournament;
    });

    this.tournamentService.getTournamentStatus(this.tournamentId).subscribe(status => {
      if(status === Status.FINISHED || status === Status.NOT_STARTED)
        this.tournamentCanBeDeletedWithWarning = false;
      else
        this.tournamentCanBeDeletedWithWarning = true;
    });

    this.tournamentService.getNumberOfTeams(this.tournamentId).subscribe(result1 => {
      this.teamService.countTeamsByTournamentId(this.tournamentId).subscribe(result2 => {
        if (result1 === result2)
          this.tournamentCanBeStarted = true;
        else
          this.tournamentCanBeStarted = true; //false; ONLY TEMPORARY!!!!!!!!!!!!!
      });
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.tournamentId,
      objectName: this.objectName,
      warning: this.tournamentCanBeDeletedWithWarning,
      position: {top: '-200px'}
    }
    });
  }

  startTournament() {

    // update tournament status to IN_PROGRESS
    const updatedStatus: Tournament = {
      status: Status.IN_PROGRESS
    }

    //TODO: tournament can only be started once!!
    // this.tournamentCanBeStarted = false;

    this.tournamentService.updateTournamentStatusByTournamentId(this.tournamentId, updatedStatus).subscribe();

    this.teamService.findAllTeamsInTournamentByTournamentId(this.tournamentId).subscribe((teams: Team[]) =>{
      var tournamentMatches: Match[] = [];

      // shuffle teams
      this.teams = StandingsUtils.shuffle(teams);

      // if tournament type is group and knockout then create league standings and group matches
      if(this.tournament.type?.replaceAll('_', ' ') == Type.GROUP_AND_KNOCKOUT.valueOf().toUpperCase()) {
        let tournamentStanding: TournamentStanding[] = [];
        let tournamentStageGroupMatches: Match[] = [];
        const numberOfGroups = Math.floor(this.teams.length/4);

        for (let i = 0; i < numberOfGroups; i++) {
          for (let j = 0; j < 4; j++) {
            tournamentStanding.push({
              tournament: {id: this.tournamentId},
              groupId: i + 1,
              team: {id: this.teams[j] ? (this.teams[j].id || 0) : 0},
              matches: 0,
              points: 0,
              goalsFor: 0,
              goalsAgainst: 0,
              wins: 0,
              draws: 0,
              losses: 0
            });
          }

          // create tournament group fixtures => function(teams, competitionId, isTournament, isRematch)
          var groupMatches: Match[] = StandingsUtils.generateRoundRobinSchedule(this.teams.splice(0, 4), this.tournamentId, true, i+1, false);
          tournamentStageGroupMatches = tournamentStageGroupMatches.concat(groupMatches);
        }
        
        tournamentMatches = tournamentStageGroupMatches;
        this.tournamentStandingService.addTournamentStanding(tournamentStanding).subscribe();

      } else { 
        // standard tournament mode start with matches in round number 1
        tournamentMatches = StandingsUtils.generateTournamentPairs(this.teams, this.tournamentId);
      }

      this.matchService.createMatches(tournamentMatches).subscribe();

    });
  }

}

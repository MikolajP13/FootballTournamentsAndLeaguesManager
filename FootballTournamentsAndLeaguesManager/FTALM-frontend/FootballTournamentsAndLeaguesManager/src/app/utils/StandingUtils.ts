import { Match } from "../models/Match/match";
import { Team } from "../models/Team/team";
import { LeagueAboutComponent } from "../pages/details/league-about/league-about.component";

export class StandingsUtils {
  private static BLANK_TEAM_NAME: string = 'Blank Team';

  static shuffle(teams: Team[]): Team[] {
    for (let i = teams.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [teams[i], teams[j]] = [teams[j], teams[i]];
    }
    return teams;
  }

  static generateRoundRobinSchedule(teams: Team[], competitonId: number, isTournament: boolean, groupId: number, isRematch: boolean): Match[] {
    
    if(teams.length % 2 !== 0)
      teams.push({id: null, name: StandingsUtils.BLANK_TEAM_NAME});

    let numberOfRounds = teams.length - 1;
    let matchesPerRound = teams.length / 2;
    let alternate = true;
    let offsetArray = StandingsUtils.generateOffsetArray(teams);

    let firstHalfSeasonFixtures = StandingsUtils.generateFixtures(0, numberOfRounds, matchesPerRound,
      alternate, offsetArray, teams);

    let allFixtures = firstHalfSeasonFixtures;
    
    if(isRematch){
      let secondHalfSeasonFixtures = StandingsUtils.generateFixtures(teams.length - 1, numberOfRounds, 
        matchesPerRound, alternate, offsetArray, teams);

      allFixtures = firstHalfSeasonFixtures.concat(secondHalfSeasonFixtures);
    }

    return StandingsUtils.outputFixtures(allFixtures, competitonId, isTournament, groupId);
  }

  static generateFixtures(roundNoOffset: number, numberOfRounds: number, matchesPerRound: number, 
    alternate: boolean, offsetArray: number[], teams: Team[]) {

    let fixtures = [];

    for (let roundNo = 1; roundNo <= numberOfRounds; roundNo++) {
      alternate = !alternate;

      let homes = this.getHomes(roundNo, teams.length, offsetArray, matchesPerRound);
      let aways = this.getAways(roundNo, teams.length, offsetArray, matchesPerRound);

      for (let matchIndex = 0; matchIndex < matchesPerRound; matchIndex++) {
        let homeOpponentId = homes[matchIndex];
        let awayOpponentId = aways[matchIndex];
        let homeOpponent = teams[homeOpponentId];
        let awayOpponent = teams[awayOpponentId];

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

  static outputFixtures(fixtures: any[], competitionId: number, isTournament: boolean, groupId: number): Match[] {
    let roundNo = 0;
    var match: Match;
    var matches: Match[] = [];

    for (let fixtureId = 0; fixtureId < fixtures.length; fixtureId++) {
      let fixture = fixtures[fixtureId];

      if (fixture.roundNo > roundNo) {
        roundNo = fixture.roundNo;
      }

      if(fixtureId < fixtures.length/2){
        if(isTournament) {
          match = {
            homeTeamId: fixture.awayOpponent.id,
            awayTeamId: fixture.homeOpponent.id,
            tournament: {id: competitionId},
            homeTeamScore: null,
            awayTeamScore: null,
            matchweek: isTournament ? groupId: roundNo,
            round: isTournament ? 0 : null,
            matchProtocolCreated: false
          }
        }
        else {
          match = {
            homeTeamId: fixture.awayOpponent.id,
            awayTeamId: fixture.homeOpponent.id,
            league: {id: competitionId},
            homeTeamScore: null,
            awayTeamScore: null,
            matchweek: isTournament ? groupId: roundNo,
            round: isTournament ? 0 : null,
            matchProtocolCreated: false
          }
        }
        
      }
      else {
        if(isTournament) {
          match = {
            homeTeamId: fixture.homeOpponent.id,
            awayTeamId: fixture.awayOpponent.id,
            tournament: {id: competitionId},
            homeTeamScore: null,
            awayTeamScore: null,
            matchweek: isTournament ? groupId: roundNo,
            round: isTournament ? 0 : null,
            matchProtocolCreated: false
          }
        }
        else {
          match = {
            homeTeamId: fixture.homeOpponent.id,
            awayTeamId: fixture.awayOpponent.id,
            league: {id: competitionId},
            homeTeamScore: null,
            awayTeamScore: null,
            matchweek: isTournament ? groupId: roundNo,
            round: isTournament ? 0 : null,
            matchProtocolCreated: false
          }
        }
      }

      matches.push(match);
    }

    return matches;
  }

  static generateOffsetArray(teams: Team[]) {
    let offsetArray = [];

    for (let i = 1; i < teams.length; i++) {
      offsetArray.push(i);
    }

    offsetArray = offsetArray.concat(offsetArray);
    return offsetArray;
  }

  static getHomes(roundNo: number, teamsCount: number, offsetArray: number[], matchesPerRound: number) {
    let offset = teamsCount - roundNo;
    let homes = offsetArray.slice(offset, offset + matchesPerRound - 1);

    return [0, ...homes];
  }

  static getAways(roundNo: number, teamsCount: number, offsetArray: number[], matchesPerRound: number) {
    let offset = teamsCount - roundNo + matchesPerRound - 1;
    let aways = offsetArray.slice(offset, offset + matchesPerRound);

    return aways.reverse();
  }

  static generateTournamentPairs(teams: Team[], tournamentId: number): Match[] {
    var tournamentBracketMatches: Match[] = [];
    var match: Match;

    if(teams.length % 2 !== 0)
      teams.push({id: null, name: StandingsUtils.BLANK_TEAM_NAME});

    for (let i = 0; i < teams.length; i += 2) {
      tournamentBracketMatches.push(match = {
        homeTeamId: teams[i].id || undefined,
        awayTeamId: teams[i+1].id || undefined,
        tournament: {id: tournamentId},
        homeTeamScore: null,
        awayTeamScore: null,
        matchweek: undefined,
        round: 1,
        matchProtocolCreated: false
      });
    }
  
    return tournamentBracketMatches;
  }
}
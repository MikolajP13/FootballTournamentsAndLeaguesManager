import { Status } from "../TournamentLeagueBase/tournamentLeagueBase"

export class Tournament {
  id?: number;
  user?: {
    id?: number;
  }
  name?: string;
  startDate?: string;
  endDate?: string;
  numberOfTeams?: number;
  status?: Status;
  type?: Type;
}

export class TournamentTeam {
  teamId?: number;
  tournamentId?: number;
}

export enum Type {
  SINGLE_ELIMINATION = "Single Elimination",
  GROUP_AND_KNOCKOUT = "Group and Knockout",
  // DOUBLE_ELIMINATION = "Double Elimination" next version feature
}
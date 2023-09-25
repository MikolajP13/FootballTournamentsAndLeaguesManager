import { Status } from "../TournamentLeagueBase/tournamentLeagueBase"

export class League {
  id?: number;
  user?: {
    id?: number;
  }
  name?: string;
  startDate?: string;
  numberOfTeams!: number;
  status?: Status;
  type?: Type;
}

export class LeagueTeam {
  teamId?: number;
  leagueId?: number;
}

export enum Type {
  STANDARD_MODE = "Standard Mode",
  SPLIT_MODE = "Split Mode"
}
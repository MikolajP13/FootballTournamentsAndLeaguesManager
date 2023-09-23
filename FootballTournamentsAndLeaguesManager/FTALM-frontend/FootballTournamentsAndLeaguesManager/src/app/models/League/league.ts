import { Status } from "../TournamentLeagueBase/tournamentLeagueBase"

export class League {
  id?: number;
  user?: {
    id?: number;
  }
  name?: string;
  startDate?: string;
  numberOfTeams?: number;
  status?: Status;
  type?: Type;
}

export enum Type {
  STANDARD_MODE = "Standard Mode",
  SPLIT_MODE = "Split Mode"
}
import { Status } from "../TournamentLeagueBase/tournamentLeagueBase"

export class Tournament {
  id?: number;
  user?: {
    id?: number;
  }
  name?: string;
  startDate?: string;
  numberOfTeams?: number;
  status?: Status;
}
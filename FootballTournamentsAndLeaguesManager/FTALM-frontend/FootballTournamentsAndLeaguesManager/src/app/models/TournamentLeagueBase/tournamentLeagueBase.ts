export class TournamentLeagueBase {
  id?: number;
  name?: string;
  numberOfTeams?: number;
  startDate!: Date;
  endDate!: Date;
  status?: Status;
  competitionType?: CompetitionType;
}

export enum Status {
  NOT_STARTED = "NOT_STARTED", IN_PROGRESS = "IN_PROGRESS", FINISHED = "FINISHED"
}

export enum CompetitionType {
  LEAGUE = "league", TOURNAMENT = "tournament"
}
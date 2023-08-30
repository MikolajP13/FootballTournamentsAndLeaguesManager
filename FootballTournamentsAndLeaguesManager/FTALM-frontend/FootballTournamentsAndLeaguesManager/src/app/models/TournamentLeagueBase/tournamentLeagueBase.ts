export class TournamentLeagueBase {
  id?: number;
  name?: string;
  numberOfTeams?: number;
  startDate!: Date;
  status?: Status;
  competitionType?: CompetitionType;
  CompetitionType: any;
}

enum Status {
  NOT_STARTED, IN_PROGRESS, FINISHED
}

export enum CompetitionType {
  LEAGUE = "league", TOURNAMENT = "tournament"
}
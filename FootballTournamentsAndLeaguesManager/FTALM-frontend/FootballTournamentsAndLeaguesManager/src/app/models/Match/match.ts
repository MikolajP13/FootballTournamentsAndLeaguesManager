export class Match {
  id?: number
  date?: Date
  homeTeam?: {
    id: number
  }
  homeTeamName?: string
  awayTeam?: {
    id: number
  }
  awayTeamName?: string
  homeTeamScore?: number
  awayTeamScore?: number
  tournamentId?: number
  leagueId?: number
  matchweek?: number
  round?: number
  matchProtocolCreated?: boolean
}
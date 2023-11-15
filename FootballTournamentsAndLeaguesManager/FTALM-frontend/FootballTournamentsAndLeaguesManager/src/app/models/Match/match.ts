export class Match {
  id?: number
  date?: Date
  homeTeamId?: number
  homeTeamName?: string
  awayTeamId?: number
  awayTeamName?: string
  homeTeamScore?: number | null
  awayTeamScore?: number | null
  tournamentId?: number
  league?: { id: number }
  matchweek?: number
  round?: number | null
  matchProtocolCreated?: boolean
}
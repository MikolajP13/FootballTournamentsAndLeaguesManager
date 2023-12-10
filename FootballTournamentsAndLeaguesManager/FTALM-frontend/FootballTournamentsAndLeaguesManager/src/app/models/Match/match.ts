export class Match {
  id?: number
  date?: Date
  homeTeamId?: number
  homeTeamName?: string
  awayTeamId?: number
  awayTeamName?: string
  homeTeamScore?: number | null
  awayTeamScore?: number | null
  tournament?: {id?: number}
  league?: { id?: number }
  matchweek?: number // in case of tournament with group stage this will be a group identifier
  round?: number | null
  matchProtocolCreated?: boolean
}
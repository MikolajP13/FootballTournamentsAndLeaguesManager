
export class LeagueStanding {
  id?: number;
  league?: { id?: number };
  leagueName?: string
  team?: { id?: number };
  teamName?: string
  matches?: number
  points!: number
  goalsFor!: number
  goalsAgainst!: number
  wins!: number
  draws?: number
  losses?: number
}
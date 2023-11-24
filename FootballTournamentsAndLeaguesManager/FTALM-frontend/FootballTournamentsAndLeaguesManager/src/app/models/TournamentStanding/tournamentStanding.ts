export class TournamentStanding {
  id?: number;
  tournament?: { id?: number };
  tournamentName?: string
  groupId?: number
  team?: { id?: number };
  teamName?: string
  matches?: number
  points?: number
  goalsFor!: number
  goalsAgainst!: number
  wins!: number
  draws!: number
  losses!: number
}
export class Team {
  id?: number | null;
  user?: {
    id: number;
  };
  name!: string;
  captainId?: number | null;
  isInLeague?: boolean;
  isInTournament?: boolean;
  established?: Date;
  numberOfPlayers?: number
}
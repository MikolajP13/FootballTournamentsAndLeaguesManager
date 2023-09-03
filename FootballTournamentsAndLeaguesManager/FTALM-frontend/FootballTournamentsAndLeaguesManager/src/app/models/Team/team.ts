export class Team {
  id?: number;
  user?: {
    id: number;
  };
  name?: string;
  captainId?: number | null;
  isInLeague?: boolean;
  isInTournament?: boolean;
  established?: Date;
}
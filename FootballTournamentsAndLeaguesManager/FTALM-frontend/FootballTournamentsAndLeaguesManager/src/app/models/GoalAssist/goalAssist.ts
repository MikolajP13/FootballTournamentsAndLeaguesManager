export class GoalAssist {
  id?: number;
  match?: {
    id?: number;
  }
  team?: {
    id?: number;
  }
  teamName?: string;
  scorerPlayer?:{
    id?: number;
  }
  scorerPlayerFirstName?: string;
  scorerPlayerLastName?: string;
  assistPlayerId?: number | null;
  assistPlayerFirstName?: string;
  assistPlayerLastName?: string;
  minute?: number;
  matchId?: number;
  type?: string; // fix iterating over event array (type any)
}
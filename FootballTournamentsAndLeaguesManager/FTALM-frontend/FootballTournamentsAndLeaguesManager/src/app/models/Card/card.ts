
export class Card {
  id?: number;
  match?: {
    id?: number;
  }
  team?: {
    id?: number;
  }
  player?: {
    id?: number;
  }
  playerFirstName?: string;
  playerLastName?: string;
  minute?: number;
  cardType?: CardType;
  matchId?: number;
  teamId?: number;
  playerId?: number;
}

export enum CardType {
  YELLOW = "Yellow",
  SECOND_YELLOW = "Second yellow", 
  RED = "Red"
}
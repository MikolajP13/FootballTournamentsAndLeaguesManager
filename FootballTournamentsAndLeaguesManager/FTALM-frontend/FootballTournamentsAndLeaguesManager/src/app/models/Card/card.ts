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
  teamName?: string;
  playerId?: number;
  type?: string; // fix iterating over event array (type any)
}

export enum CardType {
  YELLOW = "Yellow",
  SECOND_YELLOW = "Second yellow", 
  RED = "Red"
}
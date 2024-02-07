export class MatchEvent {
  firstPlayerId?: number;
  secondPlayerId?: number;
  event?: EventType;
}

export enum EventType {
  GOAL, YELLOW_CARD, SECOND_YELLOW_CARD, RED_CARD
}
export class Substitution {
  id?: number;
  match?: {
    id?: number;
  }
  team?: {
    id?: number;
  }
  enteringPlayer?: {
    id?: number;
  }
  enteringPlayerFirstName?: string;
  enteringPlayerLastName?: string;
  exitingPlayer?: {
    id?: number;
  }
  exitingPlayerFirstName?: string;
  exitingPlayerLastName?: string;
  minute?: number;
  teamName?: string;
  type?: string; // fix iterating over event array (type any)
}
export class Player {
  id?: number;
  team?: {
    id: number;
  };
  firstName?: string;
  lastName?: string;
  dateOfBirth?: Date;
  age?: number;
  heightInCm?: number;
  foot?: Foot;
  joinedDate?: Date;
  position?: Position;
  positionDetail?: PositionDetail;
  appearances?: number;
  minutes?: number;
  yellowCards?: number;
  secondYellowCards?: number;
  redCards?: number;
  goalsConceded?: number;
  cleanSheets?: number;
  goals?: number;
  assists?: number;
  suspended?: boolean;
  injured?: boolean;
  teamCaptain?: boolean;
}

export enum Foot {
  RIGHT = "right", LEFT = "left", BOTH = "both",
}

export enum Position {
  GOALKEEPER = 0, DEFENDER, MIDFIELDER, FORWARD
}

export enum PositionDetail {
  LF = "Left Forward", CF = "Centre Forward", RF = "Right Forward",
  LW = "Left Wing", RW = "Right Wing", CAM = "Centre Attacking Midfielder",
  LM = "Left Midfielder", CM = "Centre Midfielder", RM = "Right Midfielder",
  CDM = "Centre Defensive Midfielder",
  LB = "Left Back", LCB = "Left Centre Back", CB = "Centre Back", RCB = "Right Centre Back", RB = "Right Back",
  GK = "Goalkeeper"
}
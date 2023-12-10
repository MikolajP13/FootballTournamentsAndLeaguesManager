import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MatchWeekNumberService {
  matchWeekNumber: number = 1;
  roundNumber: number = 1;
  groupNumber: number = 1;
  
  constructor() { }
}

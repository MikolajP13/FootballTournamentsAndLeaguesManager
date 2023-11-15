import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MatchWeekNumberService {
  matchWeekNumber: number = 1;

  constructor() { }
}

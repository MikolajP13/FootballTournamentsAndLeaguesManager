import { Injectable } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { NavigationEnd, Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SidenavService {
  private sidenav?: MatSidenav;
  private isOpenSubject = new BehaviorSubject<boolean>(false);
  private showMatchesSublistSource = new BehaviorSubject<boolean>(false);

  constructor(private router: Router){
    //hide sidenav after page change 
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd && this.sidenav) {
        this.sidenav.close();
      }
    });
  }

  setSidenav(sidenav: MatSidenav): void {
    this.sidenav = sidenav;
  }

  toggleSidenav(): void {
    if(this.sidenav){
      this.sidenav.toggle();
      this.isOpenSubject.next(this.sidenav.opened);
    }
  }

  toggleMatchesSublist(): boolean {
    this.showMatchesSublistSource.next(!this.showMatchesSublistSource.value);
    return this.showMatchesSublistSource.value;
  }

  getIsOpen(): Observable<boolean> {
    return this.isOpenSubject.asObservable();
  }
}

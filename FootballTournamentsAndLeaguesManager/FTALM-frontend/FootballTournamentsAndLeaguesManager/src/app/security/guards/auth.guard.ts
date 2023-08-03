import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

export function authGuard(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
  
  if(sessionStorage.getItem('user') !== null){
    return true;
  }else{
    window.location.href = "";
    return false;
  }

}

import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogGuard implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const jwt = localStorage.getItem('token');
    if(jwt == null) {
      return true;
    }else{
      const jwtData = jwt.split('.')[1];
      let decodedJwtJsonData = window.atob(jwtData);
      let decodedJwtData = JSON.parse(decodedJwtJsonData);
      const expirationDate: Date = new Date(decodedJwtData.exp*1000);
      if (expirationDate < new Date()) {
        return true;
      }
    }
    const url = this.router.url;
    this.router.navigateByUrl(url);
    return false;
  }
  
}

import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {SessionService} from "../services/session.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private sessionService: SessionService, private router: Router) {}

  canActivate(): Observable<boolean> {
    return this.sessionService.$isLogged().pipe(
      map(isLogged => {
        if (!isLogged) {
          this.router.navigate(['/login']);
        }
        return isLogged;
      })
    );
  }
}

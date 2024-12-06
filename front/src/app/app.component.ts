import { Component } from '@angular/core';
import {SessionService} from "./services/session.service";
import {Router} from "@angular/router";
import {AuthService} from "./features/auth/services/auth.service";
import {Observable} from "rxjs";
import {User} from "./interfaces/user.interface";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front';

  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
  }

  public ngOnInit(): void {
    this.autoLog();
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }
  public autoLog(): void {
    this.authService.me().subscribe(
      (user: User) => {
        this.sessionService.logIn(user);
      },
      (_) => {
        this.sessionService.logOut();
      }
    )
  }

}

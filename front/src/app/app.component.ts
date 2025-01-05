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
  public isLandingPage: boolean = false;

  public isLogged$: Observable<boolean>;
  isRegisterPage: boolean = false;
  isLoginPage: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
    this.router.events.subscribe(() => {
      this.isLandingPage = this.router.url === '/' || this.router.url === '/landing';
      this.isLoginPage = this.router.url === '/login';
      this.isRegisterPage = this.router.url === '/register';
    });

    this.isLogged$ = this.sessionService.$isLogged();
  }

  public ngOnInit(): void {
    this.autoLog();
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

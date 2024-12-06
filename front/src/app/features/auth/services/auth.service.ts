import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {catchError, Observable, throwError} from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { AuthSuccess  } from '../interfaces/authSuccess.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { User } from 'src/app/interfaces/user.interface';
import {environment} from "../../../../environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/auth/register`, registerRequest);
  }


  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/auth/login`, loginRequest).pipe(
      catchError(err => {
        console.error('Erreur de connexion', err);
        return throwError(() => new Error('Connexion échouée'));
      })
    );
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/auth/me`);
  }

}

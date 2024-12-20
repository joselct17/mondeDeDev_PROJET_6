import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;
  public user: User | undefined;
  private redirectUrl: string | null = null;
  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  constructor() {
    this.restoreSession(); //Restaure la session utilisateur au démarrage
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: User): void {
    this.user = user;
    this.isLogged = true;
    localStorage.setItem('user', JSON.stringify(user)); //Sauvegarde l'utilisateur
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token'); // Supprimer le token (JWT)
    localStorage.removeItem('user'); // Supprimer l'utilisateur de localStorage
    this.user = undefined;
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  setRedirectUrl(url: string): void {
    this.redirectUrl = url;
  }

  getRedirectUrl(): string | null {
    return this.redirectUrl;
  }

  // Restaure la session au démarrage de l'application
  private restoreSession(): void {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      this.user = JSON.parse(savedUser);
      this.isLogged = true;
      this.next();
    }
  }
}

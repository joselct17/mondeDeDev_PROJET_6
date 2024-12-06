import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { AuthSuccess } from '../../interfaces/authSuccess.interface';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public hide = true; // Contrôle la visibilité du mot de passe
  public onError = false; // Contrôle l'état d'erreur global
  public errorMessage: string = ''; // Message d'erreur pour l'email
  public isLoading = false;

  // Formulaire de connexion
  public form: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(3)]]
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) {}

  // Getter pour l'email
  get email() {
    return this.form.get('email');
  }

  // Getter pour le mot de passe
  get password() {
    return this.form.get('password');
  }

  // Soumettre le formulaire
  public submit(): void {
    if (this.form.invalid) {
      this.updateErrorMessage(); // Met à jour les messages d'erreur
      return;
    }
    this.isLoading = true;
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe(
      (response: AuthSuccess) => {
        // Stocke le token et connecte l'utilisateur
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/articles']);
          this.isLoading = false;
        });
      },
      error => {
        this.onError = true; // Active un état d'erreur
        this.errorMessage = 'Échec de la connexion. Veuillez vérifier vos identifiants.';
      }
    );
  }

  // Met à jour les messages d'erreur pour l'email
  public updateErrorMessage(): void {
    if (this.email?.hasError('required')) {
      this.errorMessage = 'L\'email est requis.';
    } else if (this.email?.hasError('email')) {
      this.errorMessage = 'L\'adresse email n\'est pas valide.';
    } else {
      this.errorMessage = '';
    }
  }
}


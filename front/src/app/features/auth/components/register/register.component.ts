import { Component } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { AuthSuccess } from '../../interfaces/authSuccess.interface';
import { User } from 'src/app/interfaces/user.interface';

export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return { passwordInvalid: true };
    }

    const hasMinLength = value.length >= 8;
    const hasUpperCase = /[A-Z]/.test(value);
    const hasLowerCase = /[a-z]/.test(value);
    const hasNumber = /[0-9]/.test(value);
    const hasSpecialChar = /[@$!%*?&]/.test(value);

    const isValid = hasMinLength && hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;

    return !isValid ? { passwordInvalid: true } : null;
  };
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  public onError = false;
  hide: boolean = true;

  public form: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    userName: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, passwordValidator()]]
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar
  ) {}

  public submit(): void {
    console.log('Formulaire soumis:', this.form.value);

    if (this.form.invalid) {
      console.log('Formulaire invalide.');
      this.matSnackBar.open('Veuillez remplir tous les champs correctement.', 'Fermer', {
        duration: 3000
      });
      return;
    }

    const registerRequest: RegisterRequest = this.form.value as RegisterRequest;
    console.log('Envoi de la requête d’enregistrement:', registerRequest);

    this.authService.register(registerRequest).subscribe({
      next: (response: AuthSuccess) => {
        console.log('Enregistrement réussi:', response);

        localStorage.setItem('token', response.token);

        this.authService.me().subscribe((user: User) => {
          console.log('Utilisateur récupéré:', user);

          this.sessionService.logIn(user);

          this.matSnackBar.open('Utilisateur enregistré avec succès.', 'Fermer', {
            duration: 3000
          });

          // Rediriger vers /login
          this.router.navigate(['/login']);
        });
      },
      error: (err) => {
        console.error('Erreur lors de l’enregistrement:', err);
        this.matSnackBar.open('Erreur lors de l’enregistrement de l’utilisateur.', 'Fermer', {
          duration: 3000
        });
        this.onError = true;
      }
    });
  }

}

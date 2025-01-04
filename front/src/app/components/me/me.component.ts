import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { User } from 'src/app/interfaces/user.interface';
import { Theme } from 'src/app/features/theme/interfaces/theme.interface';
import {Observable} from "rxjs";
import {ThemeResponse} from "../../features/theme/interfaces/api/themeResponse.interface";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  public user: User | undefined;
  public profileForm!: FormGroup;
  constructor(
    private authService: AuthService,
    private themeService: ThemeService,
    private matSnackBar: MatSnackBar,
    private fb: FormBuilder,
    private cd: ChangeDetectorRef // Inject ChangeDetectorRef
  ) {}

  public ngOnInit(): void {
    // Charger les informations utilisateur
    this.authService.me().subscribe((user: User) => {
      // Ajoutez isSubscribed = true à chaque thème de la liste
      this.user = {
        ...user,
        subscribedThemes: user.subscribedThemes.map((theme) => ({
          ...theme,
          isSubscribed: true // Tous les thèmes de cette liste sont déjà abonnés
        }))
      };

      // Initialisez le formulaire avec les données utilisateur
      this.profileForm = this.fb.group({
        username: [this.user.username, [Validators.required]],
        email: [this.user.email, [Validators.required, Validators.email]],
      });

      this.cd.detectChanges();
    });
  }

  // Action de souscription
  toggleSubscription(theme: any): void {
    if (theme.isSubscribed) {
      this.themeService.unsubscribe(theme.id).subscribe(
        () => {
          this.matSnackBar.open('Désabonné avec succès', 'Fermer', { duration: 3000 });
          // Supprimez le thème de la liste
          this.user!.subscribedThemes = this.user!.subscribedThemes.filter((t) => t.id !== theme.id);
          this.cd.detectChanges(); // Met à jour la vue
        },
        () => {
          this.matSnackBar.open('Erreur lors du désabonnement', 'Fermer', { duration: 3000 });
        }
      );
    } else {
      this.themeService.subscribe(theme.id).subscribe(
        () => {
          this.matSnackBar.open('Abonné avec succès', 'Fermer', { duration: 3000 });
          theme.isSubscribed = true;
        },
        () => {
          this.matSnackBar.open('Erreur lors de la souscription', 'Fermer', { duration: 3000 });
        }
      );
    }
  }



  public saveProfile(): void {
    if (this.profileForm.invalid) {
      this.matSnackBar.open('Veuillez remplir tous les champs correctement', 'Fermer', { duration: 3000 });
      return;
    }

    const updatedUser = {
      ...this.user,
      ...this.profileForm.value,
    };

    this.authService.updateProfile(updatedUser).subscribe(
      () => {
        this.matSnackBar.open('Profil mis à jour avec succès', 'Fermer', { duration: 3000 });
      },
      () => {
        this.matSnackBar.open('Erreur lors de la mise à jour du profil', 'Fermer', { duration: 3000 });
      }
    );
  }

  public back(): void {
    window.history.back();
  }
}

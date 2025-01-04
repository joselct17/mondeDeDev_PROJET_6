import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { User } from 'src/app/interfaces/user.interface';
import { Theme } from 'src/app/features/theme/interfaces/theme.interface';
import {Observable} from "rxjs";
import {ThemeResponse} from "../../features/theme/interfaces/api/themeResponse.interface";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  public user: User | undefined;
  constructor(
    private authService: AuthService,
    private themeService: ThemeService,
    private matSnackBar: MatSnackBar,
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

      //Force Angular à vérifier les modifications
      this.cd.detectChanges();
    });
  }

  // Action de souscription
  toggleSubscription(theme: any): void {
    if (theme.isSubscribed) {
      // Désabonnement
      this.themeService.unsubscribe(theme.id).subscribe(
        (response:ThemeResponse) => {
          this.matSnackBar.open('Désabonné avec sucées', 'Fermer', { duration: 3000 });
          theme.isSubscribed = false; // Met à jour l'état local
        },
        (error) => {
          this.matSnackBar.open('Erreur lors du désabonnement', 'Fermer', { duration: 3000 });
        }
      );
    } else {
      // Souscription
      this.themeService.subscribe(theme.id).subscribe(
        (response) => {
          this.matSnackBar.open('Abonné avec sucées', 'Fermer', { duration: 3000 });
          theme.isSubscribed = true; // Met à jour l'état local
        },
        (error) => {
          this.matSnackBar.open('Erreur lors de la souscription', 'Fermer', { duration: 3000 });
        }
      );
    }
  }

  public saveProfile(): void {
    if (this.user) {
      this.authService.updateProfile(this.user).subscribe(
        () => {
          alert('Profil mis à jour avec succès');
        },
        () => {
          alert('Erreur lors de la mise à jour du profil');
        }
      );
    }
  }

  public back(): void {
    window.history.back();
  }
}

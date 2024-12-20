import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { ThemeService } from 'src/app/features/theme/services/theme.service';
import { User } from 'src/app/interfaces/user.interface';
import { Theme } from 'src/app/features/theme/interfaces/theme.interface';
import {Observable} from "rxjs";

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

  // Fonction de souscription / désabonnement
  toggleSubscription(theme: Theme): void {
    if (theme.isSubscribed) {
      this.themeService.unsubscribe(theme.id).subscribe(
        () => {
          alert('Désabonné avec succès');
          theme.isSubscribed = false; // Met à jour l'état local
        },
        (error) => {
          alert('Erreur lors du désabonnement');
        }
      );
    } else {
      this.themeService.subscribe(theme.id).subscribe(
        () => {
          alert('Abonné avec succès');
          theme.isSubscribed = true; // Met à jour l'état local
        },
        (error) => {
          alert('Erreur lors de la souscription');
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

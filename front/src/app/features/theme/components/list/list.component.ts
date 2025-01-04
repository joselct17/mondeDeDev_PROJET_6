import { Component } from '@angular/core';
import {Observable} from "rxjs";
import {ArticlesResponse} from "../../../articles/interfaces/api/articlesResponse.interface";
import {SessionService} from "../../../../services/session.service";
import {ArticlesService} from "../../../articles/services/articles.service";
import {User} from "../../../../interfaces/user.interface";
import {ThemesResponse} from "../../interfaces/api/themesResponse.interface";
import {ThemeService} from "../../services/theme.service";
import {ThemeResponse} from "../../interfaces/api/themeResponse.interface";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-theme',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent {

  public themes:Observable<ThemesResponse> = this.themeService.all();

  constructor(
    private themeService: ThemeService,
    private matSnackBar: MatSnackBar,
  ) {
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
}

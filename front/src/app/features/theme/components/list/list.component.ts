import { Component } from '@angular/core';
import {Observable} from "rxjs";
import {ArticlesResponse} from "../../../articles/interfaces/api/articlesResponse.interface";
import {SessionService} from "../../../../services/session.service";
import {ArticlesService} from "../../../articles/services/articles.service";
import {User} from "../../../../interfaces/user.interface";
import {ThemesResponse} from "../../interfaces/api/themesResponse.interface";
import {ThemeService} from "../../services/theme.service";
import {ThemeResponse} from "../../interfaces/api/themeResponse.interface";

@Component({
  selector: 'app-theme',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent {

  public themes:Observable<ThemesResponse> = this.themeService.all();

  constructor(
    private themeService: ThemeService,
  ) {
  }

  // Action de souscription
  toggleSubscription(theme: any): void {
    if (theme.isSubscribed) {
      // Désabonnement
      this.themeService.unsubscribe(theme.id).subscribe(
        (response:ThemeResponse) => {
          alert('Désabonné avec succès');
          theme.isSubscribed = false; // Met à jour l'état local
        },
        (error) => {
          alert('Erreur lors du désabonnement');
        }
      );
    } else {
      // Souscription
      this.themeService.subscribe(theme.id).subscribe(
        (response) => {
          alert('Abonné avec succès');
          theme.isSubscribed = true; // Met à jour l'état local
        },
        (error) => {
          alert('Erreur lors de la souscription');
        }
      );
    }
  }
}

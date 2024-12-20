import { Injectable } from "@angular/core";
import { environment } from "../../../../environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ThemesResponse } from "../interfaces/api/themesResponse.interface";
import { ThemeResponse } from "../interfaces/api/themeResponse.interface";
import { Theme } from "../interfaces/theme.interface";

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private pathService = `${environment.apiUrl}/themes`;

  constructor(private httpClient: HttpClient) { }

  // Récupérer tous les thèmes
  public all(): Observable<ThemesResponse> {
    return this.httpClient.get<ThemesResponse>(this.pathService);
  }

  // Créer un thème
  public create(form: { title: string; description: string }): Observable<ThemeResponse> {
    return this.httpClient.post<ThemeResponse>(this.pathService, form);
  }

  // Récupérer les détails d'un thème
  public detail(id: string): Observable<Theme> {
    return this.httpClient.get<Theme>(`${this.pathService}/${id}`);
  }

  // Souscrire à un thème
  public subscribe(themeId: number): Observable<string> {
    return this.httpClient.post<string>(`${this.pathService}/subscribe/${themeId}`, {});
  }

  // Se désinscrire d'un thème
  public unsubscribe(themeId: number): Observable<ThemeResponse> {
    return this.httpClient.delete<ThemeResponse>(`${this.pathService}/unsubscribe/${themeId}`);
  }

}


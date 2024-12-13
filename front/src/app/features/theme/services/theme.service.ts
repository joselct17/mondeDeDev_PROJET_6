import {Injectable} from "@angular/core";
import {environment} from "../../../../environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ThemesResponse} from "../interfaces/api/themesResponse.interface";
import {ThemeResponse} from "../interfaces/api/themeResponse.interface";
import {Theme} from "../interfaces/theme.interface";


@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private pathService = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }


  public all():Observable<ThemesResponse> {
    return this.httpClient.get<ThemesResponse>(`${this.pathService}/themes`)
  }


  public create (form: FormData):Observable<ThemeResponse> {
    return this.httpClient.post<ThemeResponse>(this.pathService, form)
  }

  public subscribe(id:string, form:FormData):Observable<ThemeResponse> {
    return this.httpClient.post<ThemeResponse>(`${this.pathService}/subscribe/${id}`, form)
  }

  public unSubscribe(id:string):Observable<ThemeResponse> {
    return this.httpClient.delete<ThemeResponse>(`${this.pathService}/unsubscribe/${id}`)
  }

}

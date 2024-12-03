import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from 'src/app/features/articles/interfaces/article.interface';
import { ArticleResponse } from '../interfaces/api/articleResponse.interface';
import { ArticlesResponse } from '../interfaces/api/articlesResponse.interface';


@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  private pathService:string = 'api/articles';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<ArticlesResponse> {
    return this.httpClient.get<ArticlesResponse>(this.pathService);
  }

  public detail(id: string): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/${id}`);
  }

  public create(form: FormData): Observable<ArticleResponse> {
    return this.httpClient.post<ArticleResponse>(this.pathService, form);
  }

  public update(id: string, form: FormData): Observable<ArticleResponse> {
    return this.httpClient.put<ArticleResponse>(`${this.pathService}/${id}`, form);
  }
}

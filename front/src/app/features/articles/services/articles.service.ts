import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from 'src/app/features/articles/interfaces/article.interface';
import { ArticleResponse } from '../interfaces/api/articleResponse.interface';
import { ArticlesResponse } from '../interfaces/api/articlesResponse.interface';
import {environment} from "../../../../environment";


@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  private pathService = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<ArticlesResponse> {
    return this.httpClient.get<ArticlesResponse>(`${this.pathService}/articles`);
  }

  public detail(id: string): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/articles/${id}`);
  }

  public create(article: { name: any; theme: any; content: any }): Observable<ArticleResponse> {
    return this.httpClient.post<ArticleResponse>(`${this.pathService}/articles`, article, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  public update(id: string, article: { name: any; theme: any; content: any }): Observable<ArticleResponse> {
    return this.httpClient.put<ArticleResponse>(`${this.pathService}/${id}`, article);
  }
}

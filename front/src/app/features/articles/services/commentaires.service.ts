import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentaireRequest } from '../interfaces/api/commentaireRequest.interface';
import { CommentaireResponse } from '../interfaces/api/commentaireResponse.interface';
import {environment} from "../../../../environment";

@Injectable({
  providedIn: 'root'
})
export class CommentairesService {

  private pathService = `${environment.apiUrl}/comment`;

  constructor(private httpClient: HttpClient) { }

  public send(messageRequest: CommentaireRequest): Observable<CommentaireResponse> {
    return this.httpClient.post<CommentaireResponse>(this.pathService, messageRequest);
  }
  }

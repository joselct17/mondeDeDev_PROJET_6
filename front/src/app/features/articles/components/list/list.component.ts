import { Component } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { ArticlesService } from '../../services/articles.service';
import {Observable} from "rxjs";
import {ArticlesResponse} from "../../interfaces/api/articlesResponse.interface";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  public articles:Observable<ArticlesResponse> = this.articlesService.all();

  constructor(
    private sessionService: SessionService,
    private articlesService: ArticlesService
  ) { }

  get user(): User | undefined {
    return this.sessionService.user;
  }
}

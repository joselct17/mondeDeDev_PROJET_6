import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ArticleResponse } from '../../interfaces/api/articleResponse.interface';
import { Article } from '../../interfaces/article.interface';
import { ArticlesService } from '../../services/articles.service';
import {Theme} from "../../../theme/interfaces/theme.interface";
import {ThemeService} from "../../../theme/services/theme.service";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public articleForm: FormGroup | undefined;

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private articlesService: ArticlesService,
    private sessionService: SessionService,
    private themeService: ThemeService,
    private router: Router
  ) {
  }

  public ngOnInit(): void {
    const url:string = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
      this.articlesService
        .detail(this.id)
        .subscribe((article: Article) => this.initForm(article));
    } else {
      this.initForm();
    }
  }

  public submit(): void {
    const formData:FormData = new FormData();
    formData.append('title', this.articleForm!.get('title')?.value);
    formData.append('content', this.articleForm!.get('content')?.value);
    formData.append('theme', this.articleForm!.get('theme')?.value);

    if (!this.onUpdate) {
      this.articlesService.create(formData).subscribe((articleResponse: ArticleResponse) => this.exitPage(articleResponse));
    } else {
      this.articlesService.update(this.id!, formData).subscribe((articleResponse: ArticleResponse) => this.exitPage(articleResponse));
    }
  }

  private initForm(article?: Article): void {
    this.articleForm = this.fb.group({
      name: [article ? article.name : '', [Validators.required]],
      content: [article ? article.content : '', [Validators.required]],
      theme: [article ? article.theme : '', [Validators.required]]
    });
    if (!this.onUpdate) {
      this.articleForm.addControl('picture', this.fb.control('', [Validators.required]));
    }
  }

  private loadThemes(): void {
    // Assuming you have an endpoint to get the themes
    this.themeService.all().subscribe((themes: Theme[]) => {
      this.themes = themes;
    });
  }

  private exitPage(articleResponse: ArticleResponse): void {
    this.matSnackBar.open(articleResponse.content, "Close", { duration: 3000 });
    this.router.navigate(['articles']);
  }
}

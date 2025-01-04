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
import {catchError} from "rxjs";
import {ThemesResponse} from "../../../theme/interfaces/api/themesResponse.interface";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public articleForm!: FormGroup;
  public themes: Theme[] = [];

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
    this.loadThemes();
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
    if (this.articleForm.invalid) {
      this.matSnackBar.open('Veuillez remplir correctement le formulaire', 'Fermer', { duration: 3000 });
      return;
    }

    const article = {
      name: this.articleForm.get('name')?.value,
      content: this.articleForm.get('content')?.value,
      theme: this.articleForm.get('theme')?.value,
    };

    const article$ = this.onUpdate
      ? this.articlesService.update(this.id!, article)
      : this.articlesService.create(article);

    article$.subscribe({
      next: (articleResponse: ArticleResponse) => this.exitPage(articleResponse),
      error: (error) => {
        console.error('Erreur lors de l\'envoi du formulaire :', error);
        this.matSnackBar.open('Erreur lors de la soumission du formulaire', 'Fermer', { duration: 3000 });
      }
    });
  }




  private initForm(article?: Article): void {
    this.articleForm = this.fb.group({
      name: [article ? article.name : '', [Validators.required]],
      content: [article ? article.content : '', [Validators.required]],
      theme: [article ? article.theme : '', [Validators.required]]
    });
    // Vérifiez si le formulaire est valide
    console.log('Form Validity:', this.articleForm.valid);
  }

  private loadThemes(): void {
    this.themeService.all().subscribe((response: ThemesResponse) => {
      console.log('Themes Response:', response); // Vérifiez ici
      this.themes = response.themes;
    }, (error) => {
      console.error('Erreur lors du chargement des thèmes:', error);
    });
  }


  private exitPage(articleResponse: ArticleResponse): void {
    const message = articleResponse?.name ? `Article "${articleResponse.name}" créé avec succès` : 'Article créé avec succès';
    this.matSnackBar.open(message, 'Fermer', { duration: 3000 });
    this.articleForm.reset();
    this.router.navigate(['/articles']);
  }

}

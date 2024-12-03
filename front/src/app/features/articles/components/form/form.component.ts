import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ArticleResponse } from '../../interfaces/api/articleResponse.interface';
import { Article } from '../../interfaces/article.interface';
import { ArticlesService } from '../../services/articles.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public rentalForm: FormGroup | undefined;

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private rentalsService: ArticlesService,
    private sessionService: SessionService,
    private router: Router
  ) {
  }

  public ngOnInit(): void {
    const url = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
      this.rentalsService
        .detail(this.id)
        .subscribe((article: Article) => this.initForm(article));
    } else {
      this.initForm();
    }
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('title', this.rentalForm!.get('title')?.value);
    formData.append('content', this.rentalForm!.get('content')?.value);

    if (!this.onUpdate) {
      this.rentalsService
        .create(formData)
        .subscribe((articleResponse: ArticleResponse) => this.exitPage(articleResponse));
    } else {
      this.rentalsService
        .update(this.id!, formData)
        .subscribe((articleResponse: ArticleResponse) => this.exitPage(articleResponse));
    }
  }

  private initForm(article?: Article): void {
    console.log(article);
    console.log(this.sessionService.user!.id);
    if( (article !== undefined) && (article?.owner_id !== this.sessionService.user!.id)) {
      this.router.navigate(['/rentals']);
    }
    this.rentalForm = this.fb.group({
      name: [article ? article.title : '', [Validators.required]],
      description: [article ? article.content : '', [Validators.required]],
    });
    if (!this.onUpdate) {
      this.rentalForm.addControl('picture', this.fb.control('', [Validators.required]));
    }
  }

  private exitPage(articleResponse: ArticleResponse): void {
    this.matSnackBar.open(articleResponse.content, "Close", { duration: 3000 });
    this.router.navigate(['rentals']);
  }
}

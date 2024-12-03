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
        .subscribe((rental: Article) => this.initForm(rental));
    } else {
      this.initForm();
    }
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('name', this.rentalForm!.get('name')?.value);
    formData.append('surface', this.rentalForm!.get('surface')?.value);
    formData.append('price', this.rentalForm!.get('price')?.value);
    if (!this.onUpdate) {
      formData.append('picture', this.rentalForm!.get('picture')?.value._files[0]);
    }
    formData.append('description', this.rentalForm!.get('description')?.value);

    if (!this.onUpdate) {
      this.rentalsService
        .create(formData)
        .subscribe((rentalResponse: ArticleResponse) => this.exitPage(rentalResponse));
    } else {
      this.rentalsService
        .update(this.id!, formData)
        .subscribe((rentalResponse: ArticleResponse) => this.exitPage(rentalResponse));
    }
  }

  private initForm(rental?: Article): void {
    console.log(rental);
    console.log(this.sessionService.user!.id);
    if( (rental !== undefined) && (rental?.owner_id !== this.sessionService.user!.id)) {
      this.router.navigate(['/rentals']);
    }
    this.rentalForm = this.fb.group({
      name: [rental ? rental.title : '', [Validators.required]],
      description: [rental ? rental.content : '', [Validators.required]],
    });
    if (!this.onUpdate) {
      this.rentalForm.addControl('picture', this.fb.control('', [Validators.required]));
    }
  }

  private exitPage(rentalResponse: ArticleResponse): void {
    this.matSnackBar.open(rentalResponse.message, "Close", { duration: 3000 });
    this.router.navigate(['rentals']);
  }
}

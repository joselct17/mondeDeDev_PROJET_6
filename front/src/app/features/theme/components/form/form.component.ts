import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ThemeResponse } from '../../interfaces/api/themeResponse.interface';
import { Theme } from '../../interfaces/theme.interface';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public articleForm: FormGroup;

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private themeService: ThemeService,
    private sessionService: SessionService,
    protected router: Router
  ) {
    // Initialisation du formulaire
    this.articleForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  ngOnInit(): void {
    const url: string = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
      this.themeService.detail(this.id).subscribe((theme: Theme) => {
        this.articleForm.patchValue({
          name: theme.name,
          description: theme.description
        });
      });
    }
  }

  public submit(): void {
    const theme = this.articleForm.value;
    this.themeService.create(theme).subscribe(
      (themeResponse: ThemeResponse) => this.exitPage(themeResponse),
      (error) => this.matSnackBar.open('Erreur lors de la création', 'Fermer', { duration: 3000 })
    );
  }


  private exitPage(themeResponse: ThemeResponse): void {
    this.matSnackBar.open('Opération réussie', 'Fermer', { duration: 3000 });
    this.router.navigate(['/themes']);
  }
}


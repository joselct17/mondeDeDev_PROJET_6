import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ThemeResponse } from '../../interfaces/api/themeResponse.interface';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public themeForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private themeService: ThemeService,
    private router: Router
  ) {
    // Initialisation du formulaire
    this.themeForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  ngOnInit(): void {
    // Pas d'autres actions nécessaires ici puisque ce formulaire est uniquement pour la création
  }

  /**
   * Soumet le formulaire pour créer un thème
   */
  public submit(): void {
    if (this.themeForm.invalid) {
      return;
    }

    const theme = this.themeForm.value;

    this.themeService.create(theme).subscribe(
      (themeResponse: ThemeResponse) => {
        this.matSnackBar.open('Thème créé avec succès', 'Fermer', { duration: 3000 });
        this.router.navigate(['/themes']);
      },
      (error) => {
        this.matSnackBar.open('Erreur lors de la création du thème', 'Fermer', { duration: 3000 });
      }
    );
  }

  /**
   * Annule l'action en cours et redirige vers la liste des thèmes
   */
  public cancel(): void {
    this.router.navigate(['/themes']);
  }
}


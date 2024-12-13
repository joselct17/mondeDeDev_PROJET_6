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
  public articleForm: FormGroup | undefined;

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private themeService: ThemeService,
    private sessionService: SessionService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRoutingModule } from './auth-routing.module';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatSnackBarModule} from "@angular/material/snack-bar";

const materialModules = [
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule
]

@NgModule({
  declarations: [
    RegisterComponent,
    LoginComponent,
    LandingPageComponent
  ],
    imports: [
        AuthRoutingModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ...materialModules,
        MatGridListModule,
      MatSnackBarModule,
    ]
})
export class AuthModule { }

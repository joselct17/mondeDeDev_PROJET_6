import { LOCALE_ID, NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { ArticleRoutingModule } from './article-routing.module';
import { ListComponent } from './components/list/list.component';
import { FormComponent } from './components/form/form.component';
import {ArticleDetailComponent} from './components/detail/detail.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
registerLocaleData(localeFr);

const materialModules = [
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSnackBarModule
];

@NgModule({
  declarations: [
    ListComponent,
    FormComponent,
    ArticleDetailComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ArticleRoutingModule,
        ...materialModules,
        MatOptionModule,
        MatSelectModule,
        NgOptimizedImage
    ],
  providers: [
    {
      provide: LOCALE_ID,
      useValue: 'fr-FR'
    },
  ]
})
export class ArticlesModule { }

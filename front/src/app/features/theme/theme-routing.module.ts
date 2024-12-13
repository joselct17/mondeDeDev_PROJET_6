import {RouterModule, Routes} from "@angular/router";
import {ListComponent} from "./components/list/list.component";
import {FormComponent} from "./components/form/form.component";
import {NgModule} from "@angular/core";


const routes: Routes = [
  { title: 'Themes', path: '', component: ListComponent },
  { title: 'Themes - subscribe', path: 'subscribe/:id', component: FormComponent },
  { title: 'Themes - create', path: 'create', component: FormComponent },
  {title:  'Themes - unsubscribe', path:'unsubscribe/:id', component:FormComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ThemeRoutingModule { }

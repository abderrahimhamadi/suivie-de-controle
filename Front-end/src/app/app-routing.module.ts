import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {PrelevementInfoComponent} from "./components/prelevement-info/prelevement-info.component";
import {PrelevementResultatComponent} from "./components/prelevement-resultat/prelevement-resultat.component";
import {PrelevementFormComponent} from "./components/prelevement-form/prelevement-form.component";
import {ResultatFormComponent} from "./components/resultat-form/resultat-form.component";
import {FeedbackComponent} from "./components/feedback/feedback.component";
import {RegisterComponent} from "./components/register/register.component";
import {PrelevementUpdateComponent} from "./components/prelevement-update/prelevement-update.component";
import {AuthGuard} from "./guards/auth.guard";
import {LogGuard} from "./guards/log.guard";
import {ResultatUpdateComponent} from "./components/resultat-update/resultat-update.component";

const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "prelevements", component: PrelevementInfoComponent, canActivate: [AuthGuard]},
  { path: "prelevementForm/:id", component: PrelevementUpdateComponent, canActivate: [AuthGuard]},
  { path: "resultatPrelevements", component: PrelevementResultatComponent, canActivate: [AuthGuard] },
  {path: "prelvementForm", component: PrelevementFormComponent, canActivate: [AuthGuard]},
  {path: "resultatForm/:id", component: ResultatFormComponent, canActivate: [AuthGuard]},
  {path: "resultatUpdate/:id", component: ResultatUpdateComponent, canActivate: [AuthGuard]},
  {path: "feedback", component: FeedbackComponent, canActivate: [AuthGuard]},
  {path: "register", component: RegisterComponent},
  {path: "**", redirectTo: ""}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

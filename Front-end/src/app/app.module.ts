import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { PrelevementInfoComponent } from './components/prelevement-info/prelevement-info.component';
import { PrelevementResultatComponent } from './components/prelevement-resultat/prelevement-resultat.component';
import {NgOptimizedImage} from "@angular/common";
import { AuthFormComponent } from './components/auth-form/auth-form.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AuthInterceptor} from "./services/auth.interceptor";
import {TokenInterceptorProvider} from "./utils/token.interceptor";
import { PrelevementFormComponent } from './components/prelevement-form/prelevement-form.component';
import { ResultatFormComponent } from './components/resultat-form/resultat-form.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { RegisterComponent } from './components/register/register.component';
import { Routes } from '@angular/router';
import jwtDecode from 'jwt-decode';

import { PrelevementUpdateComponent } from './components/prelevement-update/prelevement-update.component';
import { ResultatUpdateComponent } from './components/resultat-update/resultat-update.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    PrelevementInfoComponent,
    PrelevementResultatComponent,
    AuthFormComponent,
    PrelevementFormComponent,
    ResultatFormComponent,
    FeedbackComponent,
    RegisterComponent,
    PrelevementUpdateComponent,
    ResultatUpdateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgOptimizedImage,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
  ],
  /*providers: [{
    provide :HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],*/
  providers: [TokenInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }

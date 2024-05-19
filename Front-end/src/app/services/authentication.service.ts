import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthRequest} from "../models/authRequest.model";
import {BehaviorSubject, Observable, map, of} from "rxjs";
import {AuthResponse} from "../models/authResponse.model";
import {RegisterRequest} from "../models/RegisterRequest.model";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  decodedToken:any;
  backendHost: string = "http://localhost:8080";
  private booleanValue = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) { }

  public login(email: string, password: string): Observable<AuthResponse> {
    let url: string = `${this.backendHost}/authentication/authenticate`;
    let request: AuthRequest = new AuthRequest();
    request.email = email;
    request.password = password;
    return this.http.post<AuthResponse>(url,request);
  }


  public register(registerRequest: RegisterRequest)  {
    let url: string = `${this.backendHost}/authentication/register`;
    return this.http.post<AuthResponse>(url,registerRequest)
  }

  public getDecodeToken() {
    return of(this.decodedToken);
  }

  public setDecodedToken(newDecodeToken: any) {
    this.decodedToken = newDecodeToken;
  }

  getBooleanValue() {
    return this.booleanValue.asObservable();
  }

  setBooleanValue(newValue: boolean) {
    this.booleanValue.next(newValue);
  }


  public logout(){
    let url: string = `${this.backendHost}/authentication/logout`;
    this.http.post(url,{}).subscribe({
      next: data => {
        console.log(data);
      },
      error: err => {
        console.log(err)
      }
    });
  }

}
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Prelevement, PrelevementResponse} from "../models/prelevement.model";
import {ResultatPrelevementDtos} from "../models/resultatPrelevement.model";
import {AuthenticationService} from "./authentication.service";


@Injectable({
  providedIn: 'root'
})
export class PrelevementService {
  backendHost: string = "http://localhost:8080";
  constructor(private http : HttpClient, private authService: AuthenticationService) { }

  public getAllPrelevement(keyword: string, etat: string,
                           numeroProcesVerbal: string, page: number, size: number): Observable<PrelevementResponse>
  {
    let url: string =
        `${this.backendHost}/prelevement/personne?keyword=${keyword}&page=${page}&size=${size}&etat=${etat}&numeroProcesVerbal=${numeroProcesVerbal}`;
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    if(this.authService.decodedToken.labo){
      url = `${this.backendHost}/prelevement/labo?keyword=${keyword}&page=${page}&size=${size}&etat=${etat}&numeroProcesVerbal=${numeroProcesVerbal}`;
    }
    return this.http.get<PrelevementResponse>(url, httpOptions);
  }


  public savePrelevement(prelevement:  Prelevement) {
    let url: string = `${this.backendHost}/prelevement`;
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.post(url, prelevement, httpOptions);
  }

  public deletePrelevement(id: number) {
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.delete(`${this.backendHost}/prelevement/${id}`, httpOptions);
  }

  public getPrelevementById(id: number) {
    let url: string = `${this.backendHost}/prelevement/${id}`;
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.get<Prelevement>(url, httpOptions);
  }

}

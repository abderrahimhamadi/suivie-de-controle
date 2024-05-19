import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResultatPrelevementDtos, ResultatResponse} from "../models/resultatPrelevement.model";
import {AuthenticationService} from "./authentication.service";
import {Detail} from "../models/detail.model";

@Injectable({
  providedIn: 'root'
})
export class ResultatPrelevementService {
  backendHost: string = "http://localhost:8080";
  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  public getAllResultat(kw: string, numerBA: string, conforme: string,page: number, size: number): Observable<ResultatResponse> {
    let idPersonne;
    this.authService.getDecodeToken().subscribe({
      next: data => {
        idPersonne=data.idPersonne;
        console.log(idPersonne);
      },
      error: err => console.log(err)
    });

    let type;
    this.authService.getDecodeToken().subscribe({
      next: data => {
        type=data.type;
      },
      error: err =>  console.log(err)
    });

    let url;
    if (type === "AGENT_CONTROLE") {
      url = `${this.backendHost}/resultatPrelevement/personne/prelevement/${idPersonne}?page=${page}&size=${size}&keyword=${kw}&numeroBA=${numerBA}&conforme=${conforme}`;
    } else {
      url =`${this.backendHost}/resultatPrelevement/personne/${idPersonne}?page=${page}&size=${size}&keyword=${kw}&numeroBA=${numerBA}&conforme=${conforme}`;
    }

    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.get<ResultatResponse>(url, httpOptions);
  }

  
  
  public saveresultat(resultat:ResultatPrelevementDtos): Observable<ResultatPrelevementDtos>{
    let url: string = `${this.backendHost}/resultatPrelevement`;
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.post<ResultatPrelevementDtos>(url, resultat, httpOptions);
  }

  public deleteResultat(id: number) {
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.delete(`${this.backendHost}/resultatPrelevement/${id}`, httpOptions);
  }

  public saveDetailNonConformite(detail: Detail): Observable<Detail> {
    let url: string = `${this.backendHost}/detailNonConformite`;
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.post<Detail>(url,detail,httpOptions);
  }

  public getDetailNonConformite(idResultat: number): Observable<Detail> {
    let url: string = `${this.backendHost}/detailNonConformite/resultat/${idResultat}`;
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.get<Detail>(url,httpOptions);
  }

  public deleteDetail(idResultat: number) {
    let url: string = `${this.backendHost}/detailNonConformite/resultat/${idResultat}`;
    const token = localStorage.getItem("token");
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + token
      })
    };
    return this.http.delete(url, httpOptions);
  }

}

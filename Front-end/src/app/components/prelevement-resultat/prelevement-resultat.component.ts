import {Component, OnInit} from '@angular/core';
import {ResultatPrelevementService} from "../../services/resultat-prelevement.service";
import {ResultatPrelevementDtos, ResultatResponse} from "../../models/resultatPrelevement.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';
import {logMessages} from "@angular-devkit/build-angular/src/builders/browser-esbuild/esbuild";
import {Detail} from "../../models/detail.model";

@Component({
  selector: 'app-prelevement-resultat',
  templateUrl: './prelevement-resultat.component.html',
  styleUrls: ['./prelevement-resultat.component.css']
})
export class PrelevementResultatComponent implements OnInit{
  resultats!: ResultatResponse;
  detail: Detail= new Detail();
  searchFormGroup!: FormGroup;
  typePersonne!: string;


  constructor(private resultatService: ResultatPrelevementService,
              private authService: AuthenticationService,
              private fb:FormBuilder,
              public authservice:AuthenticationService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.authservice.getDecodeToken().subscribe(data => {
      this.typePersonne = data.type;
    })

    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(""),
      numeroBA: this.fb.control(""),
      conforme: this.fb.control("")
    });

    this.handleGetAllResultat(0,5);
  }

  handleGetAllResultat(page: number, size: number) {
    let kw= this.searchFormGroup.value.keyword;
    let numeroBA= this.searchFormGroup.value.numeroBA;
    let conforme= this.searchFormGroup.value.conforme;
    if (numeroBA === null) {
      numeroBA = "";
    }
    this.resultatService.getAllResultat(kw, numeroBA, conforme, page, size).subscribe({
      next: data => {
        this.resultats=data;
        this.resultats.resultatPrelevementDTOS.forEach(resultat => {
          if(!resultat.conforme) {
            this.handleGetDetail(resultat);
          }
        })
      },
      error: err => {
        console.log(err);
      }
    })
  }

  handleGetDetail(resultat: ResultatPrelevementDtos) {
    this.resultatService.getDetailNonConformite(resultat.id).subscribe({
      next: data => {
        resultat.dateTA=data.dateTA;
        resultat.numeroTA=data.numeroTA;
        resultat.detail=data.detail;
      },
      error: err => {
        console.log(err);
      }
    })
  }
  

  goToPage(page: number) {
    this.handleGetAllResultat(page, 5);
  }

  handleFilterResultat() {
    this.handleGetAllResultat(0, 5)
    console.log(this.searchFormGroup.value);
  }

  handleDeleteResultat(id: number) {
    let conf: boolean = confirm("Êtes-vous sûr?");
    if(!conf) return
    this.resultatService.deleteResultat(id).subscribe({
      next: data => {
        console.log(data);
        this.handleGetAllResultat(0, 5);
      },
      error: err => console.log(err)
    })
  }

  handleUpdateReultat(resultat: ResultatPrelevementDtos) {
    this.router.navigate([`resultatUpdate/${resultat.id}`, {resultat: JSON.stringify(resultat)}])
  }
}

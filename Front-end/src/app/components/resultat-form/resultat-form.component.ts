import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { ResultatPrelevementDtos } from 'src/app/models/resultatPrelevement.model';
import { ResultatPrelevementService } from 'src/app/services/resultat-prelevement.service';
import {Detail} from "../../models/detail.model";

@Component({
  selector: 'app-resultat-form',
  templateUrl: './resultat-form.component.html',
  styleUrls: ['./resultat-form.component.css']
})
export class ResultatFormComponent implements OnInit{
  selected!: string;
  page!: number;
  id!: string;
  public resultat!: ResultatPrelevementDtos;
  detail: Detail = new Detail();
  constructor( private activatedRoute: ActivatedRoute,
               private resultatPrelevementService:ResultatPrelevementService,
               private route:Router){
  }
  @ViewChild('f') Signupfrom: NgForm | undefined;
  ngOnInit(): void {
    this.resultat = {} as ResultatPrelevementDtos;
    this.activatedRoute.params.subscribe(params => {
      this.resultat.idPrelevement= params['id'];})
    }

  onSubmit(){
    console.log(this.Signupfrom?.value);
    this.resultat.dateBA=this.Signupfrom?.value.date_BA;
    this.resultat.numeroBA=this.Signupfrom?.value.numeroBA;
    let jwt: string | null = localStorage.getItem('token');
    let jwtData: string = '';
    if (jwt) jwtData = jwt.split('.')[1];
    let decodedJwtJsonData = window.atob(jwtData);
    let decodedJwtData = JSON.parse(decodedJwtJsonData);
    let idPersonne = decodedJwtData.idPersonne;
    this.resultat.idPersonne=idPersonne;
    if(this.Signupfrom?.value.choix=="Oui"){
      this.resultat.conforme=true;
    }
    else{
      this.resultat.conforme=false;
      this.detail.dateTA=this.Signupfrom?.value.DateTA;
      this.detail.detail=this.Signupfrom?.value.Détail;
      this.detail.numeroTA=this.Signupfrom?.value.NuméroTA;
    }
    this.resultatPrelevementService.saveresultat(this.resultat).subscribe({
      next: resultat => {
        console.log("Gooood");
        if(!this.resultat.conforme){
          console.log('Gooood2')
          this.detail.idResultat = resultat.id;
          this.resultatPrelevementService.saveDetailNonConformite(this.detail).subscribe({
            next: data => {
              console.log(data);
            },
            error: err => {
              console.log(err);
            }
          })
        }
        this.Signupfrom?.reset();
        this.route.navigate(["feedback"]);
      },
      error: err => {
        console.log(err);
      }
    });
  }
  
}

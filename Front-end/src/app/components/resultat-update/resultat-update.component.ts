import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ResultatPrelevementDtos} from "../../models/resultatPrelevement.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Detail} from "../../models/detail.model";
import {ResultatPrelevementService} from "../../services/resultat-prelevement.service";

@Component({
  selector: 'app-resultat-update',
  templateUrl: './resultat-update.component.html',
  styleUrls: ['./resultat-update.component.css']
})
export class ResultatUpdateComponent implements OnInit{
  resultat!: ResultatPrelevementDtos;
  resultatFormGroup!: FormGroup;
  detail: Detail = new Detail();

  constructor(private route: ActivatedRoute, private fb: FormBuilder,
              private resultatService: ResultatPrelevementService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const resultatString = params.get('resultat');
      if (typeof resultatString === "string") {
        this.resultat = JSON.parse(resultatString);
      }
      console.log(this.resultat);
    });

    this.resultatFormGroup = this.fb.group({
      dateBA: this.fb.control(this.resultat.dateBA.toString().substring(0,10)),
      numeroBA: this.fb.control(this.resultat.numeroBA),
      conforme: this.fb.control(this.resultat.conforme?'Oui':'Non'),
      dateTA: this.fb.control(this.resultat.numeroTA!=undefined?this.resultat.dateTA.toString().substring(0,10):null),
      numeroTA: this.fb.control(this.resultat.numeroTA!=undefined?this.resultat.numeroTA:null),
      detail: this.fb.control(this.resultat.numeroTA!=undefined?this.resultat.detail:null),
    })

  }

  onSubmit() {
    console.log(this.resultatFormGroup.value);
    this.resultat.dateBA=this.resultatFormGroup.value.dateBA;
    this.resultat.numeroBA=this.resultatFormGroup.value.numeroBA;
    let jwt: string | null = localStorage.getItem('token');
    let jwtData: string = '';
    if (jwt) jwtData = jwt.split('.')[1];
    let decodedJwtJsonData = window.atob(jwtData);
    let decodedJwtData = JSON.parse(decodedJwtJsonData);
    let idPersonne = decodedJwtData.idPersonne;
    this.resultat.idPersonne=idPersonne;
    if(this.resultatFormGroup.value.conforme=="Oui"){
      this.resultat.conforme=true;
    }
    else{
      this.resultat.conforme=false;
      this.detail.dateTA=this.resultatFormGroup.value.dateTA;
      this.detail.detail=this.resultatFormGroup.value.detail;
      this.detail.numeroTA=this.resultatFormGroup.value.numeroTA;
    }

    let detail2: Detail | undefined;
    this.resultatService.saveresultat(this.resultat).subscribe({
      next: resultat => {
        this.detail.idResultat=resultat.id;
        this.resultatService.getDetailNonConformite(resultat.id).subscribe({
          next: data => {
            detail2=data
            if (!detail2 && this.resultat.conforme) {
              this.router.navigate(['resultatPrelevements'])
            } else if (!detail2 && !this.resultat.conforme) {
              console.log(this.detail)
              this.resultatService.saveDetailNonConformite(this.detail).subscribe({
                next: data => {
                  console.log(data);
                  this.router.navigate(['resultatPrelevements'])
                }, error: err => console.log("Error in saving detail", err)
              });
            } else if (detail2 && this.resultat.conforme) {
              this.resultatService.deleteDetail(resultat.id).subscribe({
                next: data => {
                  this.router.navigate(['resultatPrelevements'])
                }, error: err => console.log("Error in deleting detail", err)
              })
            } else if (detail2 && !this.resultat.conforme) {
              this.resultatService.getDetailNonConformite(this.resultat.id).subscribe({
                next: data => {
                  console.log(data)
                  this.detail.id=data.id
                  this.detail.idResultat=data.idResultat
                  console.log(this.detail)
                  this.resultatService.saveDetailNonConformite(this.detail).subscribe({
                    next: data => {
                      console.log(data);
                      this.router.navigate(['resultatPrelevements'])
                    }, error: err => console.log(console.log("Detail Updated failed"), err)
                  });
                }, error: err => console.log("Error in getting detail", err)
              })
            }

          }, error: err => console.log("Error in getting detail first", err)
        })
      },
      error: err => console.log(err)
    })
  }


}

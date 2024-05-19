import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, NgForm} from "@angular/forms";
import {Produit} from "../../models/produit.model";
import {Prelevement} from "../../models/prelevement.model";
import {PrelevementService} from "../../services/prelevement.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-prelevement-update',
  templateUrl: './prelevement-update.component.html',
  styleUrls: ['./prelevement-update.component.css']
})
export class PrelevementUpdateComponent implements OnInit{
  @ViewChild('f') Signupfrom: NgForm | undefined;
  prelevementFormGroup!: FormGroup;
  produit: Produit = new Produit();
  prelevement: Prelevement = new Prelevement() ;
  page!: number;

  constructor(private fb: FormBuilder,
              private prelevementService: PrelevementService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      const prelevementString = params.get('prelevement');
      const pageString = params.get('page');
      if (typeof prelevementString === "string") {
        this.prelevement = JSON.parse(prelevementString);
      }
      if (typeof pageString === "string") {
        this.page = JSON.parse(pageString)
      }
    })
    this.prelevementFormGroup = this.fb.group({
      nom: this.fb.control(this.prelevement.produitDTO.nom),
      categorie: this.fb.control(this.prelevement.produitDTO.categorie),
      dateProcesVerbal: this.fb.control(this.prelevement.dateProcesVerbal.toString().substring(0,10)),
      numeroProcesVerbal: this.fb.control(this.prelevement.numeroProcesVerbal),
      typePrelevement: this.fb.control(this.prelevement.typePrelevement),
      cadreControle: this.fb.control(this.prelevement.cadreControle),
      niveauPrel: this.fb.control(this.prelevement.niveauPrel),
      laboDestination: this.fb.control(this.prelevement.laboDestination),
      dateEnvoie: this.fb.control(this.prelevement.dateEnvoie.toString().substring(0,10)),
    })


  }

  onSubmit() {
    this.produit.nom = this.prelevementFormGroup.value.nom;
    this.produit.categorie = this.prelevementFormGroup.value.categorie;
    this.prelevement.produitDTO = this.produit;

    this.prelevement.dateProcesVerbal = this.prelevementFormGroup.value.dateProcesVerbal;
    this.prelevement.numeroProcesVerbal = this.prelevementFormGroup.value.numeroProcesVerbal;
    this.prelevement.typePrelevement = this.prelevementFormGroup.value.typePrelevement;
    this.prelevement.cadreControle = this.prelevementFormGroup.value.cadreControle;
    this.prelevement.niveauPrel = this.prelevementFormGroup.value.niveauPrel;
    this.prelevement.laboDestination = this.prelevementFormGroup.value.laboDestination;
    this.prelevement.dateEnvoie = this.prelevementFormGroup.value.dateEnvoie;
    this.prelevement.etatAvancement = 'EN_INSTANCE';

    let jwt: string | null = localStorage.getItem('token');
    let jwtData: string = '';
    if (jwt) jwtData = jwt.split('.')[1];
    let decodedJwtJsonData = window.atob(jwtData);
    let decodedJwtData = JSON.parse(decodedJwtJsonData);
    let idPersonne = decodedJwtData.idPersonne;

    this.prelevement.idPersonne = idPersonne;


    this.prelevementService.savePrelevement(this.prelevement).subscribe({
      next: data => {
        console.log("Gooood");
        this.prelevementFormGroup.reset();
        this.router.navigate(["feedback", {page: this.page}]);
      },
      error: err => {
        console.log(err);
      }
    })
  }
}

import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, NgForm} from "@angular/forms";
import {Produit} from "../../models/produit.model";
import {Prelevement} from "../../models/prelevement.model";
import {PrelevementService} from "../../services/prelevement.service";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-prelevement-form',
  templateUrl: './prelevement-form.component.html',
  styleUrls: ['./prelevement-form.component.css']
})
export class PrelevementFormComponent implements OnInit{
  @ViewChild('f') Signupfrom: NgForm | undefined;
  prelevementFormGroup!: FormGroup;
  produit: Produit = new Produit();
  prelevement: Prelevement = new Prelevement() ;
  idPrelevement!: number;

  constructor(private fb: FormBuilder,
              private prelevementService: PrelevementService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.prelevementFormGroup = this.fb.group({
      nom: this.fb.control(null),
      categorie: this.fb.control(null),
      dateProcesVerbal: this.fb.control(null),
      numeroProcesVerbal: this.fb.control(null),
      typePrelevement: this.fb.control(null),
      cadreControle: this.fb.control(null),
      niveauPrel: this.fb.control(null),
      laboDestination: this.fb.control(null),
      dateEnvoie: this.fb.control(null),
    })

    this.idPrelevement = this.activatedRoute.snapshot.paramMap.get('id') as unknown as number;
    if (this.idPrelevement){
      this.prelevementService.getPrelevementById(this.idPrelevement).subscribe({
        next: data => {
          console.log("Hello")
          console.log(data);
          this.prelevementFormGroup.value.nom = data.produitDTO.nom
          this.prelevementFormGroup.value.categorie = data.produitDTO.categorie
          this.prelevementFormGroup.value.dateProcesVerbal = data.dateProcesVerbal
          this.prelevementFormGroup.value.numeroProcesVerbal = data.numeroProcesVerbal
          this.prelevementFormGroup.value.typePrelevement = data.typePrelevement
          this.prelevementFormGroup.value.cadreControle = data.cadreControle
          this.prelevementFormGroup.value.niveauPrel = data.niveauPrel
          this.prelevementFormGroup.value.laboDestination = data.laboDestination
          this.prelevementFormGroup.value.dateEnvoie = data.dateEnvoie
          console.log(this.prelevementFormGroup.value);
          /*this.prelevement.produitDTO.nom = data.produitDTO.nom
          this.prelevement.produitDTO.categorie = data.produitDTO.categorie
          this.prelevement.dateProcesVerbal = data.dateProcesVerbal
          this.prelevement.numeroProcesVerbal = data.numeroProcesVerbal
          this.prelevement.typePrelevement = data.typePrelevement
          this.prelevement.cadreControle = data.cadreControle
          this.prelevement.niveauPrel = data.niveauPrel
          this.prelevement.laboDestination = data.laboDestination
          this.prelevement.dateEnvoie = data.dateEnvoie*/
        },
        error : err => {
          console.log(err)
        }
      });
    }

  }

  onSubmit(){
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
    this.prelevement.etatAvancement = 'EN_INSTANCE'

    let jwt: string | null = localStorage.getItem('token');
    let jwtData: string = '';
    if (jwt)  jwtData = jwt.split('.')[1];
    let decodedJwtJsonData = window.atob(jwtData);
    let decodedJwtData = JSON.parse(decodedJwtJsonData);
    let idPersonne = decodedJwtData.idPersonne;

    this.prelevement.idPersonne = idPersonne;

    console.log(this.prelevement)

    this.prelevementService.savePrelevement(this.prelevement).subscribe({
      next: data => {
        console.log("Gooood");
        this.prelevementFormGroup.reset();
        this.router.navigateByUrl("feedback");
      },
      error: err => {
        console.log(err);
      }
    })

  }






}
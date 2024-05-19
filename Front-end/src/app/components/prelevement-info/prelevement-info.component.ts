import {Component, OnInit} from '@angular/core';
import {PrelevementService} from "../../services/prelevement.service";
import {Prelevement, PrelevementResponse} from "../../models/prelevement.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PageEvent} from "@angular/material/paginator";
import {MatPaginator} from "@angular/material/paginator";
import {ActivatedRoute, NavigationExtras, Router} from "@angular/router";
import { AuthenticationService } from 'src/app/services/authentication.service';


@Component({
  selector: 'app-prelevement-info',
  templateUrl: './prelevement-info.component.html',
  styleUrls: ['./prelevement-info.component.css']
})
export class PrelevementInfoComponent implements OnInit{
  prelevementResponses!: PrelevementResponse;
  filterFormGroup!: FormGroup;
  page: number = 0;
  decodedToken!: any;

  constructor(private prelevementService: PrelevementService,
              private fb: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute,public authservice:AuthenticationService) {
  }

  ngOnInit(): void {
    this.authservice.getDecodeToken().subscribe({
      next: data => {
        console.log("hello")
        this.decodedToken = data;
        console.log(data)
      },
      error : err => {
        console.log(err);
      }
    })

    this.activatedRoute.paramMap.subscribe(params => {
      const pageString = params.get("page");
      if (typeof pageString === "string") {
        this.page = JSON.parse(pageString);
      }
    })

    this.filterFormGroup = this.fb.group({
      keyword: this.fb.control(""),
      numeroProcesVerbal: this.fb.control(""),
      etat: this.fb.control("")
    });

    console.log(this.filterFormGroup.value);

    this.handleGetAllPrelevement("", "", "",  this.page, 5);

  }

  handleGetAllPrelevement(keyword: string, etat: string, numeroProcesVerbal: string, page: number, size: number) {
    this.prelevementService.getAllPrelevement(keyword,etat, numeroProcesVerbal,page,size).subscribe({
      next: data => {
        this.prelevementResponses = data;
        //console.log(data)
      },
      error: err => {
        console.log(err);
      }
    });
  }

  goToPage(page: number) {
    const currentUrl = this.router.url;
    const updatedUrl = currentUrl.replace(/;page=\d+/, '');
    this.router.navigateByUrl(updatedUrl);

    let keyword: string = this.filterFormGroup.value.keyword;
    let etat: string = this.filterFormGroup.value.etat;
    let numeroProcesVerbal: string = this.filterFormGroup.value.numeroProcesVerbal;
    if (numeroProcesVerbal === null) {
      numeroProcesVerbal = "";
    }
    this.handleGetAllPrelevement(keyword, etat, numeroProcesVerbal, page, 5);
  }

  handleDeletePrelevement(id: number, page: number, size: number) {
    let keyword: string = this.filterFormGroup.value.keyword;
    let etat: string = this.filterFormGroup.value.etat;
    let numeroProcesVerbal: string = this.filterFormGroup.value.numeroProcesVerbal;
    if (numeroProcesVerbal === null) {
      numeroProcesVerbal = "";
    }
    let conf: boolean = confirm("Êtes-vous sûr?");
    if(!conf) return
    this.prelevementService.deletePrelevement(id).subscribe({
      next: data => {
        //console.log(data);
        this.handleGetAllPrelevement(keyword, etat, numeroProcesVerbal, page, size);
      },
      error: err => {
        console.log(err);
      }
    });
  }


  handleFilterPrelevement() {
    console.log(this.filterFormGroup.value);
    let keyword: string = this.filterFormGroup.value.keyword;
    let etat: string = this.filterFormGroup.value.etat;
    let numeroProcesVerbal: string = this.filterFormGroup.value.numeroProcesVerbal;
    if (numeroProcesVerbal === null) {
      numeroProcesVerbal = "";
    }
    this.handleGetAllPrelevement(keyword, etat, numeroProcesVerbal, 0, 5);
  }

  goToPrelevementForm() {
    this.router.navigateByUrl("/prelvementForm");
  }

  handleUpdatePrelevement(prelevement: Prelevement, page: number) {
    //console.log(prelevement);
    this.router.navigate(
        [`prelevementForm/${prelevement.id}`, {prelevement : JSON.stringify(prelevement), page} ])
  }

  goToPrelevemenresultat(prelevement: Prelevement) {
    this.router.navigate(['/resultatForm',prelevement.id]);
  }
}
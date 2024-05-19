import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { RegisterRequest } from 'src/app/models/RegisterRequest.model';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent {
  page: number=0;
  private RegisterRequest!: RegisterRequest;
  constructor(private router: Router,public authservice:AuthenticationService , private activatedRoute: ActivatedRoute) {
  }
  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      const pageString = params.get("page")
      if (typeof pageString === "string") {
        this.page = JSON.parse(pageString)
      }
    })
  }
  goToPrelevement() {
    this.router.navigate(['/prelevements', {page: this.page}])
  }
}

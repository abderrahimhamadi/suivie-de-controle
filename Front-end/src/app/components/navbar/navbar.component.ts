import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isRespoLabo: boolean = true;
  isAgentControl: boolean = true;
  log: boolean | undefined;
  status!: boolean;
  constructor(private Http:HttpClient,public authservice:AuthenticationService,private router:Router){
    authservice.getBooleanValue().subscribe(value => this.log=value);
  }

  open(){
    this.status=!this.status;
  }
  direct(){
    if(this.log==true){
      this.router.navigateByUrl('/prelevements')
    }
    else{
      this.router.navigateByUrl('/')
    }
  }
  logout(){
    this.authservice.logout;
    let jwt= localStorage.getItem('token');
    if(jwt){
      localStorage.removeItem('token');
      this.status=!this.status
      this.log=false;
      this.router.navigateByUrl('/');
    }
  }
}
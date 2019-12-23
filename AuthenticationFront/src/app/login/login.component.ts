import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpHeaders } from '@angular/common/http';
import { HomeComponent } from '../home/home.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { SellerService } from '../services/services';
import { Shared } from '../services/token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  SingIn:FormGroup;
  submitted = false;
  userInfo: any;
  public isLoged:boolean=false;
  uAt: any;

  constructor(
    private service:SellerService,
    private formBuilder:FormBuilder,
    private router:Router,
    private token: Shared,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
      this.SingIn = this.formBuilder.group({
        username:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        password:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])]
      });
  }

  get f() { return this.SingIn.controls; }

  onSubmit(event:any) {
    this.submitted = true;
    this.userInfo = this.SingIn.getRawValue();
    this.service.login(this.userInfo).subscribe(
      data => {
        this.userInfo = data
        this.router.navigateByUrl("");
        this.token.username = this.userInfo.logedUsername;
        localStorage.setItem('username',this.userInfo.logedUsername);
    });
  }


}

import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SellerService } from '../services/services';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  SingIn:FormGroup;
  submitted = false;
  user: any;
  public isLoged:boolean=false;
  us:any;

  constructor(
    private service:SellerService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
      this.SingIn = this.formBuilder.group({
        companyID:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        username:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        password:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        firstName:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        lastName:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        address:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        email:['',Validators.compose([Validators.required, Validators.email])]
      });
  }

  get f() { return this.SingIn.controls; }

  onSubmit(event:any) {
    this.submitted = true;
    this.user = this.SingIn.getRawValue();
    this.service.register(this.user).subscribe(
      data => {
        this.router.navigateByUrl("login");
    },
    err => {
      
    });
  }

}

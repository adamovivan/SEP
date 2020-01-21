import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpHeaders } from '@angular/common/http';
import { PayPalService } from '../service/service';

@Component({
  selector: 'app-user-data',
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.css']
})
export class UserDataComponent implements OnInit {

  SingIn:FormGroup;
  submitted = false;
  userInfo: any;
  username: any;
  addPayment: any;

  constructor(
    private payPalService:PayPalService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
      this.SingIn = this.formBuilder.group({
        id:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        secret:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])]
      });
  }

  get f() { return this.SingIn.controls; }

  onSubmit(event:any) {
    this.submitted = true;
    this.userInfo = this.SingIn.getRawValue();
    this.activatedRoute.paramMap.subscribe(
      params => {
          this.userInfo.username = params.get('username');
          this.payPalService.saveUserData(this.userInfo).subscribe(
            data => {
              
            this.addPayment = {
              username: this.userInfo.username,
              payment: 'Paypal'
            };
            this.payPalService.addPayments(this.addPayment).subscribe(
              data => {
                     window.location.href = 'https://localhost:4200/typePayments';
            })
          })
      });
     
  }

}

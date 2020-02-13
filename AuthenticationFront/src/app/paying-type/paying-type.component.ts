import { Component, OnInit } from '@angular/core';
import { AngularWaitBarrier } from 'blocking-proxy/built/lib/angular_wait_barrier';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SellerService } from '../services/services';

@Component({
  selector: 'app-paying-type',
  templateUrl: './paying-type.component.html',
  styleUrls: ['./paying-type.component.css']
})
export class PayingTypeComponent implements OnInit {

  token:any;
  url:any;
  request:any;
  payments:Array<any> = [];
  paymentsNoneActive:Array<any> = [];
  SingIn:FormGroup;
  submitted = false;
  payment: any;
  port:any;
  paymentsNew:any;
  chosen:any

  constructor(
    private service:SellerService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
    params => {
        this.token = params.get('token');
        this.service.fetchNames().subscribe(
          data => {
            this.paymentsNew = data;
            this.service.getTypePayments(this.token).subscribe(
              data => {
                this.chosen = data;
                console.log("Odabrani od strane usera " + this.chosen.payments);
                console.log("Svi u sistemu " + this.paymentsNew);
                let p = false;
                if(this.chosen.payments.length == 0){
                  this.payments = this.chosen.payments;
                }else{
                  for (let prvi of this.chosen.payments) {
                    for (let drugi of this.paymentsNew) {
                        if(prvi == drugi){
                          p = true;
                          break;
                        }
                    }
                    if(p){
                      this.payments.push(prvi);
                      console.log("BLA BLA")
                    }else{
                      this.paymentsNoneActive.push(prvi);
                    }
                    p = false
                  }
                }
                
            });
        });
    });
    this.SingIn = this.formBuilder.group({
      payment:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])]
    });
  }

  get f() { return this.SingIn.controls; }

  onSubmit(event:any) {
    this.submitted = true;
    this.payment = this.SingIn.getRawValue();
    this.token = {
      token: this.token,
      type: this.payment.payment
    }
    this.service.getPaymentLink(this.token).subscribe(
      data => {
              this.url = data;
              console.log(this.url);
              window.location.href = this.url.url;
    });
  }

}

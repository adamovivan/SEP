import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SellerService } from '../services/services';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-subscription-agreement',
  templateUrl: './subscription-agreement.component.html',
  styleUrls: ['./subscription-agreement.component.css']
})
export class SubscriptionAgreementComponent implements OnInit {

  token:any;
  url:any;
  request:any;
  subscriptionPlans:any;
  SingIn:FormGroup;
  submitted = false;
  payment: any;
  port:any;
  agreementRequest:any;

  constructor(
    private service:SellerService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
    params => {
        this.token = params.get('token');


        this.service.getSubscriptionPlansByToken(this.token).subscribe(
          data => {
                  this.subscriptionPlans = data;
        });
    });
    this.SingIn = this.formBuilder.group({
      payment:['',Validators.required]
    });
  }

  get f() { return this.SingIn.controls; }


  subscribe(item : any){
      this.agreementRequest = {
        username:item.username,
        planID:item.planID
      }
      this.service.Subscription(this.agreementRequest).subscribe(
        data => {
            this.url = data;
            window.location.href = this.url.url;
      });

  }

}

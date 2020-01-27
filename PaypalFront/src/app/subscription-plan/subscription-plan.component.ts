import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PayPalService } from '../service/service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-subscription-plan',
  templateUrl: './subscription-plan.component.html',
  styleUrls: ['./subscription-plan.component.css']
})
export class SubscriptionPlanComponent implements OnInit {

  SingIn:FormGroup;
  submitted = false;
  planInfo: any;
  username: any;
  addPayment: any;

  constructor(
    private payPalService:PayPalService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
      this.SingIn = this.formBuilder.group({
        name:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        description:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        type:['',Validators.required],
        amount:['',Validators.compose([Validators.required, Validators.pattern('[ 0-9.,!]+')])],
        frequency:['',Validators.required],
        frequencyInterval:['',Validators.required],
        cycles:['',Validators.compose([Validators.required, Validators.pattern('[0-9]+')])],
      });
  }

  get f() { return this.SingIn.controls; }

  onSubmit(event:any) {
    this.submitted = true;
    this.planInfo = this.SingIn.getRawValue();
    this.activatedRoute.paramMap.subscribe(
      params => {
          this.planInfo.token = params.get('token');
          this.payPalService.addSubscriptionPlan(this.planInfo).subscribe(
            data => {
                     window.location.href = 'https://localhost:4200/subscriptionPlans';
          })
      });
     
  }

}

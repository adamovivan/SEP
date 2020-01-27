import { Component, OnInit } from '@angular/core';
import { SellerService } from '../services/services';
import { Shared } from '../services/token';

@Component({
  selector: 'app-subscription-plans',
  templateUrl: './subscription-plans.component.html',
  styleUrls: ['./subscription-plans.component.css']
})
export class SubscriptionPlansComponent implements OnInit {

  url:any;
  plans:any;
  username:any;
  deletePlan:any;

  constructor(private service : SellerService,private token: Shared) { }

  ngOnInit() {
    this.token.username = localStorage.getItem('username');
    this.username = {
      username : this.token.username
    }
    this.service.getSubscriptionPlans(this.username).subscribe(
      data => {
            this.plans = data;
    });
  }

  addSubPlan(){
    this.username = {
      username : this.token.username
    }
    this.service.getTransactionPlanLink(this.username).subscribe(
      data => {
            this.url = data;
            window.location.href = this.url.url;
    });
  }

  delete(planID:any){
    this.service.deletePlan(planID).subscribe(
      data => {
            this.service.getSubscriptionPlans(this.username).subscribe(
              data => {
                    this.plans = data;
            });
    });
  }

}

import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SellerService } from '../services/services';
import { Router, ActivatedRoute } from '@angular/router';
import { Shared } from '../services/token';
import { SERVER_URL } from '../app.constant';

@Component({
  selector: 'app-type-payments',
  templateUrl: './type-payments.component.html',
  styleUrls: ['./type-payments.component.css']
})
export class TypePaymentsComponent implements OnInit {

  paymentsNew:any;
  payments: any;
  port:any;
  chosen:any;
  addPayment:any;

  constructor(
    private service:SellerService,
    private formBuilder:FormBuilder,
    private router:Router,
    private token: Shared,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.token.username = localStorage.getItem('username');
    this.service.fetchNames().subscribe(
      data => {
        this.paymentsNew = data;
        this.service.getPayments(this.token.username).subscribe(
          data => {
            this.chosen = data;
            let p = false;
              for (let prvi of this.chosen) {
                for (let drugi of this.paymentsNew) {
                    if(prvi == drugi){
                      p = true;
                      break;
                    }
                }
                if(!p){
                  this.payments.push(prvi);
                }
                p = false
              }
              if(this.chosen.length == 0){
                this.payments = this.paymentsNew;
              }
            
        });
    });
    
    

    
  }

  aprove(tip:any) : void{
    this.service.frontedPort(tip).subscribe(
      data => {
        this.port = data;
        this.addPayment = {
          username: this.token.username,
          payment: tip
        };
        this.service.addPayments(this.addPayment).subscribe(
          data => {
            window.location.href = 'http://localhost:' + this.port + '/input/'+ this.token.username;
        });
    });
    
    
  }

}

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
  private payments = new Array<String>();
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
            console.log("Odabrani od strane usera " + this.chosen);
            console.log("Svi u sistemu " + this.paymentsNew);
            let p = false;
            if(this.chosen.length == 0){
              this.payments = this.paymentsNew;
            }else{
              for (let prvi of this.paymentsNew) {
                for (let drugi of this.chosen) {
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

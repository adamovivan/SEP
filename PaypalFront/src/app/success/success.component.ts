import { Component, OnInit } from '@angular/core';
import { PayPalService } from '../service/service';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  paymendId:any;
  PayerId:any;
  username:any;
  completePayment:any;

  constructor(
    private service:PayPalService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      params => {
          this.paymendId = window.location.href.split("?")[1].split("&")[0].split("=")[1];
          this.PayerId = window.location.href.split("?")[1].split("&")[2].split("=")[1];
          this.username = 'mikamikic';
          this.completePayment = {
            username: this.username,
            paymentID: this.paymendId,
            payerID: this.PayerId
          }

          this.service.completePayment(this.completePayment).subscribe(
            data => {
               console.log("USPESNO ZAVRSENA TRANSAKCIJA");
          });
      });
  }

}

import { Component, OnInit } from '@angular/core';
import { PayPalService } from '../service/service';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-cancel-agreement',
  templateUrl: './cancel-agreement.component.html',
  styleUrls: ['./cancel-agreement.component.css']
})
export class CancelAgreementComponent implements OnInit {

  paymendId:any;
  PayerId:any;
  token:any;
  cancelPayment:any;

  constructor(
    private service:PayPalService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      params => {
          this.token = window.location.href.split("?")[0].split("=")[1];
          this.cancelPayment = {
            token: this.token
          }

          this.service.cancelPayment(this.cancelPayment).subscribe(
            data => {
               console.log("USPESNO OTKAZANA TRANSAKCIJA");
          });
      });
  }

}

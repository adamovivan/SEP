import { Component, OnInit } from '@angular/core';
import { PayPalService } from '../service/service';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-success-agreement',
  templateUrl: './success-agreement.component.html',
  styleUrls: ['./success-agreement.component.css']
})
export class SuccessAgreementComponent implements OnInit {

  paymendId:any;
  PayerId:any;
  token:any;
  completeAgreement:any;

  constructor(
    private service:PayPalService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      params => {
          this.token = window.location.href.split("?")[1].split("&")[0].split("=")[1];
          this.completeAgreement = {
            token: this.token
          }

          this.service.completeAgreement(this.completeAgreement).subscribe(
            data => {
               console.log("USPESNO ZAVRSENA TRANSAKCIJA");
          });
      });
  }

}

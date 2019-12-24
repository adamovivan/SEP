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

  email:any;
  total:any;
  payments:any;
  SingIn:FormGroup;
  submitted = false;
  payment: any;
  port:any;

  constructor(
    private service:SellerService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
    params => {
        this.email = params.get('email');
        this.total = params.get('total');
        this.service.getTypePayments(this.email).subscribe(
          data => {
                  this.payments = data;
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
    this.service.frontedPort(this.payment.payment).subscribe(
      data => {
        this.port = data;
        window.location.href = 'http://localhost:' + this.port + '/pay/'+ this.payments.username + '/' + this.total;
        
    });
    
  }

}

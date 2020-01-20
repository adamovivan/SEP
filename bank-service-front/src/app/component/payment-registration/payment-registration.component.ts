import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PaymentService } from 'src/app/service/payment.service';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-payment-registration',
  templateUrl: './payment-registration.component.html',
  styleUrls: ['./payment-registration.component.scss']
})
export class PaymentRegistrationComponent implements OnInit {

  signIn: FormGroup;
  submitted = false;
  userInfo: any;
  username: any;
  addPayment: any;

  constructor(
    private paymentService: PaymentService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
      this.signIn = this.formBuilder.group({
        merchantId:['', Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        merchantPassword:['', Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])]
      });
  }

  get f() { return this.signIn.controls; }

  onSubmit() {
    this.submitted = true;
    this.userInfo = this.signIn.getRawValue();
    this.activatedRoute.paramMap.subscribe(
      params => {
          this.userInfo.username = params.get('username');
          this.paymentService.saveUserData(this.userInfo).subscribe(
            () => {
              this.addPayment = {
                username: this.userInfo.username,
                payment: 'Bank'
              };
              this.paymentService.addPayments(this.addPayment).subscribe(
                data => {
                    window.location.href = environment.authenticationPaymentsUrl;
              })
             
          })
      });
     
  }

}

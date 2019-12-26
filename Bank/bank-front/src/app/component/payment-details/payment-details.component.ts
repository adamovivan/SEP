import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PaymentCardDTO } from 'src/app/dto/payment-card.dto';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/service/payment.service';
import { MatSnackBarConfig, MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrls: ['./payment-details.component.scss']
})
export class PaymentDetailsComponent implements OnInit {

  months = [];
  years = [];

  paymentId: string;
  paymentDetailsForm: FormGroup;

  successUrl: string;
  failedUrl: string;
  errorUrl: string;

  constructor(private formBuilder: FormBuilder, 
              private route: ActivatedRoute,
              private paymentService: PaymentService,
              private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.paymentDetailsForm = this.formBuilder.group({
      pan: ['', Validators.required],
      cardholderName: ['', Validators.required],
      cvv: ['', Validators.required],
      expiryMonth: ['', Validators.required],
      expiryYear: ['', Validators.required]
    });

    this.paymentId = this.route.snapshot.paramMap.get("payment-id");

    this.initMonths();
    this.initYears();

    this.setCallbackUrls();
  }

  initMonths(){
    for(let i = 1; i <= 12; i++){
      this.months.push(i);
    }
  }

  initYears(){
    for(let i = 0; i < 50; i++){
      this.years.push(2019+i);
    }
  }

  setCallbackUrls(){
    this.paymentService.getCallbackUrls(this.paymentId).subscribe(
      res => {
          this.successUrl = res.successUrl;
          this.failedUrl = res.failedUrl;
          this.errorUrl = res.errorUrl;
      },
      err => {
        this.somethingWentWrong();
      });
  }

  submit(){
    if(!this.paymentDetailsForm.valid){
      return;
    }

    let expiryDate = this.createDate(this.paymentDetailsForm.get('expiryYear').value,
                                     this.paymentDetailsForm.get('expiryMonth').value);

    let paymentCardDTO = new PaymentCardDTO();
    paymentCardDTO.pan = this.paymentDetailsForm.get('pan').value;
    paymentCardDTO.cardholderName = this.paymentDetailsForm.get('cardholderName').value;
    paymentCardDTO.expiryDate = expiryDate
    paymentCardDTO.cvv = this.paymentDetailsForm.get('cvv').value;
    paymentCardDTO.pan = this.paymentDetailsForm.get('pan').value;
    paymentCardDTO.paymentId = this.paymentId;

    this.paymentService.pay(paymentCardDTO).subscribe(
      res => {
        window.location.href = this.successUrl;
        
      },
      err => {
        if(err.status == 400){
          window.location.href = this.failedUrl;
        }
        else{
          window.location.href = this.errorUrl;
        }
        
      }
    )
  }

  createDate(expiryYear, expiryMonth){
    let month = parseInt(expiryMonth);

    if(month < 10){
      expiryMonth = '0' + expiryMonth;
    }

    return expiryYear + '-' + expiryMonth + '-' + '01';
  }

  somethingWentWrong(){
    const config = new MatSnackBarConfig();
    config.panelClass = ['background-snack'];
    config.duration = 2000;

    this.snackBar.open('Something went wrong, please try again.', null, config);
  }

}

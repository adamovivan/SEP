import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PaymentCardDTO } from 'src/app/dto/payment-card.dto';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/service/payment.service';

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

  constructor(private formBuilder: FormBuilder, 
              private route: ActivatedRoute,
              private paymentService: PaymentService) { }

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
        console.log(res)
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

}

import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { PaymentService } from 'src/app/service/payment.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-payment-error',
  templateUrl: './payment-error.component.html',
  styleUrls: ['./payment-error.component.scss']
})
export class PaymentErrorComponent implements OnInit {

  transactionId: string;

  constructor(private route: ActivatedRoute,
              private paymentService: PaymentService,
              private _location: Location) { }

  ngOnInit() {
    this.transactionId = this.route.snapshot.paramMap.get("transaction-id");
    this.paymentService.paymentError(this.transactionId).subscribe();
  }

  goBack(){
    this._location.back();
  }
}

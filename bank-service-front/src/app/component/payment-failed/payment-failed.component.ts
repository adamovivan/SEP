import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/service/payment.service';

@Component({
  selector: 'app-payment-failed',
  templateUrl: './payment-failed.component.html',
  styleUrls: ['./payment-failed.component.scss']
})
export class PaymentFailedComponent implements OnInit {

  transactionId: string;

  constructor(private route: ActivatedRoute,
              private paymentService: PaymentService,
              private _location: Location) { }

  ngOnInit() {
    this.transactionId = this.route.snapshot.paramMap.get("transaction-id");
    this.paymentService.paymentFailed(this.transactionId).subscribe();
  }

  goBack(){
    this._location.back();
  }
}

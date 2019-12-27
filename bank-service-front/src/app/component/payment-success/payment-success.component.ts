import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/service/payment.service';

@Component({
  selector: 'app-payment-success',
  templateUrl: './payment-success.component.html',
  styleUrls: ['./payment-success.component.scss']
})
export class PaymentSuccessComponent implements OnInit {

  transactionId: string;

  constructor(private route: ActivatedRoute,
              private paymentService: PaymentService) { }

  ngOnInit() {
    this.transactionId = this.route.snapshot.paramMap.get("transaction-id");
    this.paymentService.paymentSuccess().subscribe();
  }

  ok(){
    window.location.href = "https://www.google.com";
  }

}

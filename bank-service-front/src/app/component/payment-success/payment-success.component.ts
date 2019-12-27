import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-payment-success',
  templateUrl: './payment-success.component.html',
  styleUrls: ['./payment-success.component.scss']
})
export class PaymentSuccessComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  ok(){
    window.location.href = "https://www.google.com";
  }

}

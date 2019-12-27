import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-payment-failed',
  templateUrl: './payment-failed.component.html',
  styleUrls: ['./payment-failed.component.scss']
})
export class PaymentFailedComponent implements OnInit {

  constructor(private _location: Location) { }

  ngOnInit() {
  }

  goBack(){
    this._location.back();
  }
}

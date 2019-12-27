import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-payment-error',
  templateUrl: './payment-error.component.html',
  styleUrls: ['./payment-error.component.scss']
})
export class PaymentErrorComponent implements OnInit {

  
  constructor(private _location: Location) { }

  ngOnInit() {
  }

  goBack(){
    this._location.back();
  }
}

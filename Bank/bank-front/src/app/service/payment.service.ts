import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { PaymentCardDTO } from '../dto/payment-card.dto';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  public pay(paymentCardDTO: PaymentCardDTO){
    // return this.http.post(environment.zuulUrl + '/pay', paymentCardDTO);
    return this.http.post("http://localhost:8500" + '/pay', paymentCardDTO);
  }
}

import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { PaymentCardDTO } from '../dto/payment-card.dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  public pay(paymentCardDTO: PaymentCardDTO){
    // return this.http.post(environment.zuulUrl + '/pay', paymentCardDTO);
    return this.http.post("http://localhost:8500" + '/pay', paymentCardDTO);
  }

  public getCallbackUrls(paymentId: string): Observable<any> {
    return this.http.get("http://localhost:8500/get-callback-urls/" + paymentId);
  }
}

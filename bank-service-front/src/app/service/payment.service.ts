import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  public paymentSuccess() {
    return this.http.put(environment.zuulUrl + "/payment-success", {});
  }

  public paymentFailed() {
    return this.http.put(environment.zuulUrl + "/payment-failed", {});
  }

  public paymentError() {
    return this.http.put(environment.zuulUrl + "/payment-error", {});
  }
}

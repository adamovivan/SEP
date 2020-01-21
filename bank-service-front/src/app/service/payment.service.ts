import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  public paymentSuccess(transactionId: string) {
    return this.http.put(environment.zuulUrl + "/api-bank/payment-success/" + transactionId, {});
  }

  public paymentFailed(transactionId: string) {
    return this.http.put(environment.zuulUrl + "/api-bank/payment-failed/" + transactionId, {});
  }

  public paymentError(transactionId: string) {
    return this.http.put(environment.zuulUrl + "/api-bank/payment-error/" + transactionId, {});
  }

  public saveUserData(userInfo){
    return this.http.post(environment.zuulUrl + "/api-bank/payment-registration", userInfo);
  }

  addPayments(tip:any){
    return this.http.post(environment.zuulUrl + '/authentication-service/addPayments',tip);
  }
  
}

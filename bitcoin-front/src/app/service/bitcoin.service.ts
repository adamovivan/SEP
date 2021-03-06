import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BitcoinService {

  readonly SERVER_URL = 'https://localhost:8765';
  constructor(private http: HttpClient) { }


  createOrder(order:any){
    return this.http.post(this.SERVER_URL + '/api-bitcoin/pay', order);
  }

  saveMerchant(merchant:any){
    return this.http.post(this.SERVER_URL + "/api-bitcoin/save", merchant, {responseType:'text'});
  }


  success(token:string): Observable<string>{
    return this.http.get(this.SERVER_URL + "/api-bitcoin/success/" + token, {responseType:'text'});
  }

  cancel(token:string):  Observable<string>{
    return this.http.get(this.SERVER_URL + "/api-bitcoin/cancel/" + token, {responseType:'text'});
  }

  saveUserData(user:any){
    return this.http.post(this.SERVER_URL + '/api-bitcoin/save', user, {responseType:'text'});
  }

  addPayments(tip:any){
    return this.http.post(this.SERVER_URL + '/authentication-service/addPayments',tip);
  }
}

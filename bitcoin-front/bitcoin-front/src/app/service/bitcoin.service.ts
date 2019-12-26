import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BitcoinService {

  readonly SERVER_URL = 'http://localhost:8765';
  constructor(private http: HttpClient) { }


  createOrder(order:any){
    return this.http.post(this.SERVER_URL + '/api-bitcoin/pay', order);
  }

  saveMerchant(merchant:any){
    return this.http.post(this.SERVER_URL + "/api-bitcoin/save", merchant, {responseType:'text'});
  }


  success(token:string){

  }

  cancel(token:string){

  }

  saveUserData(user:any){
    return this.http.post(this.SERVER_URL + '/api-bitcoin/save', user, {responseType:'text'});
  }
}

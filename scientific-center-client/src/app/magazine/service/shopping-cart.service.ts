import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor(private http: HttpClient) { }

  public getShoppingCartItems(): Observable<any> {
    const reqHeader = new HttpHeaders({ 'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwZXJhcGVyaWMiLCJjcmVhdGVkIjoxNTc0MjU3ODg3OTkyLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUkVBREVSIn1dLCJleHAiOjE1OTIyNTc4ODd9.mgozvaUaXYjAwpkbHzIqdVxv_8i1IuJlq5BMbfUhV3g6gIAQPkuRTyWsSZ_9l-U18P1NX-NmH3uPgixzOndePA'});

    return this.http.get(environment.apiUrl + '/shopping-cart/items', {headers: reqHeader})
  }

  public pay(PaymentRequest){
    return this.http.post(environment.zuulUrl + '/paypal-service/pay', PaymentRequest);
  }
}

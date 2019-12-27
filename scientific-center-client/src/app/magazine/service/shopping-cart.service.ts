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
    return this.http.get(environment.apiUrl + '/shopping-cart/item')
  }

  public pay(){
    return this.http.post(environment.apiUrl + '/shopping-cart/pay', {});
  }
}

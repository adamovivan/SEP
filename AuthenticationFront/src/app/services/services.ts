import { Injectable } from '@angular/core';
import { SERVER_URL } from '../app.constant';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SellerService {

  constructor(private http:HttpClient){

  }

  register(user:any){
    
    return this.http.post(SERVER_URL + '/authentication-service/registerClient',user,{responseType: 'text'});
  }

  login(user:any){
    
    return this.http.post(SERVER_URL + '/authentication-service/loginClient',user,);
  }

  
}

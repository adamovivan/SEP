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

  fetchNames(){
    
    return this.http.get(SERVER_URL + '/authentication-service/discovere');
  }
  
  frontedPort(type:any){

    return this.http.get(SERVER_URL + '/api-' + type.toLowerCase() + '/frontendPort');
  }

  addPayments(tip:any){
    return this.http.post(SERVER_URL + '/authentication-service/addPayments',tip);
  }

  getPayments(tip:any){
    return this.http.get(SERVER_URL + '/authentication-service/getPayments/'+tip,);
  }
  
}

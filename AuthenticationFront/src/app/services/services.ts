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

  getPayments(tip:any){
    return this.http.get(SERVER_URL + '/authentication-service/getPayments/'+tip,);
  }

  getTypePayments(token:any){
    return this.http.get(SERVER_URL + '/authentication-service/getTypePayments/'+token,);
  }

  getSubscriptionPlansByToken(token:any){
    return this.http.get(SERVER_URL + '/authentication-service/getSubscriptionPlans/'+ token);
  }

  getPaymentLink(request:any){
    return this.http.post(SERVER_URL + '/authentication-service/getPaymentLink',request);
  }

  getTransactionPlanLink(username:any){
    return this.http.post(SERVER_URL + '/authentication-service/getTransactionPlanLink',username);
  }

  getSubscriptionPlans(username:any){
    return this.http.post(SERVER_URL + '/api-paypal/getSubscriptionPlans',username);
  }

  deletePlan(id:any){
    return this.http.delete(SERVER_URL + '/api-paypal/deletePlan/'+id);
  }

  Subscription(item:any){
      return this.http.post(SERVER_URL + '/authentication-service/getAgreementLink',item);
  }
}

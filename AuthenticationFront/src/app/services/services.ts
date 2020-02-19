import { Injectable } from '@angular/core';
import { SERVER_URL, SERVER_URL_AUTH } from '../app.constant';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<any>(SERVER_URL + '/authentication-service/loginClient',user,);
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

  getConfig(): Observable<any> {
    return this.http.get(SERVER_URL + '/authentication-service/utc-config');
  }

  startUtc(){
    return this.http.put(SERVER_URL + '/authentication-service/start-utc', {});
  }

  stopUtc(){
    return this.http.put(SERVER_URL + '/authentication-service/stop-utc', {});
  }

  setTimeout(timeout: number){
    return this.http.put(SERVER_URL + '/authentication-service/utc-timeout/' + timeout, {});
  }

  Subscription(item:any){
      return this.http.post(SERVER_URL + '/authentication-service/getAgreementLink',item);
  }

  registerCompany(company:any){
    return this.http.post(SERVER_URL_AUTH + '/registerCompany',company);
  }

  getCompanies(): Observable<any> {
    return this.http.get(SERVER_URL + '/authentication-service/getSubmissions/NONE');
  }

  acceptCompany(companyName:any){
    return this.http.post(SERVER_URL + '/authentication-service/acceptCompany',{"companyName": companyName});
  }

  declineCompany(companyName:any){
    return this.http.post(SERVER_URL + '/authentication-service/declineCompany',{"companyName": companyName, "message": "Nije odobreno"});
  }
}

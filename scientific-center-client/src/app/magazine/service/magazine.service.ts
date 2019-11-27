import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private http: HttpClient) { }

  public getAllMagazines(): Observable<any> {
    return this.http.get(environment.apiUrl + "/magazine/all");
  }

  public addMagazineToShoppingCart(magazineId: string) {
    const reqHeader = new HttpHeaders({ 'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwZXJhcGVyaWMiLCJjcmVhdGVkIjoxNTc0MjU3ODg3OTkyLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUkVBREVSIn1dLCJleHAiOjE1OTIyNTc4ODd9.mgozvaUaXYjAwpkbHzIqdVxv_8i1IuJlq5BMbfUhV3g6gIAQPkuRTyWsSZ_9l-U18P1NX-NmH3uPgixzOndePA'});

    return this.http.post(environment.apiUrl + "/shopping-cart/add-item/" + magazineId, {}, {
        headers: reqHeader // TODO delete
      });
  }
}

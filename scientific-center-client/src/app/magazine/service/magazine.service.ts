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

  public getMagazine(magazineId: string): Observable<any> {
    return this.http.get(environment.apiUrl + "/magazine/" + magazineId);
  }

  public addMagazineToShoppingCart(magazineId: string) {
    return this.http.post(environment.apiUrl + "/shopping-cart/add-magazine/" + magazineId, {});
  }

  public addArticleToShoppingCart(articleId: string) {
    return this.http.post(environment.apiUrl + "/shopping-cart/add-article/" + articleId, {});
  }
}

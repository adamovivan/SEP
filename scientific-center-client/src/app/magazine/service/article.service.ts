import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) { }

  public getArticle(articleId: string): Observable<any> {
    return this.http.get(environment.apiUrl + "/article/" + articleId);
  }

  public getArticleAuthor(articleId: string): Observable<any> {
    return this.http.get(environment.apiUrl + "/author/article/" + articleId);
  }

  public getAllArticles(): Observable<any> {
    return this.http.get(environment.apiUrl + "/article/all");
  }
}

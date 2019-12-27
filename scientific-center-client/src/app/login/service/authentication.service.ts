import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';
import * as jwt_decode from 'jwt-decode';

const TOKEN_KEY = 'authToken';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public loginSubject = new Subject<any>();
  public loginObs = this.loginSubject.asObservable();

  constructor(private http: HttpClient) { }

  login(credentials) {
    return this.http.post<any>(`${environment.apiUrl}/login`, credentials);
  }

  logout() {
    this.clearStorage();
  }

  clearStorage() {
    localStorage.removeItem(TOKEN_KEY);
  }

  public saveToken(token: string) {
    localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }

  public getDecodedToken(): any {
    const token = this.getToken();

    try {
      return jwt_decode(token);
    } catch (e) {
      return null;
    }
  }

  getTokenExpirationDate(): Date {
    const decoded = this.getDecodedToken();

    if (decoded === null) { return null; }
    if (decoded.exp === undefined) { return null; }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) { return true; }

    const date = this.getTokenExpirationDate();
    if (date === undefined) { return false; }
    return !(date.valueOf() > new Date().valueOf());
  }

  public isAuthenticated(): boolean {
    return !this.isTokenExpired();
  }

  getRoles(): Array<string> {
    const decoded = this.getDecodedToken();
    if (!decoded) { return []; }

    const roles = [];
    for (const role of decoded['roles']) {
      roles.push(role['authority']);
    }

    return roles;
  }
}

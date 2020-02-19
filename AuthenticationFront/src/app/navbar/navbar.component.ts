import { Component, OnInit } from '@angular/core';
import { Shared } from '../services/token';
import { SERVER_URL } from '../app.constant';
import { SellerService } from '../services/services';
import { AnonymousSubject } from 'rxjs/internal/Subject';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private token: Shared, private authService: AuthenticationService) { }

  ngOnInit() {
    this.token.username = localStorage.getItem('username');
    this.token.role = localStorage.getItem('role');
  }

  logout(){
    localStorage.setItem('username', 'undefined');   
    localStorage.setItem('role', 'undefined'); 
    this.token.username = 'undefined';
    this.token.role = undefined;
    this.authService.logout();
  }
}

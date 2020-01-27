import { Component, OnInit } from '@angular/core';
import { Shared } from '../services/token';
import { SERVER_URL } from '../app.constant';
import { SellerService } from '../services/services';
import { AnonymousSubject } from 'rxjs/internal/Subject';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private token: Shared) { }

  ngOnInit() {
    this.token.username = localStorage.getItem('username');
  }

  logout(){
    localStorage.setItem('username', 'undefined');   
    this.token.username = 'undefined';
    
  }
}

import { Component, OnInit } from '@angular/core';
import { Shared } from '../services/token';

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
    localStorage.setItem('token', 'undefined');   
    this.token.username = 'undefined';
    
  }
}

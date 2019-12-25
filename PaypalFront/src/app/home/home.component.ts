import { Component, OnInit } from '@angular/core';
import { PayPalService } from '../service/service';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  total:any;
  username:any;
  user:any;
  url:any;

  constructor(
    private service:PayPalService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      params => {
          this.total = params.get('total');
          this.username = params.get('username');
          this.user = {
            username: this.username,
            totalPrice: this.total
          };
      });
  }

  pay(){
    this.service.createOrder(this.user).subscribe(
      data => {
         this.url = data;
         localStorage.setItem('username',this.user.username);
         window.location.href = this.url.url;
    });
  }
}

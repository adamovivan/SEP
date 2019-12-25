import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { BitcoinService } from '../service/bitcoin.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  total : any;
  username : any;
  order : any;
  url : any; 
  constructor(private activatedRoute: ActivatedRoute, private router: Router, private formBuilder: FormBuilder, private service : BitcoinService ) { }

  ngOnInit() {
      this.activatedRoute.paramMap.subscribe(
        params=> {
          this.total = params.get('total');
          this.username = params.get('username');
          this.order = {
            username : this.username,
            amount : this.total
          };        
        });
  }


  pay(){
    this.service.createOrder(this.order).subscribe(
      data => {
        this.url = data;
        localStorage.setItem('username', this.order.username);
        window.location.href = this.url;
      }
    )
  }

  
}

import { Component, OnInit } from '@angular/core';
import { PayPalService } from '../service/service';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-cancel',
  templateUrl: './cancel.component.html',
  styleUrls: ['./cancel.component.css']
})
export class CancelComponent implements OnInit {

  username:any;

  constructor(
    private payPalService:PayPalService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
          this.username = localStorage.getItem('username');
          console.log(this.username);
  }

  cancel(){
  }

}

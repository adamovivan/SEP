import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BitcoinService } from '../service/bitcoin.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  SingIn:FormGroup;
  submitted = false;
  userInfo: any;
  username: any;
  addPayment:any;

  constructor(private service: BitcoinService, private formBuilder : FormBuilder, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.SingIn = this.formBuilder.group({
      token:['',Validators.compose([Validators.required])],
    });
  }

  get f() { return this.SingIn.controls;}

  onSubmit(event:any){
    this.submitted = true;
    this.userInfo = this.SingIn.getRawValue();
    this.activatedRoute.paramMap.subscribe(
      params => {
          this.userInfo.username = params.get('username');
          this.service.saveUserData(this.userInfo).subscribe(
            data => {
              this.addPayment = {
                username: this.userInfo.username,
                payment: 'Bitcoin'
              };
              this.service.addPayments(this.addPayment).subscribe(
                data => {
                       window.location.href = 'https://localhost:4200/typePayments';
              })
            })
          })
  }

}

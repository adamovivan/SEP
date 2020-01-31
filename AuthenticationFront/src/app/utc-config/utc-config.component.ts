import { Component, OnInit } from '@angular/core';
import { SellerService } from '../services/services';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-utc-config',
  templateUrl: './utc-config.component.html',
  styleUrls: ['./utc-config.component.css']
})
export class UtcConfigComponent implements OnInit {

  utcForm: FormGroup;
  submitted = false;

  constructor(private service: SellerService,
    private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.utcForm = this.formBuilder.group({
      timeout: ['', [Validators.min(1), Validators.required]]
    });

    this.service.getConfig().subscribe(res => {
      console.log(res);
    },
    err => {
      console.log(err);
    });
  }

  get f() { return this.utcForm.controls; }

  utcStart(){
    this.service.startUtc().subscribe(() => {

    },
    err => {
      console.log(err);
    });
  }

  utcStop(){
    this.service.stopUtc().subscribe(() => {

    },
    err => {
      console.log(err);
    });
  }

  changeTimeout(){
    this.submitted = true;
    console.log(this.utcForm.controls["timeout"].value * 1000);
  }

}

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
  timeout = 0;
  running = false;

  constructor(private service: SellerService,
    private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.utcForm = this.formBuilder.group({
      timeout: ['', [Validators.min(1), Validators.required]]
    });

    this.service.getConfig().subscribe(res => {
      console.log(res)
      this.setup(res);
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
    this.running = true;
  }

  setup(res){
    this.running = res.running;
    this.timeout = res.timeout / 1000;
  }

  utcStop(){
    this.service.stopUtc().subscribe(() => {
      this.service.getConfig().subscribe(res => {
        this.setup(res);
      })
    },
    err => {
      console.log(err);
    });
  }

  changeTimeout(){
    this.submitted = true;
    if(this.utcForm.controls.timeout.errors){
      return;
    }
    this.service.setTimeout(this.utcForm.controls["timeout"].value * 1000).subscribe(res => {
      this.service.getConfig().subscribe(res => {
        this.setup(res);
      })
    });
  }

}

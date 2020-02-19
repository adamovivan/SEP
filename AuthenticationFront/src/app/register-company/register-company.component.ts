import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SellerService } from '../services/services';

@Component({
  selector: 'app-register-company',
  templateUrl: './register-company.component.html',
  styleUrls: ['./register-company.component.css']
})
export class RegisterCompanyComponent implements OnInit {

  SingIn:FormGroup;
  submitted = false;
  company: any;
  public isLoged:boolean=false;
  us:any;
  file:File;

  constructor(
    private service:SellerService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
      this.SingIn = this.formBuilder.group({
        email:['',Validators.compose([Validators.required, Validators.email])],
        commonName:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        companyName:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        organization:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        location:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        stateLocation:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        country:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        usage:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        phoneNumber:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])],
        password:['',Validators.compose([Validators.required, Validators.pattern('[a-zA-Z 0-9!]+')])]

      });
  }

  get f() { return this.SingIn.controls; }

  onSubmit(event:any) {
    this.submitted = true;
    this.company = this.SingIn.getRawValue();
    let formData = new FormData();
    console.log(this.company)
    formData.append('file', this.file);
    formData.append('informations', new Blob([JSON.stringify(this.company)], {type: 'application/json'}));
    this.service.registerCompany(formData).subscribe(
      data => {
        this.router.navigateByUrl("");
    },
    err => {
      if(err.status === 200){
        this.router.navigateByUrl("");
      }
      
    });
  }


  fileChange(event) {
    this.file = event.target.files[0];
    console.log(this.file);
  }

}

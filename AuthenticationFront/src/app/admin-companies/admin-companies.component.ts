import { Component, OnInit } from '@angular/core';
import { SellerService } from '../services/services';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-companies',
  templateUrl: './admin-companies.component.html',
  styleUrls: ['./admin-companies.component.css']
})
export class AdminCompaniesComponent implements OnInit {


  companies: any;

  constructor(
    private service:SellerService,
    private formBuilder:FormBuilder,
    private router:Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.service.getCompanies().subscribe(
      data => {
            this.companies = data;
    });
  }

  approve(companuName:String){
    this.service.acceptCompany(companuName).subscribe(
      data => {
        window.location.href = "https://localhost:4200/adminCompanies";
    });
  }

  refuse(companuName:String){
    this.service.declineCompany(companuName).subscribe(
      data => {
        window.location.href = "https://localhost:4200/adminCompanies";
    });
  }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { typeWithParameters } from '@angular/compiler/src/render3/util';
import { BitcoinService } from '../service/bitcoin.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  orderId : string;
  response : string;
  constructor(private activatedRoute : ActivatedRoute, private router: Router, private service: BitcoinService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      params => {
        this.orderId = params.get('token');
        alert(this.orderId)
        this.service.success(this.orderId).subscribe(
          data => {
            this.response = data;
        });
      }
    );
  }

}

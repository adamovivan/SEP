import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BitcoinService } from '../service/bitcoin.service';

@Component({
  selector: 'app-cancel',
  templateUrl: './cancel.component.html',
  styleUrls: ['./cancel.component.css']
})
export class CancelComponent implements OnInit {

  orderId : string;
  response : string;
  constructor(private activatedRoute : ActivatedRoute, private router: Router, private service: BitcoinService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      params => {
        this.orderId = params.get('token');
        this.service.cancel(this.orderId).subscribe(
          data => {
            this.response = data;
        });
      }
    );
  }

}

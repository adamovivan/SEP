import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ShoppingCartService } from '../../service/shopping-cart.service';
import { PaymentRequest } from '../../model/payment-request.model';
import { CommonService } from 'src/app/login/service/common.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {

  displayedColumns: string[] = ['no', 'issn', 'title', 'scientificField', 'price', 'remove'];
  magazineItems = [];
  totalPrice = 0;

  constructor(private shoppingCartService: ShoppingCartService,
              private commonService: CommonService,
              private cdref: ChangeDetectorRef
              ) { }

  ngOnInit() {
    this.shoppingCartService.getShoppingCartItems().subscribe(
      res => {
        this.magazineItems = [res];
        this.totalPrice = this.calculateTotalPrice();
      },
      err => {
        console.log(err)
        this.commonService.somethingWentWrong();
      }
      
    );
  }

  ngAfterContentChecked() {
    this.cdref.detectChanges();
  }

  removeFromShoppingCart(element){
    console.log(element)
  }

  calculateTotalPrice(){
    let sum = 0;
    this.magazineItems.forEach(element => sum += element.price);
    return sum;
  }

  proceedToPayment(){
    this.shoppingCartService.pay().subscribe(res => {
      window.location.href = res.url;
    },
    err => {
      console.log(err)
    });
  }

}

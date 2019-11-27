import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonService } from '../../service/common.service';
import { ShoppingCartService } from '../../service/shopping-cart.service';
import { PaymentRequest } from '../../model/payment-request.model';

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
        this.magazineItems = res;
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
    let paymentRequest = new PaymentRequest();
    paymentRequest.totalPrice = this.totalPrice;
    paymentRequest.magazineId = 1;
    paymentRequest.paymentType = "PAYPAL";

    this.shoppingCartService.pay(paymentRequest).subscribe(res => {
      console.log(res);
    })
  }

}

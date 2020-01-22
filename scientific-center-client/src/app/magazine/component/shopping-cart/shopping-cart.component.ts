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

  displayedColumns: string[] = ['no', 'itemType', 'title', 'price', 'remove'];
  items = [];
  totalPrice = 0;

  constructor(private shoppingCartService: ShoppingCartService,
              private commonService: CommonService,
              private cdref: ChangeDetectorRef
              ) { }

  ngOnInit() {
    this.shoppingCartService.getShoppingCartItems().subscribe(
      res => {
        console.log(res)
        this.items = res;
        this.mapMagazineArticleToItem();
        this.totalPrice = this.calculateTotalPrice();
      },
      () => {
        this.commonService.somethingWentWrong();
      }
      
    );
  }

  mapMagazineArticleToItem() {
    for(let item of this.items){
      let el;
      if(item.membership.magazine != undefined){
        el = item.membership.magazine;
        item["itemType"] = "Magazine";
      }
      else if(item.membership.article != undefined){
        el = item.membership.article;
        item["itemType"] = "Article";
      }

      delete item.membership.magazine;
      delete item.membership.article;
      item.membership["item"] = el;
    }
  }

  ngAfterContentChecked() {
    this.cdref.detectChanges();
  }

  removeFromShoppingCart(element){
    this.shoppingCartService.removeFromCart(element.id).subscribe(() => {
      this.items = this.items.filter(obj => obj !== element);
      this.totalPrice = this.calculateTotalPrice();
      this.commonService.showMessage("Item successfully removed from shopping cart.");
    },
    () => {
      this.commonService.somethingWentWrong();
    });
    
  }

  calculateTotalPrice(){
    let sum = 0;
    this.items.forEach(element => sum += element.price);
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

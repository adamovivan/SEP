import { Component, OnInit } from '@angular/core';
import { MagazineService } from '../../service/magazine.service';
import { Router } from '@angular/router';
import { CommonService } from '../../service/common.service';

@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.scss']
})
export class MagazineComponent implements OnInit {

  displayedColumns: string[] = ['no', 'issn', 'title', 'scientificField', 'addToShoppingCart'];
  magazines = [];

  constructor(private magazineService: MagazineService,
              private commonService: CommonService,
              private router: Router) { }

  ngOnInit() {
    this.magazineService.getAllMagazines().subscribe(
      res => {
        this.magazines = res;
      }
    );
  }

  addToShoppingCart(element){
    this.magazineService.addMagazineToShoppingCart(element.id).subscribe(res => {
       this.commonService.showMessage("Item successfully added.");
    },
    err => {
       console.log(err)
       this.commonService.somethingWentWrong();
    });
  }
  
  showShoppingCart(){
    this.router.navigateByUrl('/shopping-cart');
  }

}
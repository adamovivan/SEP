import { Component, OnInit } from '@angular/core';
import { MagazineService } from '../../service/magazine.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonService } from '../../service/common.service';

@Component({
  selector: 'app-magazines',
  templateUrl: './magazines.component.html',
  styleUrls: ['./magazines.component.scss']
})
export class MagazinesComponent implements OnInit {

  displayedColumns: string[] = ['no', 'issn', 'title', 'scientificFields', 'show'];
  magazines = [];

  constructor(private magazineService: MagazineService,
              private commonService: CommonService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.magazineService.getAllMagazines().subscribe(
      res => {
        this.magazines = res;
      },
      () => {
        this.commonService.somethingWentWrong();
      }
    );
  }

  addToShoppingCart(element){
    this.magazineService.addMagazineToShoppingCart(element.id).subscribe(() => {
       this.commonService.showMessage("Item successfully added.");
    },
    () => {
       this.commonService.somethingWentWrong();
    });
  }

  showMagazine(element){
    this.router.navigate(['magazine/' + element.id])
  }
  
  showShoppingCart(){
    this.router.navigate(['/shopping-cart']);
  }
}

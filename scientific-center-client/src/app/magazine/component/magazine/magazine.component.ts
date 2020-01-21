import { Component, OnInit } from '@angular/core';
import { MagazineService } from '../../service/magazine.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonService } from '../../service/common.service';

@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.scss']
})
export class MagazineComponent implements OnInit {

  displayedColumns: string[] = ['no', 'title', 'scientificFields', 'show', 'addToShoppingCart'];
  magazine = {};
  articles = [];
  magazineId: string;

  constructor(private magazineService: MagazineService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.magazineId = this.route.snapshot.paramMap.get("id");
    this.magazine["title"] = '';
    this.magazine["issn"] = '';
    this.magazineService.getMagazine(this.magazineId).subscribe(res => {
      this.magazine = res;
      this.articles = res.articles;
    },
    () => {
      this.commonService.somethingWentWrong();
    })
  }

  addToShoppingCart(element){
    this.magazineService.addArticleToShoppingCart(element.id).subscribe(res => {
       this.commonService.showMessage("Article successfully added.");
    },
    err => {
       console.log(err)
       this.commonService.somethingWentWrong();
    });
  }

  addMagazineToCart(){
    this.magazineService.addMagazineToShoppingCart(this.magazineId).subscribe(() => {
      this.commonService.showMessage("Magazine successfully added.");
   },
   err => {
      console.log(err)
      this.commonService.somethingWentWrong();
   });
  }

  showArticle(element){
    console.log(element);
  }

  showShoppingCart(){
    this.router.navigate(['/shopping-cart']);
  }
}
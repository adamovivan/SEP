import { Component, OnInit } from '@angular/core';
import { CommonService } from '../../service/common.service';
import { ArticleService } from '../../service/article.service';
import { Router } from '@angular/router';
import { MagazineService } from '../../service/magazine.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {

  displayedColumns: string[] = ['no', 'title', 'scientificFields', 'subscriptionType', 'show', 'addToShoppingCart'];
  articles = [];

  constructor(private articleService: ArticleService,
    private commonService: CommonService,
    private magazineService: MagazineService,
    private router: Router) { }

  ngOnInit() {
    this.articleService.getAllArticles().subscribe(res => {
      console.log(res)
      this.articles = res;
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

  showArticle(element){
    this.router.navigate(['/article/' + element.id]);
  }

  showShoppingCart(){
    this.router.navigate(['/shopping-cart']);
  }

}

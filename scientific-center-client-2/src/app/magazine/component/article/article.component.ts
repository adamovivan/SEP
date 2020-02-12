import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonService } from '../../service/common.service';
import { ArticleService } from '../../service/article.service';
import { MagazineService } from '../../service/magazine.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  articleId: string;
  article = {};

  constructor(private commonService: CommonService,
    private route: ActivatedRoute,
    private router: Router,
    private articleService: ArticleService,
    private magazineService: MagazineService) { }

  ngOnInit() {
    this.articleId = this.route.snapshot.paramMap.get("id");
    this.article["title"] = '';
    this.articleService.getArticle(this.articleId).subscribe(res => {
      console.log(res);
      this.article = res;
    });
  }

  addArticleToCart(){
    this.magazineService.addArticleToShoppingCart(this.articleId).subscribe(() => {
      this.commonService.showMessage("Article successfully added.");
   },
   err => {
      console.log(err)
      this.commonService.somethingWentWrong();
   });
  }

  showShoppingCart(){
    this.router.navigate(['/shopping-cart']);
  }

}

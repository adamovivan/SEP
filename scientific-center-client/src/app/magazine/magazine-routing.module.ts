import { NgModule } from '@angular/core';
import { MagazineComponent } from './component/magazine/magazine.component';
import { Routes, RouterModule } from '@angular/router';
import { ShoppingCartComponent } from './component/shopping-cart/shopping-cart.component';
import { MagazinesComponent } from './component/magazines/magazines.component';
import { ArticleComponent } from './component/article/article.component';
import { ArticlesComponent } from './component/articles/articles.component';

const routes: Routes = [
  {
    path: 'magazines', 
    component: MagazinesComponent
  },
  {
    path: 'magazine/:id', 
    component: MagazineComponent,
  },
  {
    path: 'shopping-cart',
    component: ShoppingCartComponent
  },
  {
    path: 'article/:id',
    component: ArticleComponent
  },
  {
    path: 'author/articles',
    component: ArticlesComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MagazineRoutingModule { }

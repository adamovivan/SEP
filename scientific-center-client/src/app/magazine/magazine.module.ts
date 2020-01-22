import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MagazineComponent } from './component/magazine/magazine.component';
import { MagazineService } from './service/magazine.service';
import { MagazineRoutingModule } from './magazine-routing.module';
import { MatTableModule, MatIconModule,  MatButtonModule, MatSnackBarModule, MatFormFieldModule, MatSelectModule, MatCardModule } from '@angular/material';
import { ShoppingCartComponent } from './component/shopping-cart/shopping-cart.component';
import { ShoppingCartService } from './service/shopping-cart.service';
import { CommonService } from './service/common.service';
import { MagazinesComponent } from './component/magazines/magazines.component';
import { ArticleComponent } from './component/article/article.component';
import { ArticlesComponent } from './component/articles/articles.component';

@NgModule({
  declarations: [
    MagazineComponent,
    ShoppingCartComponent,
    MagazinesComponent,
    ArticleComponent,
    ArticlesComponent
  ],
  imports: [
    CommonModule,
    MagazineRoutingModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,
    MatSnackBarModule,
    MatSelectModule,
    MatCardModule
  ],
  providers: [
    MagazineService,
    ShoppingCartService,
    CommonService
  ]
})
export class MagazineModule { }

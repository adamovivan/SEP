import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MagazineComponent } from './component/magazine/magazine.component';
import { MagazineService } from './service/magazine.service';
import { MagazineRoutingModule } from './magazine-routing.module';
import { MatTableModule, MatIconModule,  MatButtonModule, MatSnackBarModule } from '@angular/material';
import { ShoppingCartComponent } from './component/shopping-cart/shopping-cart.component';
import { ShoppingCartService } from './service/shopping-cart.service';
import { CommonService } from './service/common.service';

@NgModule({
  declarations: [
    MagazineComponent,
    ShoppingCartComponent
  ],
  imports: [
    CommonModule,
    MagazineRoutingModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  providers: [
    MagazineService,
    CommonService,
    ShoppingCartService
  ]
})
export class MagazineModule { }

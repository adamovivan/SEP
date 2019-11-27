import { NgModule } from '@angular/core';
import { MagazineComponent } from './component/magazine/magazine.component';
import { Routes, RouterModule } from '@angular/router';
import { ShoppingCartComponent } from './component/shopping-cart/shopping-cart.component';

const routes: Routes = [
  {
    path: 'magazines', 
    component: MagazineComponent
  },
  {
    path: 'shopping-cart',
    component: ShoppingCartComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MagazineRoutingModule { }

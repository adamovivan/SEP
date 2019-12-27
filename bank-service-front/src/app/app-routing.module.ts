import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaymentSuccessComponent } from './component/payment-success/payment-success.component';
import { PaymentFailedComponent } from './component/payment-failed/payment-failed.component';
import { PaymentErrorComponent } from './component/payment-error/payment-error.component';


const routes: Routes = [
  { path: 'payment-success', component: PaymentSuccessComponent },
  { path: 'payment-failed', component: PaymentFailedComponent },
  { path: 'payment-error', component: PaymentErrorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

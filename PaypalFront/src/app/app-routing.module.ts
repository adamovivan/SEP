import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CancelComponent } from './cancel/cancel.component';
import { SuccessComponent } from './success/success.component';
import { UserDataComponent } from './user-data/user-data.component';
import { SubscriptionPlanComponent } from './subscription-plan/subscription-plan.component';
import { CancelAgreementComponent } from './cancel-agreement/cancel-agreement.component';
import { SuccessAgreementComponent } from './success-agreement/success-agreement.component';


const routes: Routes = [
  {
    path: 'success',
    component: SuccessComponent
  },
  {
    path: 'cancel',
    component: CancelComponent
  },
  {
    path: 'input/:username',
    component: UserDataComponent
  },
  {
    path: 'subscriptionPlan/:token',
    component: SubscriptionPlanComponent
  },
  {
    path: 'cancelAgreement',
    component: CancelAgreementComponent
  },
  {
    path: 'successAgreement',
    component: SuccessAgreementComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

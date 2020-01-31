import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SuccessComponent } from './success/success.component';
import { CancelComponent } from './cancel/cancel.component';
import { UserDataComponent } from './user-data/user-data.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { SubscriptionPlanComponent } from './subscription-plan/subscription-plan.component';
import { CancelAgreementComponent } from './cancel-agreement/cancel-agreement.component';
import { SuccessAgreementComponent } from './success-agreement/success-agreement.component';

@NgModule({
  declarations: [
    AppComponent,
    SuccessComponent,
    CancelComponent,
    UserDataComponent,
    SubscriptionPlanComponent,
    CancelAgreementComponent,
    SuccessAgreementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

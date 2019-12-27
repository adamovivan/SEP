import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PaymentSuccessComponent } from './component/payment-success/payment-success.component';
import { PaymentFailedComponent } from './component/payment-failed/payment-failed.component';
import { PaymentErrorComponent } from './component/payment-error/payment-error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';

@NgModule({
  declarations: [
    AppComponent,
    PaymentSuccessComponent,
    PaymentFailedComponent,
    PaymentErrorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

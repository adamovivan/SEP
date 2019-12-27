import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PaymentDetailsComponent } from './component/payment-details/payment-details.component';
import { MaterialModule } from './material.module';
import { PaymentService } from './service/payment.service';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    PaymentDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule
  ],
  providers: [PaymentService],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Shared } from './services/token';
import { TypePaymentsComponent } from './type-payments/type-payments.component';
import { PayingTypeComponent } from './paying-type/paying-type.component';
import { SubscriptionPlansComponent } from './subscription-plans/subscription-plans.component';
import { UtcConfigComponent } from './utc-config/utc-config.component';
import { SubscriptionAgreementComponent } from './subscription-agreement/subscription-agreement.component';
import { AuthTokenInterceptor } from './interceptors/auth-token.interceptor';
import { AuthenticationService } from './services/authentication.service';
import { RegisterCompanyComponent } from './register-company/register-company.component';
import { AdminCompaniesComponent } from './admin-companies/admin-companies.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    TypePaymentsComponent,
    PayingTypeComponent,
    SubscriptionPlansComponent,
    UtcConfigComponent,
    SubscriptionPlansComponent,
    SubscriptionAgreementComponent,
    RegisterCompanyComponent,
    AdminCompaniesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [Shared,
    AuthenticationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthTokenInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }

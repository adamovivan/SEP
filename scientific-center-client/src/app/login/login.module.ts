import { NgModule } from '@angular/core';
import { LoginComponent } from './component/login/login.component';
import { AuthenticationService } from './service/authentication.service';
import { CommonService } from './service/common.service';
import { MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule, MatToolbarModule, MatCardModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginRoutingModule } from './login-routing.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthTokenInterceptor } from './interceptors/auth-token.interceptor';


@NgModule({
  declarations: [LoginComponent],
  imports: [
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    FormsModule,
    LoginRoutingModule,
    MatInputModule,
    MatToolbarModule,
    MatCardModule
  ],
  providers: [
    CommonService,
    AuthenticationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthTokenInterceptor,
      multi: true
    },
  ]
})
export class LoginModule { }

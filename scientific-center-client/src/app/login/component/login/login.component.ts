import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';
import { LoginDTO } from '../../dto/login.dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonService } from '../../service/common.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  user: LoginDTO = new LoginDTO();
  hide = true;

  loginForm: FormGroup;

  constructor(public authService: AuthenticationService,
              public router: Router,
              private formBuilder: FormBuilder,
              private commonService: CommonService) { }

  ngOnInit() {
    this.authService.clearStorage();

    this.loginForm = this.formBuilder.group({
      password: ['', Validators.required],
      username: ['', Validators.required]
    });
  }

  onLoginSubmit() {

    if(!this.loginForm.valid){
      return;
    }

    this.authService.login(this.user).subscribe(data => {
      this.authService.saveToken(data.accessToken);

      const roles = this.authService.getRoles();

      if (roles.length > 0) {
        this.authService.loginSubject.next(roles);
        this.router.navigate(['magazines']);
      }
  
    },
    error => {
        if (error['status'] === 403) {
          this.commonService.showMessage("Wrong username or password");
        }
      }
    );
  }

}

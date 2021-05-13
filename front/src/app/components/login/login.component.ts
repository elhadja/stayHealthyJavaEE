import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/api/dto/loginDTO';
import { UserApiService } from 'src/app/api/user-api.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  error: string;
  readonly pswMinLength = 8;
  readonly pswMaxLength = 16;
  readonly loginFormConstants = {
    email: "email",
    password: "password"
  };

  constructor(private readonly userApiService: UserApiService,
    private readonly router: Router,
    private readonly userService: UserService)
  {
    this.loginForm = new FormGroup({
      email: new FormControl('front@gmail.com', [Validators.required, Validators.email]),
      password: new FormControl('aaaaaaaa', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
      userType: new FormControl('', [Validators.required])
    });

    this.error = '';
  }

  ngOnInit(): void {
    this.error = '';
  }

  public onSubmit(): void {
    let input: LoginDTO = {
      email: this.loginForm.get(this.loginFormConstants.email)?.value,
      password: this.loginForm.get(this.loginFormConstants.password)?.value,
    }
    this.userApiService.login(input).subscribe(
      (res) => {
        console.log(res);
          this.userService.setToken(res.token);
          // TODO get userType from back or find better solution
          if (this.loginForm.get('userType')?.value === 'patient') {
            this.router.navigate(['/account']);
          } else {
            this.router.navigate(['/doctor']);
          }
      },
      (error) => {
        this.error = error.error.message;
      }
    );
  }

  get email() {return this.loginForm.get(this.loginFormConstants.email)}
  get password() {return this.loginForm.get(this.loginFormConstants.password)}
  get userType() {return this.loginForm.get('userType')}
}

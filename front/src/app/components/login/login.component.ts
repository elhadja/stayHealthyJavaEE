import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/dto/loginDTO';
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

  constructor(private readonly router: Router,
    private readonly userService: UserService)
  {
    this.loginForm = new FormGroup({
      email: new FormControl('front@gmail.com', [Validators.required, Validators.email]),
      password: new FormControl('aaaaaaaa', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
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
    this.userService.login(input).subscribe(
      (res) => {
          this.userService.setToken(res.token, res.id, res.userType);
          if (res.userType === 0) {
            this.router.navigate(['/patient']);
          } else {
            this.router.navigate(['/doctor']);
          }
      }
     );
  }

  get email() {return this.loginForm.get(this.loginFormConstants.email)}
  get password() {return this.loginForm.get(this.loginFormConstants.password)}
}

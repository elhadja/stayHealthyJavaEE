import { ValueConverter } from '@angular/compiler/src/render3/view/template';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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

  constructor(private readonly userApiService: UserApiService) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(16)])
    });
    this.error = '';
   }

  ngOnInit(): void {
    this.error = '';
  }

  public onSubmit(): void {
    let input: LoginDTO = {
      email: this.loginForm.get("email")?.value,
      password: this.loginForm.get("password")?.value,
    }
    this.userApiService.login(input).subscribe(
      (res) => {
          this.error = '';
          console.log("=> ",this.error)
      },
      (error) => {
        this.error = error.error.message;
      }
    );
  }

  get email() {return this.loginForm.get("email")}
  get password() {return this.loginForm.get("password")}
}

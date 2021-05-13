import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { signupDTO } from 'src/app/api/dto/signupDTO';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  input: signupDTO;
  signupForm: FormGroup;

  public constructor(private readonly userService: UserService,
    private readonly fb: FormBuilder) {
    this.input = {};
    this.signupForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]],
      passwordAgain: ['', [Validators.required,
         (control: AbstractControl): {[key: string]: any} | null => {
          return (false) ? {spec: {value: control.value}} : null; // TODO fix the custom validator
         }]],
      address: this.fb.group({
        road: [''],
        postalCode: [''],
        city: ['']
      })
    });
  }

  ngOnInit(): void {
  }

  onSignup(): void {
    this.input = {
      firstName: this.signupForm.get("firstName")?.value,
      lastName: this.signupForm.get("lastName")?.value,
      email: this.signupForm.get("email")?.value,
      password: this.signupForm.get("password")?.value,
      address: {
        road: this.signupForm.get("address")?.get("road")?.value,
        postalCode: this.signupForm.get("address")?.get("postalCode")?.value,
        city: this.signupForm.get("address")?.get("city")?.value,
      }
    }

    // TODO manage token
    this.userService.signup(this.input).subscribe(
      (res) => {
        console.log(res);
      },
      (error) => {
        console.log(error);
      }
    )
  }

  get firstName() {return this.signupForm.get("firstName")}
  get lastName() {return this.signupForm.get("lastName")}
  get email() {return this.signupForm.get("email")}
  get password() {return this.signupForm.get("password")}
  get passwordAgain() {return this.signupForm.get("passwordAgain")}
}

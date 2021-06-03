import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { signupDTO } from 'src/app/api/dto/signupDTO';
import { MessageService } from 'src/app/services/Message.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  input: signupDTO;
  signupForm: FormGroup;
  readonly pswMinLength = 8;
  readonly pswMaxLength = 16;

  public constructor(private readonly userService: UserService,
    private readonly fb: FormBuilder,
    private readonly messageService: MessageService,
    private readonly router: Router) {
    this.input = {};
    this.signupForm = this.fb.group({
      firstName: ['bah', Validators.required],
      lastName: ['elhadj', Validators.required],
      email: ['elhadja007@gmail.com', [Validators.required, Validators.email]],
      password: ['aaaaaaaa', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]],
      passwordAgain: ['aaaaaaaa', [Validators.required,
         (control: AbstractControl): {[key: string]: any} | null => {
          return (false) ? {spec: {value: control.value}} : null; // TODO fix the custom validator
         }]],
      address: this.fb.group({
        road: [''],
        postalCode: [''],
        city: ['']
      }),
      userType: ['0', [Validators.required]]
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
      address: !this.isAddressSetted() ? undefined : {
        road: this.signupForm.get("address")?.get("road")?.value,
        postalCode: this.signupForm.get("address")?.get("postalCode")?.value,
        city: this.signupForm.get("address")?.get("city")?.value,
      },
      userType: this.signupForm.get("userType")?.value
    }

    this.userService.signup(this.input).subscribe(
      (res) => {
        this.router.navigate(['/login']);
      },
      (error) => {
        this.messageService.showMessage(error.error.message, 2);
      }
    )
  }

  private isAddressSetted(): boolean {
    return this.signupForm.get("address")?.get("road")?.value != ""
          || this.signupForm.get("address")?.get("postalCode")?.value != ""
          || this.signupForm.get("address")?.get("city")?.value != "";
  }

  get firstName() {return this.signupForm.get("firstName")}
  get lastName() {return this.signupForm.get("lastName")}
  get email() {return this.signupForm.get("email")}
  get password() {return this.signupForm.get("password")}
  get passwordAgain() {return this.signupForm.get("passwordAgain")}
  get userType() {return this.signupForm.get('userType')}
}

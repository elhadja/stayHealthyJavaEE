import { Component, OnInit } from '@angular/core';
import { signupDTO } from 'src/app/api/dto/signupDTO';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  input: signupDTO;

  ngOnInit(): void {
  }

  public constructor(private readonly userService: UserService) {
    this.input = {
      firstName: "firstName",
      lastName: "lastName",
      email: "emailfront",
      password: "password",
    }
  }

  public onSignup(): void {
    this.input = {
      firstName: "firstName",
      lastName: "lastName",
      email: "emailfront",
      password: "password",
    }

    this.userService.signup(this.input).subscribe(res => {
      console.log(res);
    })
  }
}

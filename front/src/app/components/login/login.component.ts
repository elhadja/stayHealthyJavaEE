import { Component, OnInit } from '@angular/core';
import { LoginDTO } from 'src/app/api/dto/loginDTO';
import { UserApiService } from 'src/app/api/user-api.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private readonly userApiService: UserApiService) { }

  ngOnInit(): void {
  }

  public onLogin(): void {
    let input: LoginDTO = {
      email: "email",
      password: "password"
    }
    this.userApiService.login(input)
        .subscribe((res) => {
          console.log(res);
        });
  }

}

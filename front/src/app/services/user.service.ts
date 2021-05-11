import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { signupDTO } from '../api/dto/signupDTO';
import { UserApiService } from '../api/user-api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private readonly userApiService: UserApiService) { }

  public signup(input: signupDTO): Observable<any> {
    return this.userApiService.signup(input);
  }
}

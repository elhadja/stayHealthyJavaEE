import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { signupDTO } from '../api/dto/signupDTO';
import { UserApiService } from '../api/user-api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private token: string;

  constructor(private readonly userApiService: UserApiService) { 
    const tmp = localStorage.getItem('token');
    if (tmp != null) {
      this.token = tmp;
    } else {
      this.token = '';
    }
  }

  public signup(input: signupDTO): Observable<any> {
    return this.userApiService.signup(input);
  }

  public setToken(token: string): void {
    localStorage.setItem('token', token);
    this.token = token;
    this.userApiService.setHttpOptions(token);
 }
  
  public isLogged(): boolean {
    return this.token.length > 0;
  }
}

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
  private userId: number;

  constructor(private readonly userApiService: UserApiService) { 
    this.userId = 0;
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

  public setToken(token: string, id: number): void {
    localStorage.setItem('token', token);
    this.userId = id;
    this.token = token;
    this.userApiService.setHttpOptions(token);
 }
  
  public isLogged(): boolean {
    return this.token.length > 0;
  }

  public getUserId(): number {
    return this.userId;
  }
}

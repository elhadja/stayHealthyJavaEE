import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDTO } from '../dto/loginDTO';
import { signupDTO } from '../dto/signupDTO';
import { updateCredentialsOutputDTO } from '../dto/UpdateCredentialsOutputDTO';
import { UpdatePasswordRequestDTO } from '../dto/UpdatePasswordRequestDTO';
import { API } from './api';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private token: string;
  private userId: number;

  private readonly baseUri: string;

  constructor(private api: API) { 
    this.userId = 0;
    const tmp = localStorage.getItem('token');
    if (tmp != null) {
      this.token = tmp;
    } else {
      this.token = '';
    }
    this.baseUri = "/users";
  }

  public signup(input: signupDTO): Observable<any> {
    return this.api.post(this.baseUri, input);
  }

  public login(input: LoginDTO): Observable<any> {
    return this.api.post(this.baseUri + "/login", input);
  }

  public updateCredentials(input: updateCredentialsOutputDTO, id: number): Observable<any> {
    return this.api.put(this.baseUri + "/" + id + "/credentials", input);
  }

  public updatePassword(input: UpdatePasswordRequestDTO, userId: number, newPassword: string): Observable<any> {
    return this.api.put(this.baseUri + "/" + userId + "/" + "password", input);
  }


  public getById(id: number = this.userId): Observable<any> {
    return this.api.get(this.baseUri + "/" + id);
  }


  public setToken(token: string, id: number): void {
    localStorage.setItem('token', token);
    localStorage.setItem('id', id + "");
    this.userId = id;
    this.token = token;
    this.api.setHttpOptions(token);
 }
  
  public isLogged(): boolean {
    return this.token.length > 0;
  }

  public getUserId(): number {
    const id = localStorage.getItem('id');
    if (id != null) {
      this.userId = +id;
    }
    return this.userId;
  }
}

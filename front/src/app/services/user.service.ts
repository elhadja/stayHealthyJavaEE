import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDTO } from '../api/dto/loginDTO';
import { signupDTO } from '../api/dto/signupDTO';
import { updateCredentialsOutputDTO } from '../api/dto/UpdateCredentialsOutputDTO';
import { UpdatePasswordRequestDTO } from '../api/dto/UpdatePasswordRequestDTO';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private token: string;
  private userId: number;

  private readonly baseUri: string;
  private httpOptions: {
    headers?: HttpHeaders;
  };


  constructor(private httpClient: HttpClient) { 
    this.userId = 0;
    const tmp = localStorage.getItem('token');
    if (tmp != null) {
      this.token = tmp;
    } else {
      this.token = '';
    }
    this.baseUri = "http://localhost:8080/users";
      this.httpOptions = {
          headers: new HttpHeaders({
            'Content-Type':  'application/json',
            Authorization: ''
          })
      };
  }

  public signup(input: signupDTO): Observable<any> {
    return this.httpClient.post(this.baseUri, input);
  }

  public login(input: LoginDTO): Observable<any> {
    return this.httpClient.post(this.baseUri + "/login", input);
  }

  public updateCredentials(input: updateCredentialsOutputDTO, id: number): Observable<any> {
    console.log(this.httpOptions)
    return this.httpClient.put(this.baseUri + "/" + id + "/credentials", input, this.httpOptions);
  }

  public updatePassword(input: UpdatePasswordRequestDTO, userId: number, newPassword: string): Observable<any> {
    return this.httpClient.put(this.baseUri + "/" + userId + "/" + "password", input,this.httpOptions);
  }


  public getById(id: number): Observable<any> {
    return this.httpClient.get(this.baseUri + "/" + id, this.httpOptions);
  }


  public setToken(token: string, id: number): void {
    localStorage.setItem('token', token);
    this.userId = id;
    this.token = token;
    this.setHttpOptions(token);
 }
  
  public isLogged(): boolean {
    return this.token.length > 0;
  }

  public getUserId(): number {
    return this.userId;
  }

  private setHttpOptions(token: string) {
    this.httpOptions.headers = this.httpOptions.headers?.set("Authorization", "Bearer " + token);
  }
}

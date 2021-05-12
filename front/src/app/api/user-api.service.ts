import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDTO } from './dto/loginDTO';
import { signupDTO } from './dto/signupDTO';

@Injectable({
  providedIn: 'root'
})
export class UserApiService {
  private readonly baseUri: string;

  constructor(private readonly httpClient: HttpClient) {
    this.baseUri = "http://localhost:8080/users";
   }

  public signup(input: signupDTO): Observable<any> {
    return this.httpClient.post(this.baseUri, input);
  }

  public login(input: LoginDTO): Observable<any> {
    return this.httpClient.post(this.baseUri + "/login", input);
  }
}

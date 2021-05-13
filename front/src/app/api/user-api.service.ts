import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDTO } from './dto/loginDTO';
import { signupDTO } from './dto/signupDTO';
import { updateCredentialsOutputDTO } from './dto/UpdateCredentialsOutputDTO';

@Injectable({
  providedIn: 'root'
})
export class UserApiService {
  private readonly baseUri: string;
  private httpOptions: {
    headers?: HttpHeaders;
  };

  constructor(private readonly httpClient: HttpClient) {
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

  public setHttpOptions(token: string) {
    this.httpOptions.headers = this.httpOptions.headers?.set("Authorization", "Bearer " + token);
  }
}

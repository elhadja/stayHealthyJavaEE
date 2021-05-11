import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { signupDTO } from './dto/signupDTO';

@Injectable({
  providedIn: 'root'
})
export class UserApiService {
  private readonly baseUri = "http://localhost:8080/users";

  constructor(private readonly httpClient: HttpClient) { }

  public signup(input: signupDTO): Observable<any> {
    return this.httpClient.post(this.baseUri, input);
  }
}

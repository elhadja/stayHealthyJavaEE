import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class API {
    private baseURI: string;
    private httpOptions: {
        headers?: HttpHeaders;
    };

    constructor(private httpClient: HttpClient) {
        this.baseURI = "http://localhost:8080";
        this.httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                Authorization: ''
            })
        };
    }

    public post(uri: string, body: any): Observable<any> {
        return this.httpClient.post(this.baseURI + uri, body, this.httpOptions);
    }

    public put(uri: string, body: any): Observable<any> {
        return this.httpClient.put(this.baseURI + uri, body, this.httpOptions);
    }

    public get(uri: string): Observable<any> {
        return this.httpClient.get(this.baseURI + uri, this.httpOptions);
    }

    public setHttpOptions(token: string) {
        this.httpOptions.headers = this.httpOptions.headers?.set("Authorization", "Bearer " + token);
    }
}
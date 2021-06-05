import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ObservableInput, throwError } from "rxjs";
import { catchError } from 'rxjs/operators'
import { MessageService } from "./Message.service";

@Injectable()
export class API {
    private baseURI: string;
    private httpOptions: {
        headers?: HttpHeaders;
    };

    constructor(private httpClient: HttpClient, private messageService: MessageService) {
        this.baseURI = "http://localhost:8080";
        this.httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                Authorization: ''
            })
        };
    }

    public post(uri: string, body: any): Observable<any> {
        return this.httpClient.post(this.baseURI + uri, body, this.httpOptions)
                    .pipe(
                        catchError(this.f)
                    );
    }


    public put(uri: string, body: any): Observable<any> {
        return this.httpClient.put(this.baseURI + uri, body, this.httpOptions).pipe(
            catchError(this.f)
        );
    }

    public get(uri: string): Observable<any> {
        return this.httpClient.get(this.baseURI + uri, this.httpOptions).pipe(
            catchError(this.f)
        );
    }
    public delete(uri: string): Observable<any> {
        return this.httpClient.delete(this.baseURI + uri, this.httpOptions).pipe(
            catchError(this.f)
        );
    }

    public setHttpOptions(token: string) {
        this.httpOptions.headers = this.httpOptions.headers?.set("Authorization", "Bearer " + token);
    }

    private f = (error: HttpErrorResponse): Observable<never> => {
        this.messageService.showMessage(error.error.message, 2);
        return throwError(error);
    }
}
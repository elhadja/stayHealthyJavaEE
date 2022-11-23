import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ObservableInput, throwError } from "rxjs";
import { catchError } from 'rxjs/operators'
import { environment } from "src/environments/environment";
import { MessageService } from "./Message.service";

@Injectable()
export class API {
    private baseURI: string;
    private httpOptions: {
        headers?: HttpHeaders;
    };

    constructor(private httpClient: HttpClient, private messageService: MessageService) {
        this.baseURI = environment.api_base_uri;
        this.httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                Authorization: ''
            })
        };

        /*
        let token = localStorage.getItem('token');
        if (token !== null) {
            this.setHttpOptions(token);
        }
        */
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
        if (error.status === 401 && error.error === undefined) {
            this.messageService.showMessage("Votre session à expiré", 2);
        } else {
            this.messageService.showMessage(error.error.message, 2);
        }

        return throwError(error);
    }
}
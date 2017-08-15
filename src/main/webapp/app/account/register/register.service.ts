import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient } from "@angular/common/http";

@Injectable()
export class Register {

    constructor(private http: HttpClient) {}

    save(account: any): Observable<any> {
        return this.http.post('api/register', account, { responseType: 'text' });
    }
}

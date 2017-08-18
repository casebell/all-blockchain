import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient } from "@angular/common/http";

@Injectable()
export class PasswordResetInitService {

    constructor(private http: HttpClient) {}

    save(mail: string): Observable<any> {
        return this.http.post('api/account/reset_password/init', mail,{responseType:'text'});
    }
}

import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { API_SERVER_URL } from "../../app.constants";
import { Observable } from "rxjs/Observable";

@Injectable()
export class CoinSidenavService {

  private resourceUrl = API_SERVER_URL;
  
  constructor(private http: HttpClient) { 
 
  }

  findAll() :Observable<any> {
    return this.http.get(`${this.resourceUrl}/coins`);
  }
}

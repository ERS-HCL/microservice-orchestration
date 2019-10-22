import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { catchError, switchMap, startWith } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private _http: HttpClient) { }

  getData(url){
    return this._http.get(url).pipe(catchError(this.handleError));
  }

  postData(url, body, headers){
    let httpHeaders = headers ? headers : { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }
    return this._http.post(url, body, headers).pipe(catchError(this.handleError));
  }

  putData(url, body){
    return this._http.put(url, JSON.stringify(body), { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }).pipe(catchError(this.handleError));
  }

  deleteData(url){
    return this._http.delete(url).pipe(catchError(this.handleError));
  }

  public handleError(err: HttpErrorResponse) {
    let errMsg = '';
    if (err.error instanceof Error) {
      errMsg = err.error.message;
    } else {
      errMsg = err.error;
    }
    return throwError(errMsg);
  }
}

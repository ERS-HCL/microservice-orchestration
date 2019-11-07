import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http.service';
import { uiConfig } from '../ui-conf';
import { MatSnackBar } from "@angular/material";
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-check-in',
  templateUrl: './check-in.component.html',
  styleUrls: ['./check-in.component.css']
})
export class CheckInComponent implements OnInit {
  apiPoint: string;
  checkin: string;
  durationInSeconds = 5;
  constructor(private _httpSvc: HttpService,private snackBar: MatSnackBar) {
    const { protocol, host } = uiConfig;
    this.apiPoint = `${protocol}://${host}`;
  }


  checkIn() {

    
    //const headers = new HttpHeaders().set('Content-Type', 'application/fhir+json;charset=UTF-8');

    this._httpSvc.postData(`${this.apiPoint}:${uiConfig.apiConfig.bp.port}/${uiConfig.apiConfig.bp.module1}`,
      this.checkin, { responseType: 'text' }).subscribe((resp: any) => {
        const response = resp;
        console.log(response);
        this.snackBar.open("Successfully checked-in", "", {
          duration: 2000,
       });
      }, error => {
        console.log(error);
        this.snackBar.open("ERROR not able to save", "", {
          duration: 3000,
       });
      });


  }



  ngOnInit() {
    this.checkin="urn:uuid:3920d39a-ec8e-4577-b6f3-98d99468ede8";
  }

}

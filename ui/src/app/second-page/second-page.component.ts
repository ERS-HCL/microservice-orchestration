import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http.service';
import { uiConfig } from '../ui-conf';
import { patientdata } from '../mock-data';
import { MatSnackBar } from "@angular/material";



import { HttpParams, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-second-page',
  templateUrl: './second-page.component.html',
  styleUrls: ['./second-page.component.css']
})
export class SecondPageComponent implements OnInit {
  apiPoint: string;
  str: string;
  durationInSeconds = 5;


  constructor(private _httpSvc: HttpService,private snackBar: MatSnackBar) {
    const { protocol, host } = uiConfig;
    this.apiPoint = `${protocol}://${host}`;
  }



  pushMe() {

    
    const headers = new HttpHeaders().set('Content-Type', 'application/fhir+json;charset=UTF-8');

    this._httpSvc.postData(`${this.apiPoint}:${uiConfig.apiConfig.patient.port}/${uiConfig.apiConfig.patient.module}`,
      this.str, { headers: headers, responseType: 'text' }).subscribe((resp: any) => {
        const response = resp;
        console.log(response);
        this.snackBar.open("Successfully Saved", "", {
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

    this.str= JSON.stringify(patientdata);
    

  }

}

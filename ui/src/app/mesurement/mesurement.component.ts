import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http.service';
import { uiConfig } from '../ui-conf';
import { MatSnackBar } from "@angular/material";
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-mesurement',
  templateUrl: './mesurement.component.html',
  styleUrls: ['./mesurement.component.css']
})
export class MesurementComponent implements OnInit {
  apiPoint: string;
  bpvalue: string;
  durationInSeconds = 5;
  constructor(private _httpSvc: HttpService,private snackBar: MatSnackBar) {
    const { protocol, host } = uiConfig;
    this.apiPoint = `${protocol}://${host}`;
  }
  measurebp() {

    
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    this._httpSvc.postData(`${this.apiPoint}:${uiConfig.apiConfig.bp.port}/${uiConfig.apiConfig.bp.module2}`,
      this.bpvalue, { headers: headers,responseType: 'text' }).subscribe((resp: any) => {
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
    this.bpvalue="{\"patientId\": \"urn:uuid:3920d39a-ec8e-4577-b6f3-98d99468ede8\", \"bpValue\": \"80\"}";

  }

}

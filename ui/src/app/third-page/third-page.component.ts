import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http.service';
import { uiConfig } from '../ui-conf';
import { MatSnackBar } from "@angular/material";


@Component({
  selector: 'app-third-page',
  templateUrl: './third-page.component.html',
  styleUrls: ['./third-page.component.css']
})
export class ThirdPageComponent implements OnInit {

  apiPoint: string;
  str: string;

  constructor(private _httpSvc: HttpService,private snackBar: MatSnackBar) { 
    const { protocol, host } = uiConfig;
    this.apiPoint = `${protocol}://${host}`;
  }

  ngOnInit() {
    this._httpSvc.getData(`${this.apiPoint}:${uiConfig.apiConfig.patient.port}/${uiConfig.apiConfig.patient.module}`).subscribe((resp: any) => {
      const response = resp;
      console.log(response);
   //   this.str=JSON.stringify(resp);
      this.str=response.entry;


    }, error => {
      console.log(error);
      this.snackBar.open("ERROR not able to Retrieve", "", {
        duration: 3000,
     });
  });
  }

  displayedColumns: string[] = ['sno','fullUrl', 'name', 'gender', 'phone'];
  dataSource = this.str;

}

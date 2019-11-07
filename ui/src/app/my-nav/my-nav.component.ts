import { Component,  ChangeDetectorRef, OnDestroy } from '@angular/core';
import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import {MediaMatcher} from '@angular/cdk/layout';
import { Router } from '@angular/router';


@Component({
  selector: 'my-nav',
  templateUrl: './my-nav.component.html',
  styleUrls: ['./my-nav.component.css']
})
export class MyNavComponent implements OnDestroy {
  isLoginPage: boolean;
  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher, private _router: Router) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
   
  }



  mobileQuery: MediaQueryList;

  fillerNav = [
    {name: "Patient",  icon: "account_box",subMenu:[{name:'Create', route: "second-page"},{name:'Fetch', route: "third-page"}]},
    {name: "Blood Pressure", icon: "devices",subMenu:[{name:'Check-in', route: "check-in"},{name:'Measurement', route: "mesurement"}]},
    {name: "Schedule",  icon: "schedule",subMenu:[{name:'Create', route: "second-page"},{name:'Fetch', route: "third-page"}]},
    {name: "Payment",  icon: "payment",subMenu:[{name:'Create', route: "second-page"},{name:'Fetch', route: "third-page"}]}

  ];

  private _mobileQueryListener: () => void;

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }


  isExpanded = true;
  showSubmenu: boolean = true;
  isShowing = true;
  showSubSubMenu: boolean = true;

  mouseenter() {
    if (!this.isExpanded) {
      this.isShowing = true;
    }
  }

  mouseleave() {
    if (!this.isExpanded) {
      this.isShowing = false;
    }
  }

}

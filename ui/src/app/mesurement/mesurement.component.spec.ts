import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MesurementComponent } from './mesurement.component';

describe('MesurementComponent', () => {
  let component: MesurementComponent;
  let fixture: ComponentFixture<MesurementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MesurementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MesurementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

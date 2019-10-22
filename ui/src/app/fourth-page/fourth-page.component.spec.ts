import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FourthPageComponent } from './fourth-page.component';

describe('FourthPageComponent', () => {
  let component: FourthPageComponent;
  let fixture: ComponentFixture<FourthPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FourthPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FourthPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

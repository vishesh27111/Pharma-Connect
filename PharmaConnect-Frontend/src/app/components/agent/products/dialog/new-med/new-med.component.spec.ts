import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewMedComponent } from './new-med.component';

describe('NewMedComponent', () => {
  let component: NewMedComponent;
  let fixture: ComponentFixture<NewMedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewMedComponent]
    });
    fixture = TestBed.createComponent(NewMedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DonatebloodDialogComponent } from './donateblood-dialog.component';

describe('DonatebloodDialogComponent', () => {
  let component: DonatebloodDialogComponent;
  let fixture: ComponentFixture<DonatebloodDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DonatebloodDialogComponent]
    });
    fixture = TestBed.createComponent(DonatebloodDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

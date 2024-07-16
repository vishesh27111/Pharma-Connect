import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedDialogComponent } from './med-dialog.component';

describe('MedDialogComponent', () => {
  let component: MedDialogComponent;
  let fixture: ComponentFixture<MedDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MedDialogComponent]
    });
    fixture = TestBed.createComponent(MedDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

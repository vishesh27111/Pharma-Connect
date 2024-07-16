import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoreinfoDialogComponent } from './storeinfo-dialog.component';

describe('StoreinfoDialogComponent', () => {
  let component: StoreinfoDialogComponent;
  let fixture: ComponentFixture<StoreinfoDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StoreinfoDialogComponent]
    });
    fixture = TestBed.createComponent(StoreinfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

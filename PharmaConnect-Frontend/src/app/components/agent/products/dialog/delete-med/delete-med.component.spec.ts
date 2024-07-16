import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteMedComponent } from './delete-med.component';

describe('DeleteMedComponent', () => {
  let component: DeleteMedComponent;
  let fixture: ComponentFixture<DeleteMedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteMedComponent]
    });
    fixture = TestBed.createComponent(DeleteMedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

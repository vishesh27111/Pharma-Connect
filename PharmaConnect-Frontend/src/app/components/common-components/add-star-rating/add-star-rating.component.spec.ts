import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStarRatingComponent } from './add-star-rating.component';

describe('AddStarRatingComponent', () => {
  let component: AddStarRatingComponent;
  let fixture: ComponentFixture<AddStarRatingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddStarRatingComponent]
    });
    fixture = TestBed.createComponent(AddStarRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

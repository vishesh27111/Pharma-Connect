import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayStarRatingComponent } from './display-star-rating.component';

describe('DisplayStarRatingComponent', () => {
  let component: DisplayStarRatingComponent;
  let fixture: ComponentFixture<DisplayStarRatingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DisplayStarRatingComponent]
    });
    fixture = TestBed.createComponent(DisplayStarRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

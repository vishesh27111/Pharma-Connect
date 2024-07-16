import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BestSellingProductsComponent } from './best-selling-products.component';

describe('BestSellingProductsComponent', () => {
  let component: BestSellingProductsComponent;
  let fixture: ComponentFixture<BestSellingProductsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BestSellingProductsComponent]
    });
    fixture = TestBed.createComponent(BestSellingProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

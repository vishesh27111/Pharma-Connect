import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnualSaleChartComponent } from './annual-sale-chart.component';

describe('AnnualSaleChartComponent', () => {
  let component: AnnualSaleChartComponent;
  let fixture: ComponentFixture<AnnualSaleChartComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AnnualSaleChartComponent]
    });
    fixture = TestBed.createComponent(AnnualSaleChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

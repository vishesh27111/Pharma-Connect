import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginatedTableProductsComponent } from './paginated-table.component';

describe('PaginatedTableComponent', () => {
  let component: PaginatedTableProductsComponent;
  let fixture: ComponentFixture<PaginatedTableProductsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaginatedTableProductsComponent]
    });
    fixture = TestBed.createComponent(PaginatedTableProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

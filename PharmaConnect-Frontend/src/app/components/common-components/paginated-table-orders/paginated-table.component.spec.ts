import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginatedTableOrdersComponent } from './paginated-table.component';

describe('PaginatedTableComponent', () => {
  let component: PaginatedTableOrdersComponent;
  let fixture: ComponentFixture<PaginatedTableOrdersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaginatedTableOrdersComponent]
    });
    fixture = TestBed.createComponent(PaginatedTableOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

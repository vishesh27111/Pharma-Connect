import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginatedTableReservationsComponent } from './paginated-table.component';

describe('PaginatedTableComponent', () => {
  let component: PaginatedTableReservationsComponent;
  let fixture: ComponentFixture<PaginatedTableReservationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaginatedTableReservationsComponent]
    });
    fixture = TestBed.createComponent(PaginatedTableReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

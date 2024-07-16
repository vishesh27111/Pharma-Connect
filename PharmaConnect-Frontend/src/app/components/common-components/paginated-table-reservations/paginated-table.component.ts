import {AfterViewInit, Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Output, EventEmitter } from '@angular/core';

/**
 * @title Table with pagination
 */
@Component({
  selector: 'paginated-table-reservations',
  styleUrls: ['paginated-table.component.sass'],
  templateUrl: 'paginated-table.component.html'
})
export class PaginatedTableReservationsComponent implements AfterViewInit, OnChanges {
  @Input() displayedColumns: string[] = ['reservationId', 'userName', 'drugName', 'quantityNeeded', 'unitPrice', 'lockAcquiredTime', 'releaseTime', 'update'];
  @Input() columnNames: string[] = ['Reservation ID', 'Customer Name', 'Medicine Name', 'Quantity', 'Price', 'Booked On', 'Reserved Until'];
  @Input() data: any[] = [];
  @Output() reserveEvent = new EventEmitter<any>();
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnChanges(changes: SimpleChanges): void {
      this.dataSource = new MatTableDataSource<any>(this.data);
  }

  completePurchase(reservationId:any){
    this.reserveEvent.emit(reservationId);
  }
}

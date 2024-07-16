import {AfterViewInit, Component, Input, SimpleChanges, ViewChild} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

/**
 * @title Table with pagination
 */
@Component({
  selector: 'paginated-table-orders',
  styleUrls: ['paginated-table.component.sass'],
  templateUrl: 'paginated-table.component.html'
})
export class PaginatedTableOrdersComponent implements AfterViewInit {
  @Input() displayedColumns: string[] = ['reservationId', 'drugName', 'lockAcquiredTime', 'unitPrice', 'quantityNeeded', 'totalBill', 'status'];
  @Input() columnNames: string[] = ['Order ID', 'Drug Name', 'Date of Purchase', 'Unit Price', 'Quantity', 'Total Bill', 'Status'];
  @Input() data: any[] = [];
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnChanges(changes: SimpleChanges): void {
      this.dataSource = new MatTableDataSource<any>(this.data);
  }
}

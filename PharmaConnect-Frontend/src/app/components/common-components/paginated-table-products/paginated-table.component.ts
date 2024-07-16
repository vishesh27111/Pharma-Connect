import {AfterViewInit, Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Output, EventEmitter } from '@angular/core';

/**
 * @title Table with pagination
 */
@Component({
  selector: 'paginated-table-products',
  styleUrls: ['paginated-table.component.sass'],
  templateUrl: 'paginated-table.component.html'
})
export class PaginatedTableProductsComponent implements AfterViewInit, OnChanges {
  @Input() displayedColumns: string[] = ['code', 'drug_name', 'company_name', 'quantity', 'expiration_date', 'unit_price', 'update', 'delete'];
  @Input() columnNames: string[] = ['Code', 'Medicine Name', 'Company Name', 'Quantity', 'Expiration Date', 'Price'];
  @Input() data: any[] = [];
  @Output() updateEvent = new EventEmitter<any>();
  @Output() deleteEvent = new EventEmitter<any>();
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnChanges(changes: SimpleChanges): void {
      this.dataSource = new MatTableDataSource<any>(this.data);
  }

  UpdateDialog(code:any){
    this.updateEvent.emit(code);
  }

  DeleteDialog(code:number){
    this.deleteEvent.emit(code);
  }
}

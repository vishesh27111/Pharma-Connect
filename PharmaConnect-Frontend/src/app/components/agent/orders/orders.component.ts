import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StoreService } from 'src/app/services/store.service';
import { MessageDialogComponent } from '../../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.sass']
})
export class OrdersComponent implements OnInit {
  ordersData: any[];
  constructor(
    private storeService: StoreService, private _snackBar: MatSnackBar, public msgDialog: MatDialog
  ) {}
  ngOnInit(): void {
    const storeId = localStorage.getItem("storeId");
    this.storeService.getStorePurchases(storeId).subscribe((data: any) => {
       this.ordersData = data[0]?.storeReservationDetails?.filter((x: any) => x.status.toLowerCase() !== "waiting");
    }, (error) => {
      // this._snackBar.open(error);

      const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error encountered";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
    });
  }
  displayedColumns: string[] = ['code', 'medicineName', 'companyName', 'quantity', 'expirationDate', 'price'];
  columnNames: string[] = ['Code', 'Medicine Name', 'Company Name', 'Quantity', 'Expiration Date', 'Price'];
}

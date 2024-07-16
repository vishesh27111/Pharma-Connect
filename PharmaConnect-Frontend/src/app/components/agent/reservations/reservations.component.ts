import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StoreService } from 'src/app/services/store.service';
import { MessageDialogComponent } from '../../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.sass']
})
export class ReservationsComponent implements OnInit {
  reservationData: any[] = [];
  constructor(
    private storeService: StoreService, private _snackBar: MatSnackBar, public msgDialog: MatDialog
  ) {}
  ngOnInit() {
    this.getStorePurchase();
  }
  getStorePurchase() {
    const storeId = localStorage.getItem("storeId");
    this.storeService.getStorePurchases(storeId).subscribe(response => {
      this.reservationData = response[0]?.storeReservationDetails?.filter((x: any) => x.status.toLowerCase() === "waiting");
    }, error => {
      // this._snackBar.open("Error occured. Try Again");
      const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error loading reservations";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
    });
  }
  completePurchase(reservationId: any) {
    this.storeService.completePurchase(reservationId).subscribe(response => {
      this._snackBar.open(response.message);
      this.getStorePurchase();
    }, error => {
      const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error completing reservations";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
    });
  }
}

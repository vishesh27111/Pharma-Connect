import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';
import { MessageDialogComponent } from '../../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-previous-orders',
  templateUrl: './previous-orders.component.html',
  styleUrls: ['./previous-orders.component.sass']
})
export class PreviousOrdersComponent implements OnInit {
  isLoading = false;
  orders: any[] = [];
  constructor(
    private userService: UserService,private _snackBar: MatSnackBar, public msgDialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    const emailId = localStorage.getItem("emailId");
    this.userService.getUserBookings(emailId).subscribe(response => {
      this.isLoading = false;
      this.orders = response[0]?.userReservationDetails?.filter((x: any) => x?.status?.toLowerCase() !== 'waiting');
    }, error => {
      this.isLoading = false;
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = "Error loading orders";
      const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
    });
  }

  onOrderItemClick(itemId: number) {
    console.log(itemId);
  }
}

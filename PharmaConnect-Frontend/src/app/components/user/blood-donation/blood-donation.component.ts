import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, inject } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { map } from 'rxjs';
import { DonatebloodDialogComponent } from '../dialog/donateblood-dialog/donateblood-dialog.component';

@Component({
  selector: 'app-blood-donation',
  templateUrl: './blood-donation.component.html',
  styleUrls: ['./blood-donation.component.sass']
})
export class BloodDonationComponent {

  private breakpointObserver = inject(BreakpointObserver);

  DonationServices = [
    {
      "id": 1,
      "name": "Canadian Blood Services",
      "type": "Blood donation Center",
      "address": "7071 Bayers Rd Unit 252, Halifax, NS B3L 2C2",
      "website": "https://www.blood.ca/",
      "phone": "+1 8882366283",
    },
    {
      "id": 2,
      "name": "Blood Collection Service",
      "type": "Non-profit organization",
      "address": "7071 Bayers Rd, Halifax, NS B3L 2C2",
      "website": "https://www.nshealth.ca/blood-collection",
      "phone": "+1 9024732074",
    },
    {
      "id": 3,
      "name": "NSHA Services @ Bayers Road Centre",
      "type": "Medical Center",
      "address": "7071 Bayers Rd, Halifax, NS B3L 2C2",
      "website": "http://www.nshealth.ca/locations-details/Bayers%20Road%20Centre",
      "phone": "+1 9024541661",
    }
  ]

  constructor(private donateDialog: MatDialog) {}

  /** Based on the screen size, switch from standard to one column per row */
  cardLayout = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({ matches }) => {
      if (matches) {
        return {
          columns: 1,
          miniCard: { cols: 1, rows: 1 },
          chart: { cols: 1, rows: 2 },
          table: { cols: 1, rows: 4 },
        };
      }
 
      return {
          columns: 4,
          miniCard: { cols: 1, rows: 1 },
          chart: { cols: 2, rows: 2 },
          table: { cols: 4, rows: 4 },
        };
    })
  );

  donateBlood(center: any){
      const dialogConfig = new MatDialogConfig();
      const dialogRef = this.donateDialog.open(DonatebloodDialogComponent, dialogConfig);
  }
}

import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';
import { MessageDialogComponent } from '../../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-find-stores',
  templateUrl: './find-stores.component.html',
  styleUrls: ['./find-stores.component.sass']
})
export class FindStoresComponent implements OnInit {
  currentLocation: any;
  medicalStores: any[] = [];
  isLoading = false;
  filterBy = "POPULARITY";

  constructor(
    private userService: UserService, private _snackBar: MatSnackBar,public msgDialog: MatDialog

  ) { }

  ngOnInit() {
    this.isLoading = true;
    this.getCurrentLocationDetails();
  }

  getCurrentLocationDetails() {
    this.userService.getGeolocationDetails().subscribe(response => {
      this.currentLocation = response;
      this.getMedicalStores();
      this.isLoading = false;
    }, error => {
      // alert("An error occured.");

      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = "Error getting current location";
      const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
    });
  }

  getMedicalStores() {
    const requestBody = {
      "includedTypes": [
          "pharmacy"
      ],
      "maxResultCount": 20,
      "rankPreference": this.filterBy,
      "locationRestriction": {
          "circle": {
              "center": {
                  "latitude": this.currentLocation.latitude,
                  "longitude": this.currentLocation.longitude
              },
              "radius": 20000.0
          }
      }
    };
    this.userService.getMedicalStoresInCurrentLocation(requestBody).subscribe((response) => {
      this.medicalStores = response.places;
    }, (error) => {
      // this._snackBar.open("Error while fetching store data.");

      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = "Error getting store locations";
      const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
 
      // dialogRef.afterClosed().subscribe(result => {
      // });
    })
  }

  onFilterChange() {
    this.getMedicalStores();
  }
}


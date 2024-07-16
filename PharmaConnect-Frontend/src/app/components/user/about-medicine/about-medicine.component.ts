import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import { MedDialogComponent } from '../dialog/med-dialog/med-dialog.component';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { StoreinfoDialogComponent } from '../dialog/storeinfo-dialog/storeinfo-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageDialogComponent } from '../../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-about-medicine',
  templateUrl: './about-medicine.component.html',
  styleUrls: ['./about-medicine.component.sass']
})

export class AboutMedicineComponent implements OnInit {
  isLoading = false;
  searchTerm: string = '';
  drugs: any[] = [];
  filteredData: any[] = [];
  nearbyStores: any[] = [];
  farawayStores: any[] = [];
  filteredStores: any[] = [];
  buyNowClicked = false;
  buy_med: any;
  geolocationDetails: any;
  quantity: number = 0;
  lockAcquiredTime: number = 180;

  constructor(
    private InfoDialog: MatDialog,
    private router: Router,
    private userService: UserService,
    private _snackBar: MatSnackBar,
    public msgDialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.userService.getGeolocationDetails().subscribe(response => {
      this.geolocationDetails = response;
      console.log(this.geolocationDetails);
    }, error => {
      // this._snackBar.open("Error occured");

      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = "Error occured";
      const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
 
      // dialogRef.afterClosed().subscribe(result => {
      // });
    });
    this.userService.getAllDrugs().subscribe(data => {
      this.isLoading = false;
      this.drugs = data;
      this.filteredData = this.drugs;
    }, error => {
      this.isLoading = false;
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = "Error loading medicines";
      const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
    });
  }

  onSearch() {
    if (this.searchTerm) {
      this.filteredData = this.drugs.filter(item =>
        item.drug_name.toLowerCase().includes(this.searchTerm.toLowerCase()) || 
        item.company_name.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    } else {
      this.filteredData = this.drugs;
    }
  }

  LearnMore(data:any) {
    const dialogConfig = new MatDialogConfig();

    const drug = this.filteredData.filter(x => x.code === data.code)[0];
    dialogConfig.data = drug;

    const dialogRef = this.InfoDialog.open(MedDialogComponent, dialogConfig);
  }

  KnowMore(store:any) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.data = store;

    const dialogRef = this.InfoDialog.open(StoreinfoDialogComponent, dialogConfig);
  }
  

  findStoresForDrugs(drug:any) {
      this.buyNowClicked = true;
      this.buy_med = drug;
      this.filteredStores = [];

      this.userService.getStoresForMedicines(drug.drugId).subscribe(data => {
        this.nearbyStores = data.filter((store: any) => store.zipcode.trim().slice(0, 3) === this.geolocationDetails.postal);
        this.farawayStores = data.filter((store: any) => store.zipcode.trim().slice(0, 3) !== this.geolocationDetails.postal);
        for (let store of [...this.nearbyStores, ...this.farawayStores]) {
          this.filteredStores.push({...store, bookQuantity: 1, lockAcquiredTime: 10})
        }
        console.log(this.filteredStores);
      }, error => {
        // this._snackBar.open("Error occured");

        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error finding stores";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
  
        // dialogRef.afterClosed().subscribe(result => {
        // });
      });
  }

  reserveDrug(store: any) {
    this.isLoading = true;
    if (store.bookQuantity < 1 || store.bookQuantity > 10 || store.lockAcquiredTime < 10 || store.lockAcquiredTime > 180) {
      this.isLoading = false;
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = "You can book quantity between 1 to 10 at a time.\nYou can reserve medicines between 10 mins. to 3 hrs.";
      const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
    } else {
      const reserveRequest = {
        user_email: localStorage.getItem("emailId"),
        store_id: store.storeId,
        drug_id: store.drugId,
        quantity: store.bookQuantity,
        lockAcquiredTime: store.lockAcquiredTime
      };
      this.userService.reserveMedicine(reserveRequest).subscribe(response => {
        this._snackBar.open("Drug has been successfully reserved", "Close");
        this.findStoresForDrugs(this.buy_med);
        this.isLoading = false;
      }, error => {
        // this._snackBar.open("An error occured");
        this.isLoading = false;
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error reserving drugs. Either the required quantity is not available or the reservation is more than 180 minutes";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
      });
    }
  }

  med_list(){
    this.buyNowClicked = false;
  }
}
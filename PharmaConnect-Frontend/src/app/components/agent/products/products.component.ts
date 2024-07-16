import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import { NewMedComponent } from './dialog/new-med/new-med.component';
import { DeleteMedComponent } from './dialog/delete-med/delete-med.component';
import { UpdateMedComponent } from './dialog/update-med/update-med.component';
import { StoreService } from 'src/app/services/store.service';
import { MatCardSmImage } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageDialogComponent } from '../../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.sass']
})
export class ProductsComponent implements OnInit {
  storeDrugs: any[];
  drugsData: any[];

  constructor(
    private addDialog: MatDialog,
    private updateDialog: MatDialog,
    private deleteDialog: MatDialog,
    private storeService: StoreService,
    private _snackBar: MatSnackBar,
    public msgDialog: MatDialog
  ) {}
  ngOnInit(): void {
    const storeId = localStorage.getItem("storeId");
    this.storeService.getDrugsForStore(storeId).subscribe((data: any) => {
      this.storeDrugs = data;
    }, (error) => {
      this._snackBar.open(error);
    });
    this.storeService.getAllDrugs().subscribe((data: any) => {
      this.drugsData = data;
    }, (error) => {
      this._snackBar.open(error);
    });
  }

    AddDialog() {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.data = this.drugsData;

        this.addDialog.open(NewMedComponent, {data: this.drugsData});

        const dialogRef = this.addDialog.open(NewMedComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(
          res => {
            if (res.isSave) {
              if (this.storeDrugs.filter(x => x.code === res.data.code).length > 0) {
                this.UpdateDrug(res, 'add');
              } else {
                console.log(res.data.drug_id);
                this.storeDrugs.push({
                  drugId: res.data.drug_id,
                  drug_name: this.drugsData.filter(x => x.code === res.data.code)[0].drug_name,
                  code: res.data.code,
                  quantity: res.data.quantity,
                  unit_price: res.data.unit_price,
                  company_name: this.drugsData.filter(x => x.code === res.data.code)[0].company_name,
                  production_date: this.drugsData.filter(x => x.code === res.data.code)[0].production_date,
                  expiration_date: this.drugsData.filter(x => x.code === res.data.code)[0].expiration_date,
                  prescription: true
                });
                this.storeDrugs = [...this.storeDrugs];
                console.log(this.storeDrugs);
                const storeId: number = +localStorage.getItem("storeId")!;
                const insertData = {
                  drug_id: res.data.drug_id,
                  store_id: storeId,
                  unit_price: res.data.unit_price,      
                  quantity: res.data.quantity,
                  code: res.data.code
                };
                this.storeService.addDrug(insertData).subscribe((data) => {
                  console.log('Drug Inserted successfully');
                  this._snackBar.open('Drug Inserted successfully',"",{duration:3000});
                }, (error) => {
                  // this._snackBar.open("Error adding.");

                  const dialogConfig = new MatDialogConfig();
                    dialogConfig.data = "Error adding drug.";
                    const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
              
                    // dialogRef.afterClosed().subscribe(result => {
                    // });
                });
              }
            }
            this.addDialog.closeAll();
          });
    }

    UpdateDialog(code:any){
      const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.data = this.storeDrugs.filter(x => x.code === code)[0];

        let arg = this.storeDrugs.filter(x => x.code === code)[0];

        this.updateDialog.open(UpdateMedComponent, {data: arg});

        const dialogRef = this.updateDialog.open(UpdateMedComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(
          res => {
            console.log(res);
            if (res.isSave) {
              this.UpdateDrug(res, 'update');
            }
            this.updateDialog.closeAll();
        });
    }

    UpdateDrug(res: any, type: string) {
      let updateItem: any;
      this.storeDrugs = this.storeDrugs.map(x=> {
        if(x.code === res.data.code) {
          updateItem = {
            drugId: x.drugId,
            company_name: x.company_name,
            expiration_date: x.expiration_date,
            unit_price: res.data.unit_price,
            quantity: type === 'update' ? res.data.quantity : res.data.quantity + x.quantity,
            drug_name: res.data.medicineName,
            code: res.data.code
          };
          return updateItem;
        }
        return x;
      });
      const storeId: number = +localStorage.getItem("storeId")!;
      const updateData = {
        drug_id: updateItem.drugId,
        store_id: storeId,
        unit_price: updateItem.unit_price,      
        quantity: updateItem.quantity,
        code: updateItem.code
      };
      this.storeService.updateDrug(updateData).subscribe((data) => {
        console.log('Drug Updated successfully');
        this._snackBar.open("Drug Updated successfully","",{duration:2000});
      }, (error) => {
        // this._snackBar.open("Error updating drug.");
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error updating drug.";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
  
        // dialogRef.afterClosed().subscribe(result => {
        // });
      });
    }

    DeleteDialog(id:number){
      const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;

        this.deleteDialog.open(DeleteMedComponent, dialogConfig);

        const dialogRef = this.deleteDialog.open(DeleteMedComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(
          res => {
            if(res === true){
                const drug = this.storeDrugs.filter(x => x.code === id);
                if (drug.length > 0) {
                  const storeId: number = +localStorage.getItem("storeId")!;
                    this.storeService.deleteDrug(drug[0].drugId, storeId).subscribe((data) => {
                    console.log('Drug Deleted successfully');
                    this._snackBar.open('Drug Deleted successfully',"",{duration:3000});
                  }, (error) => {
                    // this._snackBar.open("Error deleting drug.");

                    const dialogConfig = new MatDialogConfig();
                    dialogConfig.data = "Error deleting drug.";
                    const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
              
                    // dialogRef.afterClosed().subscribe(result => {
                    // });
                  });
                }
                this.storeDrugs = this.storeDrugs.filter(x => x.code != id);
            }
            this.deleteDialog.closeAll();
          });
    }

}

import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UpdateMedComponent } from 'src/app/components/agent/products/dialog/update-med/update-med.component';

@Component({
  selector: 'app-med-dialog',
  templateUrl: './med-dialog.component.html',
  styleUrls: ['./med-dialog.component.sass']
})
export class MedDialogComponent {
drug: any;

  constructor(private formBuilder: FormBuilder, private dialogRef: MatDialogRef<MedDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}


  close() {
    this.dialogRef.close({isSave: false, data: this.data});
  }

}

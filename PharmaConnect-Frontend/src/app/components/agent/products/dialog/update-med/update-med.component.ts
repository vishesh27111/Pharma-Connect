import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-update-med',
  templateUrl: './update-med.component.html',
  styleUrls: ['./update-med.component.sass']
})
export class UpdateMedComponent {

  public UpdateMedForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private dialogRef: MatDialogRef<UpdateMedComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
    this.UpdateMedForm = this.formBuilder.group({
      drug_id: this.formBuilder.control(this.data.drugId),
      code: this.formBuilder.control(this.data.code),
      medicineName: this.formBuilder.control(this.data.drug_name),
      quantity: this.formBuilder.control(this.data.quantity),
      unit_price: this.formBuilder.control(this.data.unit_price)
    });
  }

  save() {
    this.dialogRef.close({isSave: true, data: this.UpdateMedForm.value});
  }

  close() {
    this.dialogRef.close({isSave: false, data: this.data});
  }

}

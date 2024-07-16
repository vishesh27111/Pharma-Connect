import { AfterViewInit, Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-new-med',
  templateUrl: './new-med.component.html',
  styleUrls: ['./new-med.component.sass']
})
export class NewMedComponent {
  public NewMedForm: FormGroup;
  public drugsData: any[];

  constructor(private formBuilder: FormBuilder, private dialogRef: MatDialogRef<NewMedComponent>, @Inject(MAT_DIALOG_DATA) public data: any[]) {
    this.drugsData = data;
    this.NewMedForm = this.formBuilder.group({
      drug_id: new FormControl(''),
      code: new FormControl(''),
      medicineName: new FormControl(''),
      quantity: new FormControl(0),
      unit_price: new FormControl(0)
    });
  }

  onDrugNameSelected(drug: any) {
    console.log(drug);
    this.NewMedForm.controls['drug_id'].setValue(drug.drugId);
    this.NewMedForm.controls['code'].setValue(drug.code);
    this.NewMedForm.controls['medicineName'].setValue(drug.drug_name);
  }

  save() {
    this.dialogRef.close({isSave: true, data: this.NewMedForm.value});
  }

  close() {
      this.dialogRef.close();
  }
}

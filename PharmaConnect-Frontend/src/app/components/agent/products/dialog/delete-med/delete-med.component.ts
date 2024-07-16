import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-med',
  templateUrl: './delete-med.component.html',
  styleUrls: ['./delete-med.component.sass']
})
export class DeleteMedComponent {

  isDelete = false;
  constructor(private dialogRef: MatDialogRef<DeleteMedComponent>) {}

  no(){
    this.dialogRef.close(this.isDelete);
  }

  yes(){
    this.isDelete= true;
    this.dialogRef.close(this.isDelete);
  }
}

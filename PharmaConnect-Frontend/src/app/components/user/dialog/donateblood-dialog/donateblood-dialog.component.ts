import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-donateblood-dialog',
  templateUrl: './donateblood-dialog.component.html',
  styleUrls: ['./donateblood-dialog.component.sass']
})
export class DonatebloodDialogComponent {

  constructor(
    private userService: UserService,
    private _snackBar: MatSnackBar
  ) {}
  checked = false;
  bloodgroups  = ['O-','O+','A-','A+','B-','B+','AB-','AB+'];

  DonateBloodForm = new FormGroup({
    firstName: new FormControl('',Validators.required),
    lastName: new FormControl('',Validators.required),
    address: new FormControl('',Validators.required),
    age: new FormControl(18),
    email: new FormControl('',[Validators.required,Validators.email]),
    phone: new FormControl('',Validators.required),
    bloodgroup: new FormControl('',Validators.required),
    hasDonated: new FormControl(false,Validators.required)
  });

  onBloodGroupSelectChange(e: any) {
    this.DonateBloodForm.controls['bloodgroup'].setValue(e.target.value);
  }

  onDonationFormSubmit() {
    const request = {
      "recipient": this.DonateBloodForm.get('email')?.value,
      "msgBody": "Here are your registartion details",
      "subject":"Blood Donation Registration Confirmation",
      "firstName": this.DonateBloodForm.get('firstName')?.value,
      "lastName": this.DonateBloodForm.get('lastName')?.value,
      "address": this.DonateBloodForm.get('address')?.value,
      "phone": this.DonateBloodForm.get('phone')?.value,
      "age": this.DonateBloodForm.get('age')?.value,
      "bloodGroup": this.DonateBloodForm.get('bloodgroup')?.value,
      "email": this.DonateBloodForm.get('email')?.value,
      "donatedPreviously": this.DonateBloodForm.get('hasDonated')?.value
    };

    this.userService.sendBloodDonationEmail(request).subscribe(response => {
        this._snackBar.open("Successfully registered for blood donation","",{duration:3000});
    }, error => {
      this._snackBar.open("Successfully registered for blood donation","",{duration:3000});
    });
  }
}

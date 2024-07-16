import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { MessageDialogComponent } from '../../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class UserProfileComponent implements OnInit {
  public customerProfileForm !: FormGroup;
  public customerUpdatePassword!: FormGroup;
  public submitted: boolean = false;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private _snackBar: MatSnackBar,
    public msgDialog: MatDialog
  ) {}
  ngOnInit(): void {
    const emailId = localStorage.getItem("emailId");
    const token = localStorage.getItem("token");
    this.customerProfileForm = new FormGroup({
      fullname: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      mobile: new FormControl('', [Validators.required, Validators.pattern("^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$"), Validators.minLength(8), Validators.maxLength(16)]),
      dob: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      zip: new FormControl('', [Validators.required])
    });
    this.customerUpdatePassword = new FormGroup({
      currentPassword: new FormControl('', [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*\W).+$"), Validators.minLength(8), Validators.maxLength(16)]),
      newPassword: new FormControl('', [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*\W).+$"), Validators.minLength(8), Validators.maxLength(16)])
    });
    this.userService.getUserProfile({"emailID": emailId}, token).subscribe((response) => {
      const userData = response.Success;
      this.customerProfileForm = new FormGroup({
        fullname: new FormControl(userData.name, [Validators.required]),
        email: new FormControl(userData.email, [Validators.required, Validators.email]),
        mobile: new FormControl(userData.phone, [Validators.required, Validators.pattern("^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$")]),
        dob: new FormControl(userData.date_Of_Birth, [Validators.required]),
        address: new FormControl(userData.address, [Validators.required]),
        zip: new FormControl(userData.zipcode, [Validators.required]),
      });
    });
  }
  
  logout() {
    localStorage.removeItem('storeId');
    localStorage.removeItem('emailId');
    localStorage.removeItem('userId');
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

  save() {
    this.submitted = true;
    const userInfo =  {
      name: this.customerProfileForm.get('fullname')?.value,
      email: this.customerProfileForm.get('email')?.value,
      address: this.customerProfileForm.get('address')?.value,
      zipcode: this.customerProfileForm.get('zip')?.value,
      updatedAt: new Date(Date.now()).toISOString().split('.')[0],
      createdAt: new Date(Date.now()).toISOString().split('.')[0],
      date_Of_Birth: this.customerProfileForm.get('dob')?.value,
      phone: this.customerProfileForm.get('mobile')?.value,
    };
    this.userService.updateUserProfile(userInfo).subscribe(response => {
      this._snackBar.open("Profile Updated Successfully","",{duration:2000});

      // const dialogConfig = new MatDialogConfig();
      //   dialogConfig.data = response.message;
      //   const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
    }, error => {
      // this._snackBar.open(error.message);/
      const dialogConfig = new MatDialogConfig();
        dialogConfig.data = error.message;
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
    });
    const isUserPasswordUpdateAllowed = this.customerUpdatePassword.controls['currentPassword'].value && this.customerUpdatePassword.controls['currentPassword'].value !== '' &&
      this.customerUpdatePassword.controls['newPassword'].value && this.customerUpdatePassword.controls['newPassword'].value !== '';

    if (isUserPasswordUpdateAllowed) {
      const token = localStorage.getItem("token");
      const updatePasswordRequest = {
        emailID: userInfo.email,
        currentPassword: this.customerUpdatePassword.controls['currentPassword'].value,
        newPassword: this.customerUpdatePassword.controls['newPassword'].value
      };
      this.userService.updatePassword(updatePasswordRequest, token).subscribe(response => {
        this._snackBar.open("Updated Password Successfully");
        // const dialogConfig = new MatDialogConfig();
        // dialogConfig.data = response.message;
        // const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
      }, error => {
        // this._snackBar.open(error.message);
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Problem updating password. Try again";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
      });
    }
  }
}

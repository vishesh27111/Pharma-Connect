import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MessageDialogComponent } from '../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.sass']
})
export class SignupComponent implements OnInit {

  public customerSignupForm !: FormGroup;
  public storeSignupForm !: FormGroup;
  public submitted: boolean = false;
  public role: string = 'ROLE_USER';
  public currentYear = new Date().getFullYear();
  public currentMonth = new Date().getMonth();
  public currentDate = new Date().getDate();
  public maxDate: string = new Date(this.currentYear-18, this.currentMonth, this.currentDate).toISOString().split('T')[0];

  constructor(
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router,
    private _snackBar: MatSnackBar,
    public msgDialog: MatDialog
  ){ }

  ngOnInit() {
    this.customerSignupForm = new FormGroup({
      fullname: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,16}$/), Validators.minLength(8), Validators.maxLength(16)]),
      mobile: new FormControl('', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(10), Validators.maxLength(10)]),
      dob: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      zip: new FormControl('', [Validators.required])
    });

    this.storeSignupForm = new FormGroup({
      storeName: new FormControl('', [Validators.required]),
      managerName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,16}$/), Validators.minLength(8), Validators.maxLength(16)]),
      mobile: new FormControl('', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(10), Validators.maxLength(10)]),
      address: new FormControl('', [Validators.required]),
      zip: new FormControl('', [Validators.required]),
      registrationNumber: new FormControl('', [Validators.required])
    });
  }

  signUp(){
    this.submitted = true;
    const isValidData = this.role === "ROLE_USER" ? this.customerSignupForm.valid : this.storeSignupForm.valid;
    console.log(isValidData);
    if (isValidData) {
      const userInfo = this.role === "ROLE_USER" ? {
        name: this.customerSignupForm.get('fullname')?.value,
        email: this.customerSignupForm.get('email')?.value,
        password: this.customerSignupForm.get('password')?.value,
        address: this.customerSignupForm.get('address')?.value,
        zipcode: this.customerSignupForm.get('zip')?.value,
        updatedAt: new Date(Date.now()).toISOString(),
        createdAt: new Date(Date.now()).toISOString(),
        date_Of_Birth: this.customerSignupForm.get('dob')?.value,
        phone: this.customerSignupForm.get('mobile')?.value,
        role: this.role
      } : {
        store_name: this.storeSignupForm.get('storeName')?.value,
        manager_name: this.storeSignupForm.get('managerName')?.value,
        email: this.storeSignupForm.get('email')?.value,
        password: this.storeSignupForm.get('password')?.value,
        phone: this.storeSignupForm.get('mobile')?.value,
        address: this.storeSignupForm.get('address')?.value,
        zipcode: this.storeSignupForm.get('zip')?.value,
        registration_number: this.storeSignupForm.get('registrationNumber')?.value,
        updatedAt: new Date(Date.now()).toISOString(),
        createdAt: new Date(Date.now()).toISOString(),
        role: this.role
      };
      console.log(userInfo);
      this.authService.signup(userInfo)
        .subscribe((data: any) => {
          this._snackBar.open("Successfully signed up","", {duration: 2000});
          this.customerSignupForm.reset();
          this.storeSignupForm.reset();
          this.submitted = false;
          this.router.navigate(['login']);
        }, (error) => {
          // this._snackBar.open("Error. Try again");
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = "Error. Try again";
          const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
     
          // dialogRef.afterClosed().subscribe(result => {
          // });
        });
    }
  }

  changeRole(event: any) {
    console.log(event);
    this.customerSignupForm.reset();
    this.storeSignupForm.reset();
    this.role = event.value;
  }
}

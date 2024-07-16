import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { StoreService } from 'src/app/services/store.service';
import { MessageDialogComponent } from '../../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit {
  public storeUpdatePassword !: FormGroup;
  public storeProfileForm !: FormGroup;
  public submitted: boolean = false;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private storeService: StoreService,
    private _snackBar: MatSnackBar,
    public msgDialog: MatDialog
  ) {}
  ngOnInit(): void {
    const storeId = localStorage.getItem("storeId");
    const token = localStorage.getItem("token");
    this.storeProfileForm = new FormGroup({
      storeName: new FormControl('', [Validators.required]),
      managerName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      mobile: new FormControl('', [Validators.required, Validators.pattern("^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$")]),
      address: new FormControl('', [Validators.required]),
      zip: new FormControl('', [Validators.required]),
      registrationNumber: new FormControl('', [Validators.required]),
      monday: new FormControl({
        isOpen: false,
        from: '',
        to: ''
      }),
      tuesday: new FormControl({
        isOpen: false,
        from: '',
        to: ''
      }),
      wednesday: new FormControl({
        isOpen: false,
        from: '',
        to: ''
      }),
      thursday: new FormControl({
        isOpen: false,
        from: '',
        to: ''
      }),
      friday: new FormControl({
        isOpen: false,
        from: '',
        to: ''
      }),
      saturday: new FormControl({
        isOpen: false,
        from: '',
        to: ''
      }),
      sunday: new FormControl({
        isOpen: false,
        from: '',
        to: ''
      })
    });
    this.storeUpdatePassword = new FormGroup({
      currentPassword: new FormControl('', [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*\W).+$"), Validators.minLength(8), Validators.maxLength(16)]),
      newPassword: new FormControl('', [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*\W).+$"), Validators.minLength(8), Validators.maxLength(16)])
    });
    this.storeService.getProfile(storeId, {"role": "ROLE_ADMIN"}, token).subscribe((data) => {
      const workingHours = JSON.parse(data.working_hours);
      this.storeProfileForm = new FormGroup({
        storeName: new FormControl(data.store_name, [Validators.required]),
        managerName: new FormControl(data.manager_name, [Validators.required]),
        email: new FormControl(data.email, [Validators.required, Validators.email]),
        mobile: new FormControl(data.phone, [Validators.required, Validators.pattern("^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$")]),
        address: new FormControl(data.address, [Validators.required]),
        zip: new FormControl(data.zipcode, [Validators.required]),
        registrationNumber: new FormControl(data.registration_number, [Validators.required]),
        monday: new FormControl({
          isOpen: workingHours && workingHours.monday && workingHours.monday.toLowerCase() != "closed",
          from: workingHours && workingHours.monday && workingHours.monday.split(" - ")[0],
          to: workingHours && workingHours.monday && workingHours.monday.split(" - ")[1]
        }),
        tuesday: new FormControl({
          isOpen: workingHours && workingHours.tuesday && workingHours.tuesday.toLowerCase() != "closed",
          from: workingHours && workingHours.tuesday && workingHours.tuesday.split(" - ")[0],
          to: workingHours && workingHours.tuesday && workingHours.tuesday.split(" - ")[1]
        }),
        wednesday: new FormControl({
          isOpen: workingHours && workingHours.wednesday && workingHours.wednesday.toLowerCase() != "closed",
          from: workingHours && workingHours.wednesday && workingHours.wednesday.split(" - ")[0],
          to: workingHours && workingHours.wednesday && workingHours.wednesday.split(" - ")[1]
        }),
        thursday: new FormControl({
          isOpen: workingHours && workingHours.thursday && workingHours.thursday.toLowerCase() != "closed",
          from: workingHours && workingHours.thursday && workingHours.thursday.split(" - ")[0],
          to: workingHours && workingHours.thursday && workingHours.thursday.split(" - ")[1]
        }),
        friday: new FormControl({
          isOpen: workingHours && workingHours.friday && workingHours.friday.toLowerCase() != "closed",
          from: workingHours && workingHours.friday && workingHours.friday.split(" - ")[0],
          to: workingHours && workingHours.friday && workingHours.friday.split(" - ")[1]
        }),
        saturday: new FormControl({
          isOpen: workingHours && workingHours.saturday && workingHours.saturday.toLowerCase() != "closed",
          from: workingHours && workingHours.saturday && workingHours.saturday.split(" - ")[0],
          to: workingHours && workingHours.saturday && workingHours.saturday.split(" - ")[1]
        }),
        sunday: new FormControl({
          isOpen: workingHours && workingHours.sunday && workingHours.sunday.toLowerCase() != "closed",
          from: workingHours && workingHours.sunday && workingHours.sunday.split(" - ")[0],
          to: workingHours && workingHours.sunday && workingHours.sunday.split(" - ")[1]
        })
      });
      console.log(this.storeProfileForm.value);
    });
  }

  onWorkingHoursUpdate(day: string) {
    const isOpen = this.storeProfileForm.get(day)?.value.isOpen;
    this.storeProfileForm.controls[day].setValue({
      isOpen: isOpen,
      from: isOpen ? this.storeProfileForm.get(day)?.value.from : '',
      to: isOpen ? this.storeProfileForm.get(day)?.value.to : ''
    });
    console.log(this.storeProfileForm.value);
  }
  
  logout() {
    localStorage.removeItem('storeId');
    localStorage.removeItem('emailId');
    localStorage.removeItem('userId');
    localStorage.removeItem('userToken');
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

  save() {
    this.submitted = true;
    const storeId = localStorage.getItem('storeId');
    const token = localStorage.getItem('token');
    const storeInfo =  {
      storeId: storeId,
      email: this.storeProfileForm.get('email')?.value,
      address: this.storeProfileForm.get('address')?.value,
      manager_name: this.storeProfileForm.get('managerName')?.value,
      phone: this.storeProfileForm.get('mobile')?.value,
      registration_number: this.storeProfileForm.get('registrationNumber')?.value,
      store_name: this.storeProfileForm.get('storeName')?.value,
      updatedAt: new Date().toISOString().split('.')[0],
      zipcode: this.storeProfileForm.get('zip')?.value,
      monday: this.generateTimeString('monday'),
      tuesday: this.generateTimeString('tuesday'),
      wednesday: this.generateTimeString('wednesday'),
      thursday: this.generateTimeString('thursday'),
      friday: this.generateTimeString('friday'),
      saturday: this.generateTimeString('saturday'),
      sunday: this.generateTimeString('sunday')
    };
    this.storeService.updateProfile(storeInfo, token).subscribe(response => {
      console.log(response);
      this._snackBar.open("Profile Updated Successfully","",{duration:2000})
    }, error => {
      // this._snackBar.open("Error. Try Again");

      const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error. Try Again";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
    });
    const isUserPasswordUpdateAllowed = this.storeUpdatePassword.controls['currentPassword'].value && this.storeUpdatePassword.controls['currentPassword'].value !== '' &&
      this.storeUpdatePassword.controls['newPassword'].value && this.storeUpdatePassword.controls['newPassword'].value !== '';

    if (isUserPasswordUpdateAllowed) {
      const token = localStorage.getItem("token");
      const updatePasswordRequest = {
        storeId: storeId,
        currentPassword: this.storeUpdatePassword.controls['currentPassword'].value,
        newPassword: this.storeUpdatePassword.controls['newPassword'].value
      };
      this.storeService.updatePassword(updatePasswordRequest, token).subscribe(response => {
        this._snackBar.open("Profile Updated Successfully","",{duration:2000});
      }, error => {
        // this._snackBar.open("Error. Try Again");

        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error. Try Again";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
   
        // dialogRef.afterClosed().subscribe(result => {
        // });
      });
    }
  }

  generateTimeString(day: string) {
    return this.storeProfileForm.get(day)?.value.isOpen ? 
      this.storeProfileForm.get(day)?.value.from + " - " + this.storeProfileForm.get(day)?.value.to : 'Closed'
  }
}

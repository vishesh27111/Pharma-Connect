import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MessageDialogComponent } from '../common-components/message-dialog/message-dialog.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  public loginForm!: FormGroup;
  public submitted: boolean = false;
  public role: string = 'ROLE_USER';
  constructor(
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router,
    private _snackBar: MatSnackBar,
    public msgDialog: MatDialog
  ) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(15)
      ])
    });
  }

  login() {
    this.submitted = true;
    const credentials = {
      email: this.loginForm.get('email')?.value,
      password: this.loginForm.get('password')?.value,
      role: this.role
    };
    this.authService.login(credentials, this.role)
      .subscribe((data: any) => {
        this._snackBar.open("Successfully logged in", "",{
          duration: 3000
        });
        this.loginForm.reset();
        this.submitted = false;
        if (localStorage) {
          localStorage.setItem("storeId", data.storeId);
          localStorage.setItem("emailId", data.emailId);
          localStorage.setItem("userId", data.userId);
          localStorage.setItem("token", data.token);
        } else {
          console.log("No support. Use a fallback such as browser cookies or store on the server.");
        }
        if (this.role === "ROLE_USER") {
          this.router.navigate(['user', 'about-medicine']);
        } else {
          this.router.navigate(['store', 'dashboard']);
        }
      }, (error) => {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Invalid credentials";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
    
        // dialogRef.afterClosed().subscribe(result => {
        // });
    
        // this._snackBar.open("Invalid credentials", "Close");
      });
  }

  changeRole(event: any) {
    this.role = event.value;
    this.loginForm.reset();
  }
}

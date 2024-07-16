import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageDialogComponent } from 'src/app/components/common-components/message-dialog/message-dialog.component';
import { StoreService } from 'src/app/services/store.service';

@Component({
  selector: 'app-storeinfo-dialog',
  templateUrl: './storeinfo-dialog.component.html',
  styleUrls: ['./storeinfo-dialog.component.sass']
})
export class StoreinfoDialogComponent {

  rating: number = 0;
  averageRating: number = 0;
  roundedAverageRating: number = 0;
  profileDetails: any;
  comment: string='';
  reviewGiven: boolean = false;
  reviewList: any[] = [];
  user: any;
  date: any;
  working_hours: any
  constructor(private dialogRef: MatDialogRef<StoreinfoDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public store: any,
              private storeService: StoreService,
              private _snackBar: MatSnackBar,
              private msgDialog: MatDialog
            ) { }

  ngOnInit(): void {
    const token = localStorage.getItem("token");
    this.storeService.getProfile(this.store.storeId, {"role": "ROLE_ADMIN"}, token).subscribe(response => {
      console.log(response);
      this.profileDetails = response;
      this.working_hours= JSON.parse(this.profileDetails.working_hours);
    }, error => {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = "Error loading information";
      const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
    });

    this.getReviews();
  }

  getReviews() {
    this.storeService.getStoreReviews(this.store.storeId).subscribe(data => {
      this.reviewList = data;
      this.reviewGiven = this.userReviewExists(this.reviewList);
      this.averageRating = this.reviewList.reduce((accumulator, review) => accumulator + review.rating, 0)/this.reviewList.length;
      this.roundedAverageRating = Math.round(this.averageRating);
    }, error => {
      const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error loading information";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
    });
  }

  userReviewExists(list: any){
    let user = localStorage.getItem("userId");
    let temp = list.filter((x: { userId: string | null; }) => x.userId?.toString() === user);
    console.log(temp);
    if(temp===null || temp.length === 0){
      return false;
    }
    return true;
  }

  rate(rating: number){
    if (rating > 0) {
      this.rating = rating;
    }
  }

  submit(){
    const userId = localStorage.getItem('userId');
    if(this.rating !== null && this.rating > 0){
      let reviewPost = {
        "rating": this.rating,
        "message": this.comment,
        "reviewDate": new Date().toISOString().split('T')[0],
        "userId": userId,
        "storeId": this.profileDetails.storeId,
        "storeName": this.profileDetails.store_name
      };
      this.storeService.addStoreReviews(reviewPost).subscribe( (res)=>{
          console.log("Review submitted");
          this.reviewGiven = true;    
          this.getReviews();
      }, error => {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = "Error loading information";
        const dialogRef = this.msgDialog.open(MessageDialogComponent, dialogConfig);
      });
    }

    this.reviewGiven = true;
    
  }

  close() {
    this.dialogRef.close({isSave: false, data: this.store});
  }

}

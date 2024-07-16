import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, ViewEncapsulation, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, map, shareReplay } from 'rxjs';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.sass']
})
export class UserComponent {

  constructor(private route: ActivatedRoute, private router: Router) {}
  private breakpointObserver = inject(BreakpointObserver);

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

    onReservationsClick() {
      this.router.navigate(['user-dashboard'], {relativeTo:this.route});
    }

    onOrderClick() {
      this.router.navigate(['previous-orders'], {relativeTo:this.route});
    }

    onMedicineClick(){
      this.router.navigate(['about-medicine'], {relativeTo:this.route});
    }
    
    onDonateClick(){
      this.router.navigate(['blood-donation'], {relativeTo:this.route});
    }

    onFindStoresClick() {
      this.router.navigate(['findstores'], {relativeTo:this.route});
    }

    onProfileClick() {
      this.router.navigate(['profile'], {relativeTo:this.route});
    }
}

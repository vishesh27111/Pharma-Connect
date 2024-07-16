import { Component, OnInit, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-agent',
  templateUrl: './agent.component.html',
  styleUrls: ['./agent.component.css']
})
export class AgentComponent implements OnInit {
  storeId: string;
  constructor(private route: ActivatedRoute, private router: Router) {}
  ngOnInit(): void {
      this.storeId = window.location.search.substring(1).split("=")[1];
      console.log(this.storeId);
  }
  private breakpointObserver = inject(BreakpointObserver);

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

    onDashboardClick() {
      this.router.navigate(['dashboard'], {relativeTo:this.route});
    }

    onProductsClick() {
      this.router.navigate(['products'], {relativeTo:this.route});
    }

    onOrdersClick() {
      this.router.navigate(['orders'], {relativeTo:this.route});
    }

    onProfileClick() {
      this.router.navigate(['profile'], {relativeTo:this.route});
    }

    onReservationsClick() {
      this.router.navigate(['reservations'], {relativeTo:this.route});
    }
}

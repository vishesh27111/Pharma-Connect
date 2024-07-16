import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { AgentComponent } from './components/agent/agent.component';
import { DashboardComponent } from './components/agent/dashboard/dashboard.component';
import { ProductsComponent } from './components/agent/products/products.component';
import { UserComponent } from './components/user/user.component';
import { PreviousOrdersComponent } from './components/user/previous-orders/previous-orders.component';
import { UserDashboardComponent } from './components/user/user-dashboard/user-dashboard.component';
import { AboutMedicineComponent } from './components/user/about-medicine/about-medicine.component';
import { OrdersComponent } from './components/agent/orders/orders.component';
import { ProfileComponent } from './components/agent/profile/profile.component';
import { BloodDonationComponent } from './components/user/blood-donation/blood-donation.component';
import { FindStoresComponent } from './components/user/find-stores/find-stores.component';
import { UserProfileComponent } from './components/user/profile/profile.component';
import { storeAuthGuard, userAuthGuard } from './guard/auth-guard.guard';
import { ReservationsComponent } from './components/agent/reservations/reservations.component';

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch:'full'},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: `store`, component: AgentComponent, data:{requiresLogin: true}, canActivate:[storeAuthGuard], children: [
    {path: 'profile', component: ProfileComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'products', component: ProductsComponent},
    {path: 'orders', component: OrdersComponent},
    {path: 'reservations', component: ReservationsComponent}
  ]},
  {path: 'user', component: UserComponent, data:{requiresLogin: true}, canActivate:[userAuthGuard], children: [
    {path: 'user-dashboard', component: UserDashboardComponent},
    {path: 'previous-orders', component: PreviousOrdersComponent},
    {path: 'about-medicine', component: AboutMedicineComponent},
    {path: 'blood-donation', component: BloodDonationComponent},
    {path: 'findstores', component: FindStoresComponent},
    {path: 'profile', component: UserProfileComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

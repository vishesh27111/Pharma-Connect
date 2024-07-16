import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SignupComponent } from './components/signup/signup.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { NgChartsModule } from 'ng2-charts';
import { AgentComponent } from './components/agent/agent.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule, MatTableDataSource } from  '@angular/material/table';
import { DashboardComponent } from './components/agent/dashboard/dashboard.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatRadioModule } from '@angular/material/radio';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CardComponent } from './components/agent/dashboard/card/card.component';
import { AnnualSaleChartComponent } from './components/agent/dashboard/charts/annual-sale-chart/annual-sale-chart.component';
import { CustomerInformationComponent } from './components/agent/dashboard/charts/customer-information/customer-information.component';
import { BestSellingProductsComponent } from './components/agent/dashboard/charts/best-selling-products/best-selling-products.component';
import { ProductsComponent } from './components/agent/products/products.component';
import { NewMedComponent } from './components/agent/products/dialog/new-med/new-med.component';
import { DeleteMedComponent } from './components/agent/products/dialog/delete-med/delete-med.component';
import { UpdateMedComponent } from './components/agent/products/dialog/update-med/update-med.component';
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule } from '@angular/material/dialog';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { UserComponent } from './components/user/user.component';
import { PreviousOrdersComponent } from './components/user/previous-orders/previous-orders.component';
import { UserDashboardComponent } from './components/user/user-dashboard/user-dashboard.component';
import { AboutMedicineComponent } from './components/user/about-medicine/about-medicine.component';
import { PaginatedTableProductsComponent } from "./components/common-components/paginated-table-products/paginated-table.component";
import { OrdersComponent } from './components/agent/orders/orders.component';
import { PaginatedTableOrdersComponent } from './components/common-components/paginated-table-orders/paginated-table.component';
import { MatSortModule } from '@angular/material/sort';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { ProfileComponent } from './components/agent/profile/profile.component';
import { FindStoresComponent } from './components/user/find-stores/find-stores.component';
import { StoreListItemComponent } from './components/user/find-stores/store-list-item/store-list-item.component';
import { MedDialogComponent } from './components/user/dialog/med-dialog/med-dialog.component';
import { BloodDonationComponent } from './components/user/blood-donation/blood-donation.component';
import { DonatebloodDialogComponent } from './components/user/dialog/donateblood-dialog/donateblood-dialog.component';
import { UserProfileComponent } from './components/user/profile/profile.component';
import { ReservationsComponent } from './components/agent/reservations/reservations.component';
import { PaginatedTableReservationsComponent } from './components/common-components/paginated-table-reservations/paginated-table.component';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { StoreinfoDialogComponent } from './components/user/dialog/storeinfo-dialog/storeinfo-dialog.component';
import { MessageDialogComponent } from './components/common-components/message-dialog/message-dialog.component';
import { AddStarRatingComponent } from './components/common-components/add-star-rating/add-star-rating.component';
import { DisplayStarRatingComponent } from './components/common-components/display-star-rating/display-star-rating.component';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        SignupComponent,
        AgentComponent,
        ProductsComponent,
        DashboardComponent,
        CardComponent,
        AnnualSaleChartComponent,
        CustomerInformationComponent,
        BestSellingProductsComponent,
        ProductsComponent,
        NewMedComponent,
        DeleteMedComponent,
        UpdateMedComponent,
        UserComponent,
        PreviousOrdersComponent,
        UserDashboardComponent,
        AboutMedicineComponent,
        PaginatedTableProductsComponent,
        PaginatedTableReservationsComponent,
        OrdersComponent,
        PaginatedTableOrdersComponent,
        ProfileComponent,
        UserProfileComponent,
        MedDialogComponent,
        BloodDonationComponent,
        DonatebloodDialogComponent,
        FindStoresComponent,
        StoreListItemComponent,
        ReservationsComponent,
        StoreinfoDialogComponent,
        MessageDialogComponent,
        AddStarRatingComponent,
        DisplayStarRatingComponent
    ],
    providers: [{ provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: { hasBackdrop: false } }],
    bootstrap: [AppComponent],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        NgbModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        MatButtonToggleModule,
        NgChartsModule,
        MatToolbarModule,
        MatButtonModule,
        MatSidenavModule,
        MatIconModule,
        MatRadioModule,
        MatInputModule,
        MatSelectModule,
        MatListModule,
        MatSnackBarModule,
        MatGridListModule,
        MatCardModule,
        MatMenuModule,
        MatDialogModule,
        MatExpansionModule,
        MatTableModule,
        MatProgressBarModule,
        MatPaginatorModule,
        MatFormFieldModule,
        MatSortModule,
        MatSlideToggleModule,
        MatRadioModule,
        MatProgressSpinnerModule,
        NgxMaterialTimepickerModule,
        MatCheckboxModule,
    ]
})
export class AppModule { }

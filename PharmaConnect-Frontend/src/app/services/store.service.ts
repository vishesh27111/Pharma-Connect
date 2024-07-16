import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor(private http:HttpClient) {}

  getAllDrugs(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/store/drug/getAllDrugs`);
  }

  getDrugsForStore(storeId: string | null): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/store/availableDrugsForStore/${storeId}`);
  }

  getStorePurchases(storeId: string | null): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/reservation/store/reserved-bookings/${storeId}`);
  }

  addDrug(data: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/store/drug/addNewStoreDrugPrice`, data);
  }

  updateDrug(data: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/store/drug/updateStoreDrugPrice`, data);
  }

  deleteDrug(drugId: number, storeId: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiUrl}/store/drug/deleteDrug/${drugId}/${storeId}`);
  }

  getProfile(storeId: string | null, data: any, token: string | null): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/store/profile/${storeId}`);
  }

  updateProfile(requestBody: any, token: string | null) {
    return this.http.post<any>(`${environment.apiUrl}/store/update/profile`, requestBody, {
      headers: {
        "Authorization": "Bearer " + token
      }
    })
  }

  updatePassword(requestBody: any, token: string | null): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/store/update/password`, requestBody, {
      headers: {
        "Authorization": "Bearer " + token
      }
    });
  }

  completePurchase(reservationId: number): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/reservation/store/drug-purchase`, {"reservationId": reservationId});
  }

  getStoreReviews(storeId: string | null): Observable<any>{
    return this.http.get<any>(`${environment.apiUrl}/reviews/getStoreReviews/${storeId}`)
  }

  addStoreReviews(data: any): Observable<any>{
    return this.http.post<any>(`${environment.apiUrl}/reviews/addStoreReviews`, data)
  }

}

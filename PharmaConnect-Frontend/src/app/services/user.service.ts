import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  getMedicalStoresInCurrentLocation(requestBody: any): Observable<any> {
    return this.http.post<any>(`https://places.googleapis.com/v1/places:searchNearby`, requestBody, {
      headers: {
        "Content-Type": "application/json",
        "X-Goog-Api-Key": "AIzaSyDYQ8wDPj5AZ6LGQPHV3sg1Kj1EX3p0Y9g",
        "X-Goog-FieldMask": "*"
      }
    });
  }

  getAllDrugs(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/store/drug/getAllDrugs`);
  }

  getStoresForMedicines(drugId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/store/availableStoresForDrug/${drugId}`);
  }

  sendBloodDonationEmail(data: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/user/sendMail`, data);
  }

  getUserBookings(emailId: string | null): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/reservation/user/reserved-bookings/${emailId}`);
  }

  getUserProfile(requestBody: any, token: string | null): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/user/profile`, requestBody, {
      headers: {
        "Authorization": "Bearer " + token
      }
    });
  }

  updateUserProfile(requestBody: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/user/update/profile`, requestBody);
  }

  reserveMedicine(requestBody: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/reservation/user/drug-reserve`, requestBody);
  }

  getReservedBookings(userEmail: string | null): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/reservation/user/reserved-bookings/${userEmail}`);
  }

  updatePassword(requestBody: any, token: string | null): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/user/update/password`, requestBody, {
      headers: {
        "Authorization": "Bearer " + token
      }
    });
  }

  getGeolocationDetails(): Observable<any> {
    return this.http.get<any>(`https://geolocation-db.com/json/`);
  }
}

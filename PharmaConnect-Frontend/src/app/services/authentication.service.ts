import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  login(credentials: any, role: string): Observable<any> {
    if (role === "ROLE_USER") {
      return this.http.post<any>(`${environment.apiUrl}/api/auth/v1/signin/user`, credentials);
    }
    return this.http.post<any>(`${environment.apiUrl}/api/auth/v1/signin/store`, credentials);
  }

  signup(userInfo: any): Observable<any> {
    if (userInfo.role === "ROLE_USER") {
      return this.http.post<any>(`${environment.apiUrl}/api/auth/v1/signup/user`, userInfo);
    }
    return this.http.post<any>(`${environment.apiUrl}/api/auth/v1/signup/store`, userInfo);
  }
}

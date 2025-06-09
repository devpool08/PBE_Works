import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ViewcarService {
  private baseUrl = 'https://srxgoioj5k.execute-api.ap-south-1.amazonaws.com/dev/cars';

  constructor(private http: HttpClient) {}

  // Get all cars from API
  getCars(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  // Get car by ID from API
  getCarById(carId: string): Observable<any> {
    const url = `${this.baseUrl}/${carId}`;
    return this.http.get<any>(url);
  }
}

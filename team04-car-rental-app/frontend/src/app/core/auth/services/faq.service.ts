import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
 
export interface FaqResponse {
  content: FaqItem[];
}
 
export interface FaqItem {
  question: string;
  answer: string;
  isOpen?: boolean; // Optional flag for toggling visibility
}
 
@Injectable({
  providedIn: 'root'
})
export class FaqService {
  private apiUrl = 'https://srxgoioj5k.execute-api.ap-south-1.amazonaws.com/dev/home/faq';
 
  constructor(private http: HttpClient) {}
 
  getFaqs(): Observable<FaqResponse> {
    return this.http.get<FaqResponse>(this.apiUrl);
  }
}
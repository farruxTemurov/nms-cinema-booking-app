import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Booking } from '../models/booking.model';
import { BookingRequest } from '../models/booking-request.model';

@Injectable({ providedIn: 'root' })
export class BookingService {
  private baseUrl = 'http://localhost:9090/api/bookings';

  constructor(private http: HttpClient) { }

  bookMovie(request: BookingRequest): Observable<Booking> {
    return this.http.post<Booking>(this.baseUrl, request);
  }

  getUserBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.baseUrl}/my`);
  }

  getAllBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.baseUrl}/all`);
  }
}

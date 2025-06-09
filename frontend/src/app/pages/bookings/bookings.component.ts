import { Component, OnInit } from '@angular/core';
import { BookingService } from 'src/app/services/booking.service';
import { Booking } from 'src/app/models/booking.model';
import { BookingRequest } from 'src/app/models/booking-request.model';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.scss']
})
export class BookingsComponent implements OnInit {
  request: BookingRequest = {
    movieId: 0,
    theaterId: 0,
    bookingTime: ''
  };

  myBookings: Booking[] = [];
  allBookings: Booking[] = [];
  isAdmin: boolean = false;

  constructor(private bookingService: BookingService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.checkAdminAndLoadAllBookings();
  }

  onSubmit(): void {
    this.bookingService.bookMovie(this.request).subscribe({
      next: () => {
        this.toastr.success('Booking successful!');
        // Reload bookings based on role
        if (this.isAdmin) {
          this.loadAllBookings();
        } else {
          this.loadMyBookings();
        }
      },
      error: () => this.toastr.error('Booking failed.')
    });
  }

  loadMyBookings(): void {
    this.bookingService.getUserBookings().subscribe({
      next: (data) => this.myBookings = data,
      error: () => this.toastr.error('Failed to load your bookings.')
    });
  }

  loadAllBookings(): void {
    this.bookingService.getAllBookings().subscribe({
      next: (data) => this.allBookings = data,
      error: (err: HttpErrorResponse) => {
        console.error('Failed to reload all bookings:', err);
        this.toastr.error('Failed to load all bookings.');
      }
    });
  }

  checkAdminAndLoadAllBookings(): void {
    this.bookingService.getAllBookings().subscribe({
      next: (data) => {
        this.allBookings = data;
        this.isAdmin = true;
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 403 || err.status === 401) {
          // User not admin, load user bookings instead
          this.isAdmin = false;
          this.loadMyBookings();
        } else {
          console.error('Failed to load admin bookings:', err);
          this.toastr.error('Failed to load bookings.');
        }
      }
    });
  }
}

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
export interface BookingDetails {
  car: {
    model: string;
    rating: number;
    location: string;
    availability: string;
    pricePerDay: number;
    features: string[];
    mainPhoto: string;
    sidePhotos: string[];
    startMileage?: number; // Optional field for start mileage
    endMileage?: number; // Optional field for end mileage
  };
  pickupLocation: string;
  dropoffLocation: string;
  pickupDate: Date;
  pickupTime: string;
  dropoffDate: Date;
  dropoffTime: string;
  totalPrice?: number;
  status?: string; // Add status field
  orderId?: string; // Add orderId field if needed
  date?: string;
  bookingNumber?: number;
  clientName?: string ; // Client name or ID
  madeBy?: string;
  period?: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
// Default client name
  // Single booking details (for current booking flow)
  private bookingDetailsSubject = new BehaviorSubject<BookingDetails | null>(null);
  bookingDetails$ = this.bookingDetailsSubject.asObservable();

  setBookingDetails(data: BookingDetails): void {
    this.bookingDetailsSubject.next(data);
    console.log('Booking details set (via subject):', data);
  }

  getBookingDetails(): Observable<BookingDetails | null> {
    console.log("Getting booking details...");
    return this.bookingDetails$;
  }

  getCurrentBookingDetails(): BookingDetails | null {
    return this.bookingDetailsSubject.value;
  }

  // Multiple bookings management (used for My Bookings page)
  private bookingsSubject = new BehaviorSubject<BookingDetails[]>([]);
  bookings$ = this.bookingsSubject.asObservable();
  private editingBookingSource = new BehaviorSubject<BookingDetails | null>(null);
  currentEditingBooking$ = this.editingBookingSource.asObservable();

  addBooking(booking: BookingDetails): void {
    const currentBookings = this.bookingsSubject.getValue();
    
    // Check if booking with this orderId already exists
    const existingIndex = currentBookings.findIndex(b => b.orderId === booking.orderId);
    
    if (existingIndex !== -1) {
      // Replace existing booking
      currentBookings[existingIndex] = booking;
    } else {
      // Add new booking
      currentBookings.push(booking);
    }
    
    this.bookingsSubject.next([...currentBookings]);
  }

  getBookings(): BookingDetails[] {
    return this.bookingsSubject.getValue();
  }

  clearBookings(): void {
    this.bookingsSubject.next([]);
    console.log('All bookings cleared.');
  }
  

setEditingBooking(booking: BookingDetails) {
  this.editingBookingSource.next(booking);
}

clearEditingBooking() {
  this.editingBookingSource.next(null);
}
  updateBooking(updatedBooking: BookingDetails): void {
    const currentBookings = this.bookingsSubject.getValue();
    const index = currentBookings.findIndex(b => b.orderId === updatedBooking.orderId); // Find the booking by orderId
  
    if (index !== -1) {
      // Remove the old booking and add the updated one
      currentBookings.splice(index, 1, updatedBooking); // Replace the old booking with the updated one
      this.bookingsSubject.next([...currentBookings]); // Emit the updated list
    } else {
      console.error('Booking not found for update!');
    }
  }

  // Optional: Remove a booking by orderId
  removeBooking(orderId: string): void {
    const currentBookings = this.bookingsSubject.getValue();
    const updatedBookings = currentBookings.filter(booking => booking.orderId !== orderId);
    this.bookingsSubject.next(updatedBookings); // Emit the updated list
  }
}

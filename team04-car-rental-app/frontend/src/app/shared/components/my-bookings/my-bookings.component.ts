import { Component,OnInit } from '@angular/core';
import { BookingService } from '../../../core/auth/services/booking.service';
import { CommonModule,DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { FormsModule } from '@angular/forms';
import { FeedbackDialogComponent } from '../feedback-dialog/feedback-dialog.component';
@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.css'],
  standalone: true,
  imports: [CommonModule,ConfirmationDialogComponent, FormsModule, FeedbackDialogComponent]
})
export class MyBookingsComponent implements OnInit {
  showFeedbackDialog = false;
  bookingToFeedback: any = null;
  bookings: any[] = [];
  filteredBookings: any[] = [];
  tabs = [
    'All Bookings',
    'Reserved',
    'Service Started',
    'Service Provided',
    'Booking Finished',
    'Cancelled'
  ];
  selectedTab = 0;
  isMenuOpen = false;
  showCancelDialog = false;
  bookingToCancel: any = null;

  constructor(
    private bookingService: BookingService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings() {
    this.bookingService.bookings$.subscribe(bookings => {
      this.bookings = bookings.map(booking => ({
        ...booking,
        status: this.determineBookingStatus(booking),
        pickupDateTime: new Date(`${booking.pickupDate.toDateString()} ${booking.pickupTime}`).getTime(),
        dropoffDateTime: new Date(`${booking.dropoffDate.toDateString()} ${booking.dropoffTime}`).getTime()
      }));
      this.filterBookings();
    });
  }

  determineBookingStatus(booking: any): string {
    const now = new Date().getTime();
    
    // Manual status overrides
    if (booking.status === 'Cancelled') return 'Cancelled';
    if (booking.status === 'Booking Finished') return 'Booking Finished';

    // Automatic status based on time
    if (!booking.pickupDateTime || now < booking.pickupDateTime) {
      return 'Reserved';
    } else if (now >= booking.pickupDateTime && now < booking.dropoffDateTime) {
      return 'Service Started';
    } else {
      return 'Service Provided';
    }
  }

  selectTab(index: number) {
    this.selectedTab = index;
    this.filterBookings();
    this.isMenuOpen = false; // Close the menu when a tab is selected
  }

  filterBookings() {
    switch (this.selectedTab) {
      case 0: // All Bookings
        this.filteredBookings = [...this.bookings];
        break;
      case 1: // Reserved
        this.filteredBookings = this.bookings.filter(b => b.status === 'Reserved');
        break;
      case 2: // Service Started
        this.filteredBookings = this.bookings.filter(b => 
          b.status === 'Service Started' || b.status === 'Service Provided'
        );
        break;
      case 3: // Service Provided
        this.filteredBookings = this.bookings.filter(b => b.status === 'Service Provided');
        break;
      case 4: // Booking Finished
        this.filteredBookings = this.bookings.filter(b => b.status === 'Booking Finished');
        break;
      case 5: // Cancelled
        this.filteredBookings = this.bookings.filter(b => b.status === 'Cancelled');
        break;
      default:
        this.filteredBookings = [...this.bookings];
    }
  }

  getTotalCost(booking: any): number {
    const oneDay = 24 * 60 * 60 * 1000;
    const diffDays = Math.round(Math.abs((booking.dropoffDate - booking.pickupDate) / oneDay) || 1);
    return booking.car?.pricePerDay ? booking.car.pricePerDay * diffDays : 0;
  }

  cancelBooking(booking: any) {
    booking.status = 'Cancelled';
    this.filterBookings();
  }

  markAsFinished(booking: any) {
    booking.status = 'Booking Finished';
    this.filterBookings();
  }

  // my-bookings.component.ts
editBooking(booking: any) {
  this.bookingService.setEditingBooking(booking);
  this.router.navigate(['/edit-booking']);
}
  openCancelDialog(booking: any) {
    this.bookingToCancel = booking;
    this.showCancelDialog = true;
  }

  confirmCancel() {
    if (this.bookingToCancel) {
      this.bookingToCancel.status = 'Cancelled';
      this.filterBookings();
    }
    this.showCancelDialog = false;
    this.bookingToCancel = null;
  }

  cancelCancel() {
    this.showCancelDialog = false;
    this.bookingToCancel = null;
  }
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  canCancel(booking: any): boolean {
    const now = new Date().getTime();
    const pickupTime = new Date(`${booking.pickupDate.toDateString()} ${booking.pickupTime}`).getTime();
    const diffInMs = pickupTime - now;
    const diffInHours = diffInMs / (1000 * 60 * 60);
   
    return diffInHours > 12;
  }
  openFeedbackDialog(booking: any) {

    this.bookingToFeedback = booking;
  
    this.showFeedbackDialog = true;
  
  }
   
  submitFeedback(data: { rating: number, feedback: string }) {
  
    console.log('Feedback submitted:', data);
  
    if (this.bookingToFeedback) {
  
      this.bookingToFeedback.status = 'Booking Finished';
  
      // Optionally store rating/feedback somewhere
  
      this.filterBookings();
  
    }
  
    this.showFeedbackDialog = false;
  
    this.bookingToFeedback = null;
  
  }
   
  cancelFeedback() {
  
    this.showFeedbackDialog = false;
  
    this.bookingToFeedback = null;
  
  }
  
   

  // Handle tab selection


}
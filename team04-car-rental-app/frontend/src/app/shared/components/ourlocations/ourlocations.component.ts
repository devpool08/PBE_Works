import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { SafePipe } from '../../pipes/safe.pipe';

@Component({
  selector: 'app-ourlocations',
  standalone: true,
  imports: [CommonModule, SafePipe],
  templateUrl: './ourlocations.component.html',
  styleUrl: './ourlocations.component.css'
})
export class OurlocationsComponent implements OnInit {
  locations: any[] = [];
  selectedIndex = 0;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<any[]>('assets/data/locations.json').subscribe(data => {
      this.locations = data;
    });
  }

  selectLocation(index: number) {
    this.selectedIndex = index;
  }

  get mapUrl(): string {
    const loc = this.locations[this.selectedIndex];
    return loc
      ? `https://maps.google.com/maps?q=${loc.lat},${loc.lng}&z=15&output=embed`
      : '';
  }
}

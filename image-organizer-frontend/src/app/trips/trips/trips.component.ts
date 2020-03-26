import { Component, OnInit } from '@angular/core';
import {TripService} from '../shared/service/trip.service';
import {Trip} from '../shared/model/trip.model';
import {DateService} from '../../shared/service/date.service';

@Component({
  selector: 'app-trips',
  templateUrl: './trips.component.html',
  styleUrls: ['./trips.component.scss']
})
export class TripsComponent implements OnInit {

  trips: Trip[] = [];

  tripService: TripService;

  dateService: DateService;

  constructor(tripService: TripService,
              dateService: DateService) {
    this.tripService = tripService;
    this.dateService = dateService;
  }

  ngOnInit(): void {
    this.tripService.trips$
      .subscribe(trips => {
        if (trips.length > 0) {
          trips.forEach(trip => {
            const isNew = this.trips.find(existingTrip => existingTrip.id === trip.id) === undefined;
            if (isNew) {
              this.trips.push(trip);
            }
          });
        } else {
          this.trips = [];
        }
      })
  }

}

import {Component, Input, OnInit} from '@angular/core';
import {Trip} from '../shared/model/trip.model';
import {NgForm} from '@angular/forms';
import {TripService} from '../shared/service/trip.service';

@Component({
  selector: 'app-trip-name-form',
  templateUrl: './trip-name-form.component.html',
  styleUrls: ['./trip-name-form.component.scss']
})
export class TripNameFormComponent {

  @Input() trip: Trip;

  constructor(private tripService: TripService) { }

  update(trip: Trip, form: NgForm, $event: any) {
    $event.preventDefault();
    this.tripService.update(trip)
      .subscribe(() => {
        form.form.markAsPristine();
      });
  }

}

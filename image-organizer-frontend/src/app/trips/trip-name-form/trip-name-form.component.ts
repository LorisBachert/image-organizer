import {Component, Input, OnInit} from '@angular/core';
import {Trip} from '../shared/model/trip.model';
import {NgForm} from '@angular/forms';
import {TripService} from '../shared/service/trip.service';

@Component({
  selector: 'app-trip-name-form',
  templateUrl: './trip-name-form.component.html',
  styleUrls: ['./trip-name-form.component.scss']
})
export class TripNameFormComponent implements OnInit {

  @Input() trip: Trip;

  constructor(private tripService: TripService) { }

  ngOnInit(): void {
  }

  update(trip: Trip, form: NgForm, $event: any) {
    $event.preventDefault();
    $event.stopPropagation();
    this.tripService.update(trip)
      .subscribe(() => {
        form.form.markAsPristine();
      });
  }

}

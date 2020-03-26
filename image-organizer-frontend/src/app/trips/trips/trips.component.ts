import { Component, OnInit } from '@angular/core';
import {TripService} from '../shared/service/trip.service';
import {Trip} from '../shared/model/trip.model';
import {DateService} from '../../shared/service/date.service';
import {CdkDragDrop} from '@angular/cdk/drag-drop';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import * as arrayMove from 'array-move';

class ImageDragData {
  trip: Trip;
  image: FileMetadata;
  imageIndex: number;
}

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

  drop(newTrip: Trip, $event: CdkDragDrop<FileMetadata[], any>) {
    if ($event.previousContainer.id !== $event.container.id) {
      const data: ImageDragData = $event.item.data;
      data.trip.files.splice(data.imageIndex, 1);
      newTrip.files.push(data.image);
    } else if ($event.previousIndex !== $event.currentIndex) {
      arrayMove.mutate(newTrip.files, $event.previousIndex, $event.currentIndex);
    }
  }

  imageDragData(trip: Trip, image: FileMetadata, imageIndex: number): ImageDragData {
    return {
      trip,
      image,
      imageIndex
    }
  }
}

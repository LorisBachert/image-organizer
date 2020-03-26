import {Component, OnInit} from '@angular/core';
import {TripService} from '../shared/service/trip.service';
import {Trip} from '../shared/model/trip.model';
import {DateService} from '../../shared/service/date.service';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import * as arrayMove from 'array-move';
import {ImageService} from '../../core/image/image.service';
import {combineLatest, Observable} from 'rxjs';

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

  showDeleted = false;

  constructor(tripService: TripService,
              dateService: DateService,
              private imageService: ImageService) {
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

  drop(trip: Trip, $event: CdkDragDrop<number[], any>) {
    let changed = false;
    if ($event.previousContainer.id !== $event.container.id) {
      const data: ImageDragData = $event.item.data;
      data.trip.files.splice(data.imageIndex, 1);
      trip.files.push(data.image.id);
      changed = true;
    } else if ($event.previousIndex !== $event.currentIndex) {
      moveItemInArray(trip.files, $event.previousIndex, $event.currentIndex);
      changed = true;
    }
    if (changed) {
      this.tripService.update(trip)
        .subscribe(() => {})
    }
  }

  imageDragData(trip: Trip, image: FileMetadata, imageIndex: number): ImageDragData {
    return {
      trip,
      image,
      imageIndex
    }
  }

  getImages(trip: Trip): Observable<FileMetadata[]> {
    return combineLatest(trip.files.map(id => this.imageService.images[id]));
  }

  toggleImageDeletion(image: FileMetadata) {
    this.imageService.toggleDeletion(image.id);
  }

  update(trip: Trip, $event: any) {
    $event.preventDefault();
    this.tripService.update(trip)
      .subscribe(() => {});
  }
}

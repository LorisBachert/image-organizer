import {Component, OnInit} from '@angular/core';
import {DateService} from '../../shared/service/date.service';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import {ImageService} from '../../core/image/image.service';
import {combineLatest, Observable} from 'rxjs';
import {GalleriesService} from '../shared/service/galleries.service';
import {Gallery} from '../shared/model/gallery.model';

@Component({
  selector: 'app-galleries',
  templateUrl: './galleries.component.html',
  styleUrls: ['./galleries.component.scss']
})
export class GalleriesComponent implements OnInit {

  trips: Gallery[] = [];

  galleriesService: GalleriesService;

  showDeleted = false;

  constructor(galleriesService: GalleriesService,
              dateService: DateService,
              private imageService: ImageService) {
    this.galleriesService = galleriesService;
  }

  ngOnInit(): void {
    this.galleriesService.trips$
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

  getImages(gallery: Gallery): Observable<FileMetadata[]> {
    return combineLatest(gallery.files.map(id => this.imageService.images[id]));
  }

  toggleImageDeletion(image: FileMetadata) {
    this.imageService.toggleDeletion(image.id);
  }
}

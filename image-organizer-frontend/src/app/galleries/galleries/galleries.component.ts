import {Component, OnInit} from '@angular/core';
import {DateService} from '../../shared/service/date.service';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import {ImageService} from '../../core/image/image.service';
import {combineLatest, Observable} from 'rxjs';
import {GalleriesService} from '../shared/service/galleries.service';
import {Gallery} from '../shared/model/gallery.model';
import {MatExpansionPanel} from '@angular/material/expansion';

@Component({
  selector: 'app-galleries',
  templateUrl: './galleries.component.html',
  styleUrls: ['./galleries.component.scss']
})
export class GalleriesComponent implements OnInit {

  selectedIndex = 0;

  galleries: Gallery[] = [];

  showDeleted = true;

  newGalleryName: string;

  galleriesService: GalleriesService;

  constructor(galleriesService: GalleriesService) {
    this.galleriesService = galleriesService;
  }

  ngOnInit(): void {
    this.galleriesService.galleries$
      .subscribe(galleries => {
        if (galleries.length > 0) {
          galleries.forEach(gallery => {
            const isNew = this.galleries.find(existingGallery => existingGallery.id === gallery.id) === undefined;
            if (isNew) {
              this.galleries.push(gallery);
            }
          });
        } else {
          this.galleries = [];
        }
      })
  }

  previousGallery() {
    this.selectedIndex = Math.max(0, this.selectedIndex - 1);
  }

  nextGallery() {
    this.selectedIndex = Math.min(this.galleries.length - 1, this.selectedIndex + 1);
  }

  addGallery() {
    this.galleriesService.create(this.newGalleryName)
      .subscribe(gallery => {
        this.newGalleryName = '';
      })
  }

  toggleFavorite(gallery: Gallery) {
    gallery.favorite = ! gallery.favorite;
    this.galleriesService.update(gallery)
      .subscribe(() => {});
  }

  setSelctedIndex(index: number) {
    this.selectedIndex = index;
    setTimeout(() => {
      const panelRef = document.getElementById('panel-' + this.selectedIndex);
      if (panelRef) {
        panelRef.scrollIntoView({ behavior: 'smooth'});
      }
    }, 250);
  }
}

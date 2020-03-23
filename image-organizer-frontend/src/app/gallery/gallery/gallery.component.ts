import {Component, OnInit} from '@angular/core';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import {GalleryService} from '../shared/service/gallery.service';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {

  images: FileMetadata[] = [];

  page = 0;

  constructor(private galleryService: GalleryService) {
  }

  ngOnInit(): void {
    this.galleryService.findGallery(this.page)
      .subscribe(images => {
        this.images.push(...images);
      });
  }

}

import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {GalleriesService} from '../../service/galleries.service';
import {Gallery} from '../../model/gallery.model';
import {BehaviorSubject, Observable} from 'rxjs';
import {MatMenuTrigger} from '@angular/material/menu';

@Component({
  selector: 'app-gallery-menu',
  templateUrl: './gallery-menu.component.html',
  styleUrls: ['./gallery-menu.component.scss']
})
export class GalleryMenuComponent implements OnInit {

  @Input() currentGalleryId: number;

  @Output() select = new EventEmitter<Gallery>();

  favorites = new BehaviorSubject<Gallery[]>([]);

  nonFavorites = new BehaviorSubject<Gallery[]>([]);

  newGalleryName: string;

  @ViewChild(MatMenuTrigger) menuTrigger: MatMenuTrigger;

  constructor(private galleriesService: GalleriesService) {
  }

  ngOnInit(): void {
    this.galleriesService.galleries$.subscribe(galleries => {
      this.favorites.next(galleries.filter(g => g.favorite && g.id !== this.currentGalleryId));
      this.nonFavorites.next(galleries.filter(g => ! g.favorite && g.id !== this.currentGalleryId));
    })
  }

  open() {
    this.menuTrigger.openMenu();
  }

  createGallery() {
    this.galleriesService.create(this.newGalleryName)
      .subscribe(newGallery => {
        this.select.emit(newGallery);
        this.menuTrigger.closeMenu();
        this.newGalleryName = '';
      })
  }
}

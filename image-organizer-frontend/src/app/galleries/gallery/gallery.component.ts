import {
  AfterViewInit,
  Component,
  EventEmitter,
  HostListener,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import {Gallery} from '../shared/model/gallery.model';
import {BehaviorSubject, combineLatest, Observable} from 'rxjs';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import {ImageService} from '../../core/image/image.service';
import {GalleriesService} from '../shared/service/galleries.service';
import {MatMenu, MatMenuTrigger} from '@angular/material/menu';
import {filter, map} from 'rxjs/operators';
import {GalleryMenuComponent} from '../shared/component/gallery-menu/gallery-menu.component';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnChanges {

  @Input() gallery: Gallery;

  @Input() showDeletedImages: boolean;

  @Input() duplicateMode: boolean;

  @Output() previous = new EventEmitter<void>();

  @Output() next = new EventEmitter<void>();

  images$ = new BehaviorSubject<FileMetadata[]>([]);

  selectedIndex = 0;

  selectedIndexes = [0];

  galleriesService: GalleriesService;

  @ViewChild(GalleryMenuComponent) menu: GalleryMenuComponent;

  constructor(private imageService: ImageService,
              galleriesService: GalleriesService) {
    this.galleriesService = galleriesService;
  }

  ngOnChanges(changes: SimpleChanges): void {
    const showDeletedImages = changes.showDeletedImages ? changes.showDeletedImages.currentValue : this.showDeletedImages;
    const gallery = changes.gallery ? changes.gallery.currentValue : this.gallery;
    this.updateImages(gallery, showDeletedImages)
  }

  updateImages(gallery: Gallery, showDeletedImages: boolean) {
    this.getImages(gallery)
      .subscribe(images => {
        this.images$.next(images.filter(image => ! image.toDelete || showDeletedImages));
      })
  }

  getImages(gallery: Gallery): Observable<FileMetadata[]> {
    return combineLatest(gallery.files.map(id => this.imageService.getImage(id)));
  }

  @HostListener('window:keyup', ['$event'])
  keyUp($event: KeyboardEvent) {
    // select all
    if ($event.key.toLowerCase() === 'a' && $event.shiftKey) {
      this.selectAllImages();
    } else if ($event.key === 'ArrowLeft') {
      this.selectPreviousImage($event);
    } else if ($event.key === 'ArrowRight') {
      this.selectNextImage($event);
    } else if ($event.key.toLowerCase() === 'd' || $event.key.toLowerCase() === 'delete') {
      this.markSelectedImagesForDeletion();
    } else if ($event.key.toLowerCase() === 'a') {
      this.markSelectedImagesToKeep();
    } else if ($event.key.toLowerCase() === 'm' && ! this.duplicateMode) {
      this.menu.open();
    }
  }

  private markSelectedImagesToKeep() {
    const isAlreadyAtTheEnd = this.selectedIndex >= this.images$.getValue().length - 1;
    const maxIndex = Math.max(...this.selectedIndexes.values());
    this.doForSelection(imageId => this.imageService.markForDeletion(imageId, false));
    if (isAlreadyAtTheEnd) {
      if (this.selectedIndex >= this.images$.getValue().length - 1) {
        this.next.emit();
        return;
      }
    } else {
      this.setSelectedIndex(Math.min(this.images$.getValue().length - 1, maxIndex + 1));
      this.selectedIndexes = [this.selectedIndex];
    }
  }

  private markSelectedImagesForDeletion() {
    const minIndex = Math.min(...this.selectedIndexes.values());
    this.doForSelection(imageId => this.imageService.markForDeletion(imageId, true));
    if (this.showDeletedImages) {
      this.setSelectedIndex(this.selectedIndex + 1)
      if (this.selectedIndex > this.images$.getValue().length - 1) {
        this.next.emit();
        return;
      }
    } else {
      this.setSelectedIndex(minIndex);
    }
    this.selectedIndexes = [this.selectedIndex];
  }

  private selectNextImage($event: KeyboardEvent) {
    const isAlreadyAtTheEnd = this.selectedIndex >= this.images$.getValue().length - 1;
    if (isAlreadyAtTheEnd && !$event.shiftKey) {
      this.next.emit();
      return;
    }
    this.setSelectedIndex(Math.min(this.selectedIndex + 1, this.images$.getValue().length - 1));
    if ($event.shiftKey && !isAlreadyAtTheEnd) {
      this.selectedIndexes.push(this.selectedIndex);
    } else if (!$event.shiftKey) {
      this.selectedIndexes = [this.selectedIndex];
    }
  }

  private selectPreviousImage($event: KeyboardEvent) {
    if (this.selectedIndex <= 0 && !$event.shiftKey) {
      this.previous.emit();
      return;
    }
    this.setSelectedIndex(Math.max(this.selectedIndex - 1, 0));
    if (!$event.shiftKey || this.selectedIndex === 0) {
      this.selectedIndexes = [this.selectedIndex];
    } else {
      this.selectedIndexes.splice(this.selectedIndexes.length - 1, 1);
    }
  }

  private selectAllImages() {
    this.selectedIndexes = this.images$.getValue().map((image, index) => index);
    this.setSelectedIndex(this.selectedIndexes[this.selectedIndexes.length - 1]);
  }

  private setSelectedIndex(selectedIndex: number) {
    this.selectedIndex = selectedIndex;
    const imageRef = document.getElementById('image-' + this.selectedIndex);
    if (imageRef) {
      imageRef.scrollIntoView({ behavior: 'smooth' });
    }
  }

  private doForSelection(runnable: (imageId: number) => any) {
    this.selectedIndexes.forEach(selectedIndex => {
      runnable(this.images$.getValue()[selectedIndex].id);
    })
  }

  isSelected(index: number) {
    return this.selectedIndexes.findIndex(i => i === index) > -1;
  }

  moveSelectionTo(gallery: Gallery) {
    if (this.duplicateMode) {
      return;
    }
    const minIndex = Math.min(...this.selectedIndexes.values());
    this.doForSelection(imageId => {
      gallery.files.push(imageId);
      this.galleriesService.update(gallery)
        .subscribe(() => {
          this.gallery.files.splice(this.gallery.files.findIndex(f => f === imageId), 1);
          this.galleriesService.update(this.gallery).subscribe(() => {
            this.updateImages(this.gallery, this.showDeletedImages);
            this.setSelectedIndex(minIndex);
            this.selectedIndexes = [this.selectedIndex];
          })
        });
    })
  }
}

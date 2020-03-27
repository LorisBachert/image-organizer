import {
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

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit, OnChanges {

  @Input() gallery: Gallery;

  @Input() showDeletedImages: boolean;

  @Output() previous = new EventEmitter<void>();

  @Output() next = new EventEmitter<void>();

  images$ = new BehaviorSubject<FileMetadata[]>([]);

  selectedIndex = 0;

  selectedIndexes = [0];

  galleriesService: GalleriesService;

  @ViewChild(MatMenuTrigger) menuTrigger: MatMenuTrigger;

  constructor(private imageService: ImageService,
              galleriesService: GalleriesService) {
    this.galleriesService = galleriesService;
  }

  ngOnInit(): void {
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
    if ($event.key.toLowerCase() === 'a' && $event.shiftKey) {
      this.selectedIndexes = this.images$.getValue().map((image, index) => index);
      this.selectedIndex = this.selectedIndexes[this.selectedIndexes.length - 1];
    } else if ($event.key === 'ArrowLeft') {
      if (this.selectedIndex <= 0 && !$event.shiftKey) {
        this.previous.emit();
        return;
      }
      this.selectedIndex = Math.max(this.selectedIndex - 1, 0);
      if (!$event.shiftKey || this.selectedIndex === 0) {
        this.selectedIndexes = [this.selectedIndex];
      } else {
        this.selectedIndexes.splice(this.selectedIndexes.length - 1, 1);
      }
    } else if ($event.key === 'ArrowRight') {
      const isAlreadyAtTheEnd = this.selectedIndex >= this.images$.getValue().length - 1;
      if (isAlreadyAtTheEnd && !$event.shiftKey) {
        this.next.emit();
        return;
      }
      this.selectedIndex = Math.min(this.selectedIndex + 1, this.images$.getValue().length - 1);
      if ($event.shiftKey && !isAlreadyAtTheEnd) {
        this.selectedIndexes.push(this.selectedIndex);
      } else if (!$event.shiftKey) {
        this.selectedIndexes = [this.selectedIndex];
      }
    } else if ($event.key.toLowerCase() === 'd' || $event.key.toLowerCase() === 'delete') {
      const minIndex = Math.min(...this.selectedIndexes.values());
      this.doForSelection(imageId => this.imageService.markForDeletion(imageId, true));
      if (this.showDeletedImages) {
        this.selectedIndex++;
        if (this.selectedIndex > this.images$.getValue().length - 1) {
          this.next.emit();
          return;
        }
      } else {
        this.selectedIndex = minIndex
      }
      this.selectedIndexes = [this.selectedIndex];
    } else if ($event.key.toLowerCase() === 'a') {
      const isAlreadyAtTheEnd = this.selectedIndex >= this.images$.getValue().length - 1;
      const maxIndex = Math.max(...this.selectedIndexes.values());
      this.doForSelection(imageId => this.imageService.markForDeletion(imageId, false));
      if (isAlreadyAtTheEnd) {
        if (this.selectedIndex >= this.images$.getValue().length - 1) {
          this.next.emit();
          return;
        }
      } else {
        this.selectedIndex = Math.min(this.images$.getValue().length - 1, maxIndex + 1);
        this.selectedIndexes = [this.selectedIndex];
      }
    } else if ($event.key.toLowerCase() === 'm') {
      this.menuTrigger.openMenu();
    }
    const imageRef = document.getElementById('image-' + this.selectedIndex);
    imageRef.scrollIntoView({ behavior: 'smooth' });
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
    const minIndex = Math.min(...this.selectedIndexes.values());
    this.doForSelection(imageId => {
      gallery.files.push(imageId);
      this.galleriesService.update(gallery)
        .subscribe(() => {
          this.gallery.files.splice(this.gallery.files.findIndex(f => f === imageId), 1);
          this.galleriesService.update(this.gallery).subscribe(() => {
            this.updateImages(this.gallery, this.showDeletedImages);
            this.selectedIndex = minIndex;
            this.selectedIndexes = [this.selectedIndex];
          })
        });
    })
  }

  galleriesToMoveTo(): Observable<Gallery[]> {
    return this.galleriesService.galleries$
      .pipe(
        map(galleries => galleries.filter(gallery => gallery.id !== this.gallery.id))
      )
  }
}

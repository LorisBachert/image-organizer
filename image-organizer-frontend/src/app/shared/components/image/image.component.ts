import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FileMetadata} from '../../model/file-metadata.model';
import {environment} from '../../../../environments/environment';
import {ImageService} from '../../../core/image/image.service';
import {Observable, of} from 'rxjs';

@Component({
  selector: 'app-image',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.scss']
})
export class ImageComponent implements OnChanges {

  @Input() id: number;

  image: Observable<FileMetadata> = of(null);

  url: string;

  constructor(private imageService: ImageService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.id) {
      this.image = this.imageService.images[changes.id.currentValue];
      this.url = `${environment.api}/images/${changes.id.currentValue}`;
    }
  }
}

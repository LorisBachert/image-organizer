import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FileMetadata} from '../../model/file-metadata.model';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-image',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.scss']
})
export class ImageComponent implements OnChanges {

  @Input() image: FileMetadata;

  url: string;

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.image) {
      const image: FileMetadata = changes.image.currentValue;
      this.url = `${environment.api}/images?path=${image.path.replace('file:///', '')}`;
    }
  }
}

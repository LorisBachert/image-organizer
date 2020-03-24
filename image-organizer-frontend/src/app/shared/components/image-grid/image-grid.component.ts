import {Component, Input, OnInit} from '@angular/core';
import {FileMetadata} from '../../model/file-metadata.model';

@Component({
  selector: 'app-image-grid',
  templateUrl: './image-grid.component.html',
  styleUrls: ['./image-grid.component.scss']
})
export class ImageGridComponent implements OnInit {

  @Input() images: FileMetadata[];

  constructor() {
  }

  ngOnInit(): void {
  }

}

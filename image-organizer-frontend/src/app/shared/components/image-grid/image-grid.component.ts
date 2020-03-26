import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FileMetadata} from '../../model/file-metadata.model';

@Component({
  selector: 'app-image-grid',
  templateUrl: './image-grid.component.html',
  styleUrls: ['./image-grid.component.scss']
})
export class ImageGridComponent implements OnInit {

  @Input() ids: number[];

  @Output() imageClick = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit(): void {
  }

}

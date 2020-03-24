import {Component, Input, OnInit} from '@angular/core';
import {FileMetadata} from '../../model/file-metadata.model';

@Component({
  selector: 'app-image',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.scss']
})
export class ImageComponent implements OnInit {

  @Input() image: FileMetadata;

  constructor() { }

  ngOnInit(): void {
  }

}

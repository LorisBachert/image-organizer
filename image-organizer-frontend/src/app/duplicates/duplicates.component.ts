import { Component, OnInit } from '@angular/core';
import {ImageService} from '../service/image.service';
import {Duplicates} from '../model/duplicates.model';
import {FileMetadata} from '../model/file-metadata.model';
import {MatCheckboxChange} from '@angular/material/checkbox';

@Component({
  selector: 'app-duplicates',
  templateUrl: './duplicates.component.html',
  styleUrls: ['./duplicates.component.scss']
})
export class DuplicatesComponent implements OnInit {

  duplicates: Duplicates[] = [];

  constructor(private imageService: ImageService) { }

  ngOnInit(): void {
    this.imageService.findDuplicates()
      .subscribe(duplicates => {
        this.duplicates = duplicates;
      });
  }

  markOthersAsDeleted(duplicate: Duplicates, image: FileMetadata) {
    duplicate.files.forEach(file => file.toDelete = true);
    image.toDelete = false;
  }

  markAll(duplicate: Duplicates, toDelete: boolean) {
    duplicate.files.forEach(file => file.toDelete = toDelete);
  }
}

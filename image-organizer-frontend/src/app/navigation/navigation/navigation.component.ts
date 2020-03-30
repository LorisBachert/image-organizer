import { Component, OnInit } from '@angular/core';
import {DuplicateService} from '../../duplicate/shared/service/duplicate.service';
import {ProcessService} from '../../core/process/process.service';
import {GalleriesService} from '../../galleries/shared/service/galleries.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  processService: ProcessService;
  duplicateService: DuplicateService;
  galleriesService: GalleriesService;

  constructor(processService: ProcessService,
              duplicateService: DuplicateService,
              galleriesService: GalleriesService) {
    this.processService = processService;
    this.duplicateService = duplicateService;
    this.galleriesService = galleriesService;
  }

  ngOnInit(): void {
  }

  end() {
    this.processService.end()
      .subscribe(() => {
      })
  }
}

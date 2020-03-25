import { Component, OnInit } from '@angular/core';
import {DuplicateService} from '../../duplicate/shared/service/duplicate.service';
import {ProcessService} from '../shared/service/process.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  processService: ProcessService;
  duplicateService: DuplicateService;

  constructor(processService: ProcessService,
              duplicateService: DuplicateService) {
    this.processService = processService;
    this.duplicateService = duplicateService;
  }

  ngOnInit(): void {
  }

}

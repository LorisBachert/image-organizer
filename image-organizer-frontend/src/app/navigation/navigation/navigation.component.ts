import { Component, OnInit } from '@angular/core';
import {DuplicateService} from '../../duplicate/shared/service/duplicate.service';
import {ProcessService} from '../../core/process/process.service';
import {TripService} from '../../trips/shared/service/trip.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  processService: ProcessService;
  duplicateService: DuplicateService;
  tripService: TripService;

  constructor(processService: ProcessService,
              duplicateService: DuplicateService,
              tripService: TripService) {
    this.processService = processService;
    this.duplicateService = duplicateService;
    this.tripService = tripService;
  }

  ngOnInit(): void {
  }

  end() {
    this.processService.end()
      .subscribe(() => {
        console.log('done');
      })
  }
}

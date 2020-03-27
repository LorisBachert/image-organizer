import {Component, OnInit} from '@angular/core';
import {Configuration} from '../shared/model/configuration.model';
import {ProcessService} from '../../core/process/process.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  config: Configuration = {
    directory: 'D:\\Bilder',
    duplicates: {
      enabled: true,
      diff: 10
    },
    trips: {
      enabled: true,
      hoursBetween: 36,
      distance: 10
    }
  };

  constructor(private processService: ProcessService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  startProcess() {
    this.processService.startProcess(this.config)
      .subscribe(() => {
        if (this.config.duplicates.enabled) {
          this.router.navigateByUrl('/duplicates')
        } else if (this.config.trips.enabled) {
          this.router.navigateByUrl('/trips')
        }
      });
  }
}

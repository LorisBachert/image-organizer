import {Component, OnInit} from '@angular/core';
import {ProcessService} from './core/process/process.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'image-organizer-frontend';

  constructor(private processService: ProcessService) {
  }

  ngOnInit(): void {
    this.processService.findProcessState().subscribe(() => {});
  }
}

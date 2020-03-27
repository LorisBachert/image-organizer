import {Component, OnInit} from '@angular/core';
import {ProcessService} from './core/process/process.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'image-organizer-frontend';

  constructor(private processService: ProcessService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.processService.findProcessState().subscribe((state) => {
      if (! state.started) {
        this.router.navigateByUrl("/");
      }
    });
  }
}

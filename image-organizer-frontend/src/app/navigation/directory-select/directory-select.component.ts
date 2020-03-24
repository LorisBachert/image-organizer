import {Component, OnInit} from '@angular/core';
import {ProcessService} from '../shared/service/process.service';
import {ActivatedRoute, Router, RouterEvent} from '@angular/router';

@Component({
  selector: 'app-directory-select',
  templateUrl: './directory-select.component.html',
  styleUrls: ['./directory-select.component.scss']
})
export class DirectorySelectComponent implements OnInit {

  directory = 'D:\\Bilder';

  started: boolean;

  constructor(private processService: ProcessService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.processService.findProcessState()
      .subscribe(processState => {
        this.started = processState.started;
        if (this.started) {
          this.directory = processState.directory;
        }
      })
  }

  startProcess() {
    this.started = true;
    this.processService.startProcess(this.directory)
      .subscribe(() => {
        this.router.navigateByUrl('/gallery');
      });
  }
}

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

  processService: ProcessService;

  constructor(processService: ProcessService) {
    this.processService = processService;
  }

  ngOnInit(): void {
    this.processService.findProcessState()
      .subscribe(processState => {
        const started = processState.started;
        if (started) {
          this.directory = processState.directory;
        }
      })
  }

  startProcess() {
    this.processService.startProcess(this.directory)
      .subscribe(() => {
        console.log("Process started")
      });
  }
}

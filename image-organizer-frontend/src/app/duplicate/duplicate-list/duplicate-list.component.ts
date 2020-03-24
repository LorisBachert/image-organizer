import { Component, OnInit } from '@angular/core';
import {DuplicateService} from '../shared/service/duplicate.service';
import {Duplicate} from '../../shared/model/duplicate.model';

@Component({
  selector: 'app-duplicate-list',
  templateUrl: './duplicate-list.component.html',
  styleUrls: ['./duplicate-list.component.scss']
})
export class DuplicateListComponent implements OnInit {

  duplicates: Duplicate[];

  constructor(private duplicateService: DuplicateService) { }

  ngOnInit(): void {
    this.duplicateService.findDuplicates()
      .subscribe(duplicates => {
        this.duplicates = duplicates;
      })
  }

}

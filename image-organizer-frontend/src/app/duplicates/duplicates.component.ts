import {Component, OnInit} from '@angular/core';
import {ImageService} from '../directory-select/shared/service/image.service';
import {Duplicate} from '../shared/model/duplicate.model';
import {FileMetadata} from '../shared/model/file-metadata.model';
import {BehaviorSubject} from 'rxjs';
import {StepperSelectionEvent} from '@angular/cdk/stepper';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-duplicates',
  templateUrl: './duplicates.component.html',
  styleUrls: ['./duplicates.component.scss']
})
export class DuplicatesComponent implements OnInit {

  done = false;

  duplicate: Duplicate;

  index = 0;

  constructor(private imageService: ImageService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.index = +this.activatedRoute.snapshot.queryParamMap.get('duplicate') || 0;
    this.imageService.findNextDuplicate(this.index)
      .subscribe(nextDuplicate => {
        this.duplicate = nextDuplicate;
        if (! nextDuplicate) {
          this.done = true;
        }
      });
  }

  markOthersAsDeleted(duplicate: Duplicate, image: FileMetadata) {
    duplicate.files.forEach(file => file.toDelete = true);
    image.toDelete = false;
  }

  markAll(duplicate: Duplicate, toDelete: boolean) {
    duplicate.files.forEach(file => file.toDelete = toDelete);
  }

  resolve(duplicate: Duplicate) {
    this.duplicate = null;
    this.imageService.resolveDuplicate(this.index, duplicate)
      .subscribe(nextDuplicate => {
        this.next();
        this.duplicate = nextDuplicate;
        if (! nextDuplicate) {
          this.done = true;
        }
      });
  }

  next() {
    this.index++;
    this.updateQueryParams();
  }

  back() {
    this.index--;
    this.updateQueryParams();
  }

  updateQueryParams() {
    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams: {
        duplicate: this.index
      },
      queryParamsHandling: 'merge'
    });
  }
}

import {Component, ElementRef, HostListener, OnInit} from '@angular/core';
import {DuplicateService} from '../shared/service/duplicate.service';
import {Duplicate} from '../../shared/model/duplicate.model';
import {MatButton} from '@angular/material/button';
import {MatExpansionPanel} from '@angular/material/expansion';
import {ImageService} from '../../core/image/image.service';
import {Router} from '@angular/router';
import {Gallery} from '../../galleries/shared/model/gallery.model';

@Component({
  selector: 'app-duplicate-list',
  templateUrl: './duplicate-list.component.html',
  styleUrls: ['./duplicate-list.component.scss']
})
export class DuplicateListComponent implements OnInit {

  selectedIndex = 0;

  showResolved = true;

  duplicates: Duplicate[] = [];

  duplicateService: DuplicateService;

  constructor(duplicateService: DuplicateService,
              private imageService: ImageService,
              private router: Router) {
    this.duplicateService = duplicateService; }

  ngOnInit(): void {
    this.duplicateService.duplicates$
      .subscribe(duplicates => {
        if (duplicates.length > 0) {
          duplicates.forEach(duplicate => {
            const isNew = this.duplicates.find(existingDuplicate => existingDuplicate.id === duplicate.id) === undefined;
            if (isNew) {
              this.duplicates.push(duplicate);
            }
          });
        } else {
          this.duplicates = [];
        }
      })
  }

  resolveDuplicate(duplicate: Duplicate) {
    this.duplicateService.resolveDuplicate(duplicate)
      .subscribe(() => {
        duplicate.resolved = true;
        if (this.showResolved) {
          this.selectedIndex++;
        }
        if (this.duplicates.every(d => d.resolved) && this.selectedIndex >= this.duplicates.length) {
          this.router.navigateByUrl('/galleries');
        }
      });
  }

  findDuplicatesToDisplay(): Duplicate[] {
    return this.duplicates.filter(duplicate => !duplicate.resolved || this.showResolved);
  }

  resolvedDuplicates() {
    return this.duplicates.filter(d => d.resolved).length;
  }

  toGallery(duplicate: Duplicate): Gallery {
    return {
      name: 'Duplicate',
      files: duplicate.files
    } as Gallery;
  }

  next() {
    this.resolveDuplicate(this.duplicates[this.selectedIndex]);
  }

  previous() {
    this.selectedIndex = Math.max(0, this.selectedIndex - 1);
  }
}

import {Component, ElementRef, HostListener, OnInit} from '@angular/core';
import {DuplicateService} from '../shared/service/duplicate.service';
import {Duplicate} from '../../shared/model/duplicate.model';
import {MatButton} from '@angular/material/button';
import {MatExpansionPanel} from '@angular/material/expansion';

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

  constructor(duplicateService: DuplicateService) {
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
      });
  }

  markAllForDeletion(duplicate: Duplicate, toDelete: boolean) {
    duplicate.files.forEach(file => file.toDelete = toDelete);
  }

  keptFiles(duplicate: Duplicate) {
    return duplicate.files.filter(f => !f.toDelete).length;
  }

  findDuplicatesToDisplay(): Duplicate[] {
    return this.duplicates.filter(duplicate => !duplicate.resolved || this.showResolved);
  }

  @HostListener('window:keyup', ['$event'])
  keyUp($event: KeyboardEvent) {
    let isNumberKey = ! isNaN(+$event.key);
    const duplicate = this.duplicates[this.selectedIndex];
    if (isNumberKey) {
      const index = +$event.key - 1;
      const file = duplicate.files[index];
      if (file) {
        file.toDelete = !file.toDelete;
      }
    } else if ($event.key === 'ArrowLeft') {
      this.selectedIndex = Math.max(-1, this.selectedIndex - 1);
    } else if ($event.key === 'ArrowRight') {
      this.selectedIndex = Math.min(this.findDuplicatesToDisplay().length, this.selectedIndex + 1);
    } else if ($event.key.toLowerCase() === 'a') {
      this.markAllForDeletion(duplicate, false);
    } else if ($event.key.toLowerCase() === 'd') {
      this.markAllForDeletion(duplicate, true);
    }
  }

  focus(resolve: MatButton) {
    // needs a little timeout to work properly
    setTimeout(() => resolve.focus(), 50);
  }

  resolvedDuplicates() {
    return this.duplicates.filter(d => d.resolved).length;
  }

  opened(resolve: MatButton, index: number) {
    this.selectedIndex = index;
    // needs a little timeout to work properly
    setTimeout(() => {
      resolve.focus();
      const panelRef = document.getElementById('panel-' + index);
      panelRef.scrollIntoView({ behavior: 'smooth' });
    }, 50);
  }
}

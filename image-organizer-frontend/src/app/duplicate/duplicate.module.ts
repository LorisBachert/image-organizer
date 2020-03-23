import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DuplicateDetailsComponent } from './duplicate-details/duplicate-details.component';
import { DuplicateListComponent } from './duplicate-list/duplicate-list.component';



@NgModule({
  declarations: [DuplicateDetailsComponent, DuplicateListComponent],
  imports: [
    CommonModule
  ]
})
export class DuplicateModule { }

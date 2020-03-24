import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DuplicateDetailsComponent } from './duplicate-details/duplicate-details.component';
import { DuplicateListComponent } from './duplicate-list/duplicate-list.component';
import {SharedModule} from '../shared/shared.module';



@NgModule({
  declarations: [DuplicateDetailsComponent, DuplicateListComponent],
  exports: [DuplicateDetailsComponent, DuplicateListComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class DuplicateModule { }

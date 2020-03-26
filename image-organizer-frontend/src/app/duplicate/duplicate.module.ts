import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DuplicateDetailsComponent } from './duplicate-details/duplicate-details.component';
import { DuplicateListComponent } from './duplicate-list/duplicate-list.component';
import {SharedModule} from '../shared/shared.module';
import {MatButtonModule} from '@angular/material/button';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatIconModule} from '@angular/material/icon';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {FormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';



@NgModule({
  declarations: [DuplicateDetailsComponent, DuplicateListComponent],
  exports: [DuplicateDetailsComponent, DuplicateListComponent],
  imports: [
    CommonModule,
    SharedModule,
    MatButtonModule,
    MatExpansionModule,
    MatIconModule,
    MatSlideToggleModule,
    FormsModule,
    MatCardModule,
    MatProgressSpinnerModule
  ]
})
export class DuplicateModule { }

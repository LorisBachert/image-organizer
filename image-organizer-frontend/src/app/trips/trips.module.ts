import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TripsComponent } from './trips/trips.component';
import {SharedModule} from '../shared/shared.module';
import {MatCardModule} from '@angular/material/card';
import {MatExpansionModule} from '@angular/material/expansion';
import {DragDropModule} from '@angular/cdk/drag-drop';



@NgModule({
  declarations: [TripsComponent],
  imports: [
    CommonModule,
    SharedModule,
    MatCardModule,
    MatExpansionModule,
    DragDropModule
  ]
})
export class TripsModule { }
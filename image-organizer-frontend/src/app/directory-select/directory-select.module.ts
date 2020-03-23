import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DirectorySelectComponent} from './directory-select/directory-select.component';
import {SharedModule} from '../shared/shared.module';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';



@NgModule({
  declarations: [DirectorySelectComponent],
  exports: [
    DirectorySelectComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule
  ]
})
export class DirectorySelectModule { }

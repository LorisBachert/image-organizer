import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DirectorySelectComponent} from './directory-select/directory-select.component';
import {SharedModule} from '../shared/shared.module';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import { NavigationComponent } from './navigation/navigation.component';
import {RouterModule} from '@angular/router';



@NgModule({
  declarations: [DirectorySelectComponent, NavigationComponent],
  exports: [
    DirectorySelectComponent,
    NavigationComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    RouterModule
  ]
})
export class NavigationModule { }

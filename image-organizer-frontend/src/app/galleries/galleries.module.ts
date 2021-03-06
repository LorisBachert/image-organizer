import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../shared/shared.module';
import {MatCardModule} from '@angular/material/card';
import {MatExpansionModule} from '@angular/material/expansion';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {FormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import {GalleryNameFormComponent} from './gallery-name-form/gallery-name-form.component';
import {GalleriesComponent} from './galleries/galleries.component';
import { GalleryComponent } from './gallery/gallery.component';
import {MatIconModule} from '@angular/material/icon';
import { GalleryMenuComponent } from './shared/component/gallery-menu/gallery-menu.component';


@NgModule({
  declarations: [GalleriesComponent, GalleryNameFormComponent, GalleryComponent, GalleryMenuComponent],
  exports: [
    GalleryComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    MatCardModule,
    MatExpansionModule,
    DragDropModule,
    MatSlideToggleModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule
  ]
})
export class GalleriesModule {
}

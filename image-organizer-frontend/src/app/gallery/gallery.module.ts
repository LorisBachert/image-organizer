import {NgModule} from '@angular/core';
import {GalleryComponent} from './gallery/gallery.component';
import {SharedModule} from '../shared/shared.module';
import {CommonModule} from '@angular/common';


@NgModule({
  declarations: [GalleryComponent],
  exports: [GalleryComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class GalleryModule {
}

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CenteredContainerComponent} from './components/centered-container/centered-container.component';
import {SpinnerComponent} from './components/spinner/spinner.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {HttpClientModule} from '@angular/common/http';
import { ImageComponent } from './components/image/image.component';
import { ImageGridComponent } from './components/image-grid/image-grid.component';
import {DragDropModule} from '@angular/cdk/drag-drop';


@NgModule({
  declarations: [CenteredContainerComponent, SpinnerComponent, ImageComponent, ImageGridComponent],
  exports: [
    CenteredContainerComponent, SpinnerComponent, ImageComponent, ImageGridComponent
  ],
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    HttpClientModule,
    DragDropModule
  ]
})
export class SharedModule {
}

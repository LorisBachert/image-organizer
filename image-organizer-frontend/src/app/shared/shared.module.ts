import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CenteredContainerComponent} from './components/centered-container/centered-container.component';
import {SpinnerComponent} from './components/spinner/spinner.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {HttpClientModule} from '@angular/common/http';


@NgModule({
  declarations: [CenteredContainerComponent, SpinnerComponent],
  exports: [
    CenteredContainerComponent, SpinnerComponent
  ],
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    HttpClientModule
  ]
})
export class SharedModule {
}

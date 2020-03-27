import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DuplicateModule} from './duplicate/duplicate.module';
import {CoreModule} from './core/core.module';
import {NavigationModule} from './navigation/navigation.module';
import {HomeModule} from './home/home.module';
import {GalleriesModule} from './galleries/galleries.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NavigationModule,
    DuplicateModule,
    CoreModule,
    NavigationModule,
    GalleriesModule,
    HomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

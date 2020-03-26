import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DuplicateModule} from './duplicate/duplicate.module';
import {CoreModule} from './core/core.module';
import {GalleryModule} from './gallery/gallery.module';
import {NavigationModule} from './navigation/navigation.module';
import {TripsModule} from './trips/trips.module';
import {MatMenuModule} from '@angular/material/menu';

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
    GalleryModule,
    NavigationModule,
    TripsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

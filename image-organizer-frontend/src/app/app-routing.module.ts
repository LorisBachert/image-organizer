import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {DuplicateListComponent} from './duplicate/duplicate-list/duplicate-list.component';
import {GalleryComponent} from './gallery/gallery/gallery.component';


const routes: Routes = [
  {path: '', component: AppComponent},
  {path: 'gallery', component: GalleryComponent},
  {path: 'duplicates', component: DuplicateListComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

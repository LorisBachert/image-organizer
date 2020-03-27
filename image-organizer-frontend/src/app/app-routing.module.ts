import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DuplicateListComponent} from './duplicate/duplicate-list/duplicate-list.component';
import {HomeComponent} from './home/home/home.component';
import {GalleriesComponent} from './galleries/galleries/galleries.component';


const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'duplicates', component: DuplicateListComponent},
  {path: 'galleries', component: GalleriesComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

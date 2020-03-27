import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DuplicateListComponent} from './duplicate/duplicate-list/duplicate-list.component';
import {TripsComponent} from './trips/trips/trips.component';
import {HomeComponent} from './home/home/home.component';


const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'duplicates', component: DuplicateListComponent},
  {path: 'trips', component: TripsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

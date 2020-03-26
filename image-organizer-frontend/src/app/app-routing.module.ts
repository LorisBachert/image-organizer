import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DuplicateListComponent} from './duplicate/duplicate-list/duplicate-list.component';
import {TripsComponent} from './trips/trips/trips.component';


const routes: Routes = [
  {path: 'duplicates', component: DuplicateListComponent},
  {path: 'trips', component: TripsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

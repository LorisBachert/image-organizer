import {Injectable} from '@angular/core';
import {BaseService} from '../../../shared/service/base.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Gallery} from '../model/gallery.model';

@Injectable({
  providedIn: 'root'
})
export class GalleriesService extends BaseService {

  public readonly trips$ = new BehaviorSubject<Gallery[]>([]);

  public readonly done$ = new BehaviorSubject<Boolean>(true);

  constructor(private http: HttpClient) {
    super();
  }

  start() {
    this.done$.next(false);
    this.trips$.next([]);
    this.pollTripsUntilDone();
  }

  pollTripsUntilDone() {
    const loadData = () => this.http.get<Gallery[]>('/trips');
    const loadDone = () => this.http.get<Boolean>('/trips/done');
    this.pollUntilDone<Gallery[]>(loadData, loadDone)
      .subscribe(trips => {
        this.trips$.next(trips);
      }, () => {
      }, () => this.done$.next(true));
  }

  update(trip: Gallery): Observable<void> {
    return this.http.put<void>(`/trips/${trip.id}`, trip);
  }
}

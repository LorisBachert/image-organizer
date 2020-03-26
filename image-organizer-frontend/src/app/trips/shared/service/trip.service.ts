import {Injectable} from '@angular/core';
import {BaseService} from '../../../shared/service/base.service';
import {BehaviorSubject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Trip} from '../model/trip.model';

@Injectable({
  providedIn: 'root'
})
export class TripService extends BaseService {

  public readonly trips$ = new BehaviorSubject<Trip[]>([]);

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
    const loadData = () => this.http.get<Trip[]>('/trips');
    const loadDone = () => this.http.get<Boolean>('/trips/done');
    this.pollUntilDone<Trip[]>(loadData, loadDone)
      .subscribe(trips => {
        this.trips$.next(trips);
      }, () => {
      }, () => this.done$.next(true));
  }
}

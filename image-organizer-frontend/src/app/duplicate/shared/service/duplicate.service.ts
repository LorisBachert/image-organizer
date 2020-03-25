import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Duplicate} from '../../../shared/model/duplicate.model';
import {HttpClient} from '@angular/common/http';
import {BaseService} from '../../../shared/service/base.service';

@Injectable({
  providedIn: 'root'
})
export class DuplicateService extends BaseService {

  public readonly duplicates$ = new BehaviorSubject<Duplicate[]>([]);

  public readonly done$ = new BehaviorSubject<Boolean>(false);

  constructor(private http: HttpClient) {
    super();
  }

  start() {
    this.done$.next(false);
    this.duplicates$.next([]);
    this.pollDuplicatesUntilDone();
  }

  pollDuplicatesUntilDone() {
    const loadData = () => this.http.get<Duplicate[]>('/duplicates');
    const loadDone = () => this.http.get<Boolean>('/duplicates/done');
    this.pollUntilDone<Duplicate[]>(loadData, loadDone)
      .subscribe(duplicates => {
        this.duplicates$.next(duplicates);
      }, () => {}, () => this.done$.next(true));
  }
}

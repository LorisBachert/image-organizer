import {BehaviorSubject, interval, merge, Observable} from 'rxjs';
import {Duplicate} from '../model/duplicate.model';
import {filter, mergeMap, take, takeWhile, tap} from 'rxjs/operators';

export abstract class BaseService {

  protected pollUntilDone <T> (loadData: () => Observable<T>, loadDone: () => Observable<Boolean>): Observable<T> {
    const done$ = new BehaviorSubject<Boolean>(false);
    const poll = interval(1000)
      .pipe(
        tap(() => {
          loadDone().subscribe(done => done$.next(done))
        }),
        mergeMap(loadData),
        takeWhile(() => ! done$.getValue())
      );
    const loadFinally = done$
      .pipe(
        filter(done => !!done),
        tap(() => console.log('done')),
        mergeMap(loadData),
        take(1)
      );
    return merge(poll, loadFinally);
  }
}

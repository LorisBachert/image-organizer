import {Injectable} from '@angular/core';
import {BaseService} from '../../../shared/service/base.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Gallery} from '../model/gallery.model';
import {map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GalleriesService extends BaseService {

  public readonly galleries$ = new BehaviorSubject<Gallery[]>([]);

  public readonly done$ = new BehaviorSubject<Boolean>(true);

  constructor(private http: HttpClient) {
    super();
  }

  start() {
    this.done$.next(false);
    this.galleries$.next([]);
    this.pollGalleriesUntilDone();
  }

  pollGalleriesUntilDone() {
    const loadData = () => this.http.get<Gallery[]>('/galleries');
    const loadDone = () => this.http.get<Boolean>('/galleries/done');
    this.pollUntilDone<Gallery[]>(loadData, loadDone)
      .subscribe(galleries => {
        this.galleries$.next(galleries);
      }, () => {
      }, () => this.done$.next(true));
  }

  update(gallery: Gallery): Observable<void> {
    return this.http.put<void>(`/galleries/${gallery.id}`, gallery);
  }

  create(newGalleryName: string): Observable<Gallery> {
    const gallery: Gallery = {
      name: newGalleryName,
      files: []
    } as Gallery;
    return this.http.post<Gallery>('/galleries', gallery)
      .pipe(
        tap(gallery => {
          const galleries = this.galleries$.getValue();
          galleries.push(gallery);
          this.galleries$.next(galleries);
        })
      );
  }
}

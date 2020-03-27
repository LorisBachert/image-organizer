import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {StartProcessResult} from '../model/start-process-result.model';
import {ProcessState} from '../model/process-state.model';
import {DuplicateService} from '../../duplicate/shared/service/duplicate.service';
import {tap} from 'rxjs/operators';
import {ImageService} from '../image/image.service';
import {Configuration} from '../../home/shared/model/configuration.model';
import {GalleriesService} from '../../galleries/shared/service/galleries.service';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  public readonly started$ = new BehaviorSubject<Boolean>(false);

  constructor(private http: HttpClient,
              private duplicateService: DuplicateService,
              private galleriesService: GalleriesService,
              private imageService: ImageService) {
  }

  public startProcess(config: Configuration): Observable<StartProcessResult> {
    return this.http.post<StartProcessResult>('/process/start', config).pipe(
      tap(() => {
        this.initialize();
      })
    );
  }

  public findProcessState(): Observable<ProcessState> {
    return this.http.get<ProcessState>('/process/state')
      .pipe(
        tap(processState => {
          if (processState.started) {
            this.initialize();
          }
        })
      );
  }

  private initialize() {
    this.started$.next(true);
    this.duplicateService.start();
    this.galleriesService.start();
    this.imageService.start();
  }

  end(): Observable<void> {
    return this.http.post<void>('/process/end', null)
      .pipe(
        tap(() => this.started$.next(false))
      );
  }
}

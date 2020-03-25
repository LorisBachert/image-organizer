import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Duplicate} from '../../../shared/model/duplicate.model';
import {FileMetadata} from '../../../shared/model/file-metadata.model';
import {CrawlFileResult} from '../../../shared/model/crawl-file-result.model';
import {StartProcessResult} from '../model/start-process-result.model';
import {ProcessState} from '../model/process-state.model';
import {DuplicateService} from '../../../duplicate/shared/service/duplicate.service';
import {tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  public readonly started$ = new BehaviorSubject<Boolean>(false);

  constructor(private http: HttpClient,
              private duplicateService: DuplicateService) {
  }

  public startProcess(directory: string): Observable<StartProcessResult> {
    return this.http.post<StartProcessResult>('/process/start', null, {
      params: {
        directory
      }
    }).pipe(
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
  }
}

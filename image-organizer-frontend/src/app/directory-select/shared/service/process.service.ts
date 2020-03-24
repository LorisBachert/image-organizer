import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Duplicate} from '../../../shared/model/duplicate.model';
import {FileMetadata} from '../../../shared/model/file-metadata.model';
import {CrawlFileResult} from '../../../shared/model/crawl-file-result.model';
import {StartProcessResult} from '../model/start-process-result.model';
import {ProcessState} from '../model/process-state.model';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  constructor(private http: HttpClient) {
  }

  public startProcess(directory: string): Observable<StartProcessResult> {
    return this.http.post<StartProcessResult>('/process/start', null, {
      params: {
        directory
      }
    });
  }

  public findProcessState(): Observable<ProcessState> {
    return this.http.get<ProcessState>('/process/state');
  }
}

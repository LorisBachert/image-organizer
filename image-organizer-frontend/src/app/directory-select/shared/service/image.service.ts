import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Duplicate} from '../../../shared/model/duplicate.model';
import {FileMetadata} from '../../../shared/model/file-metadata.model';
import {CrawlFileResult} from '../../../shared/model/crawl-file-result.model';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) {
  }

  public findFiles(path: string): Observable<CrawlFileResult> {
    return this.http.get<CrawlFileResult>('/images', {
      params: {
        path
      }
    });
  }

  public findNextDuplicate(index: number): Observable<Duplicate> {
    return this.http.get<Duplicate>('/images/duplicates/' + index);
  }

  public resolveDuplicate(index: number, duplicate: Duplicate) {
    return this.http.post<Duplicate>(`/images/duplicates/${index}/resolve`, duplicate);
  }
}

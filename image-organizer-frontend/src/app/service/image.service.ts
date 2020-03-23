import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CrawlFileResult} from '../model/crawl-file-result.model';
import {Observable} from 'rxjs';
import {Duplicates} from '../model/duplicates.model';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) { }

  public findFiles(path: string): Observable<CrawlFileResult> {
    return this.http.get<CrawlFileResult>('/images', {
      params: {
        path
      }
    });
  }

  public findDuplicates(): Observable<Duplicates[]> {
    return this.http.get<Duplicates[]>('/images/duplicates');
  }

}

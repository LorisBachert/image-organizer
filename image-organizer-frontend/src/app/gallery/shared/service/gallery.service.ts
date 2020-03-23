import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {FileMetadata} from '../../../shared/model/file-metadata.model';

@Injectable({
  providedIn: 'root'
})
export class GalleryService {

  constructor(private http: HttpClient) { }

  findGallery(page: number): Observable<FileMetadata[]> {
    return this.http.get<FileMetadata[]>('/images/gallery', {
      params: {
        page: String(page)
      }
    });
  }
}

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Duplicate} from '../../../shared/model/duplicate.model';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DuplicateService {

  constructor(private http: HttpClient) {
  }

  findDuplicates(): Observable<Duplicate[]> {
    return this.http.get<Duplicate[]>('/duplicates');
  }
}

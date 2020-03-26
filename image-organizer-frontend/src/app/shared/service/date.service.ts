import { Injectable } from '@angular/core';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class DateService {

  constructor() { }

  format(date: string | Date): string {
    if (! date)  {
      return '';
    }
    return moment(date).format('YYYY.MM.DD');
  }
}

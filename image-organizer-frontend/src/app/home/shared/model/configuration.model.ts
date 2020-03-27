import {TripConfiguration} from './trip-configuration.model';
import {DuplicateConfiguration} from './duplicate-configuration.model';

export class Configuration {
  directory: string;
  duplicates?: DuplicateConfiguration;
  trips?: TripConfiguration;
}

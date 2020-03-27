import {DuplicateConfiguration} from './duplicate-configuration.model';
import {GalleryConfiguration} from './gallery-configuration.model';

export class Configuration {
  directory: string;
  duplicates?: DuplicateConfiguration;
  galleries?: GalleryConfiguration;
}

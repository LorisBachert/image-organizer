import {FileMetadata} from '../../../shared/model/file-metadata.model';

export class Trip {
  id: number;
  name: string;
  files: FileMetadata[];
  startDate: Date;
  endDate: Date;
  city: string;
  country: string;
}

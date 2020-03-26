import {FileMetadata} from './file-metadata.model';

export class Duplicate {
  id: number;
  files: FileMetadata[];
  resolved: boolean;
}

import {Injectable} from '@angular/core';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import {BehaviorSubject} from 'rxjs';
import {BaseService} from '../../shared/service/base.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ImageService extends BaseService {

  public readonly done$ = new BehaviorSubject<Boolean>(true);

  public images: { [id: number]: BehaviorSubject<FileMetadata> } = {};

  constructor(private http: HttpClient) {
    super();
  }

  start() {
    this.done$.next(false);
    this.images = {};
    this.pollImagesUntilDone();
  }

  pollImagesUntilDone() {
    const loadData = () => this.http.get<FileMetadata[]>('/images');
    const loadDone = () => this.http.get<Boolean>('/images/done');
    this.pollUntilDone<FileMetadata[]>(loadData, loadDone)
      .subscribe(images => {
        images.forEach(image => this.addImage(image));
      }, () => {
      }, () => this.done$.next(true));
  }

  public addImage(image: FileMetadata) {
    if (!this.images.hasOwnProperty(image.id)) {
      this.images[image.id] = new BehaviorSubject<FileMetadata>(image);
    } else {
      this.images[image.id].next(image);
    }
  }

  public markForDeletion(id: number, toDelete: boolean) {
    this.pushDeletion(id, toDelete);
  }

  public toggleDeletion(id: number) {
    const image = this.images[id].getValue();
    this.pushDeletion(id, !image.toDelete);
  }

  private pushDeletion(id: number, toDelete: boolean) {
    this.http.post<void>(`/images/${id}/delete`, null, {
      params: {
        toDelete: String(toDelete)
      }
    }).subscribe(() => {
      const image = this.images[id].getValue();
      image.toDelete = toDelete;
      this.images[id].next(image);
    })
  }
}

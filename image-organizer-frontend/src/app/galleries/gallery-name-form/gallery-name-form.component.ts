import {Component, Input} from '@angular/core';
import {NgForm} from '@angular/forms';
import {GalleriesService} from '../shared/service/galleries.service';
import {Gallery} from '../shared/model/gallery.model';

@Component({
  selector: 'app-gallery-name-form',
  templateUrl: './gallery-name-form.component.html',
  styleUrls: ['./gallery-name-form.component.scss']
})
export class GalleryNameFormComponent {

  @Input() gallery: Gallery;

  constructor(private galleriesService: GalleriesService) {
  }

  update(gallery: Gallery, form: NgForm, $event: any) {
    $event.preventDefault();
    this.galleriesService.update(gallery)
      .subscribe(() => {
        form.form.markAsPristine();
      });
  }

}

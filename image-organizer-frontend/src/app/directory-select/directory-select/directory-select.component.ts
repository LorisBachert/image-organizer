import {Component} from '@angular/core';
import {CrawlFileResult} from '../../shared/model/crawl-file-result.model';
import {Observable} from 'rxjs';
import {ImageService} from '../shared/service/image.service';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-directory-select',
  templateUrl: './directory-select.component.html',
  styleUrls: ['./directory-select.component.scss']
})
export class DirectorySelectComponent {

  path = 'C:\\Users\\loris\\Desktop\\Bilder';

  done: boolean;

  constructor(private imageService: ImageService,
              private router: Router) {
  }

  crawlFiles() {
    this.imageService.findFiles(this.path)
      .subscribe(() => {
        this.done = true;
        this.router.navigateByUrl('/duplicates');
      });
  }
}

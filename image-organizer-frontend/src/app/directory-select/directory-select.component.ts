import {Component} from '@angular/core';
import {CrawlFileResult} from '../model/crawl-file-result.model';
import {Observable} from 'rxjs';
import {ImageService} from '../service/image.service';

@Component({
  selector: 'app-directory-select',
  templateUrl: './directory-select.component.html',
  styleUrls: ['./directory-select.component.scss']
})
export class DirectorySelectComponent {

  path = 'C:\\Users\\loris\\Desktop\\Bilder';

  result: CrawlFileResult;

  constructor(private imageService: ImageService) {
  }

  crawlFiles() {
    this.imageService.findFiles(this.path)
      .subscribe(result => this.result = result);
  }

  reset() {
    this.result = null;
  }
}

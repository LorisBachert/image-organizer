import {Component, OnInit} from '@angular/core';
import {CrawlFileResult} from '../../shared/model/crawl-file-result.model';
import {Observable} from 'rxjs';
import {ImageService} from '../shared/service/image.service';
import {FileMetadata} from '../../shared/model/file-metadata.model';
import {ActivatedRoute, Router, RouterEvent} from '@angular/router';

@Component({
  selector: 'app-directory-select',
  templateUrl: './directory-select.component.html',
  styleUrls: ['./directory-select.component.scss']
})
export class DirectorySelectComponent implements OnInit {

  path = 'D:\\Bilder';

  done: boolean;

  isHome: boolean;

  constructor(private imageService: ImageService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.isHome = this.activatedRoute.snapshot.url.length === 0;
    this.router.events.subscribe((routerEvent: RouterEvent) => {
      this.isHome = routerEvent.url === '';
    })
  }

  crawlFiles() {
    this.imageService.findFiles(this.path)
      .subscribe(() => {
        this.done = true;
        this.router.navigateByUrl('/gallery');
      });
  }
}

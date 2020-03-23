import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-centered-container',
  templateUrl: './centered-container.component.html',
  styleUrls: ['./centered-container.component.scss']
})
export class CenteredContainerComponent {

  @Input() vertical = true;

  @Input() horizontal = true;

  constructor() {
  }

}

import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-display-star-rating',
  templateUrl: './display-star-rating.component.html',
  styleUrls: ['./display-star-rating.component.sass']
})
export class DisplayStarRatingComponent {
  @Input() filledStars: number = 0;
  @Input() unfilledStars: number = 0;
  constructor() {}
}

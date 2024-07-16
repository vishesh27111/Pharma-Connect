import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-add-star-rating',
  templateUrl: './add-star-rating.component.html',
  styleUrls: ['./add-star-rating.component.sass']
})
export class AddStarRatingComponent implements OnInit {
  @Input() filledStars: number;
  @Input() unfilledStars: number;
  @Output() rate = new EventEmitter<any>();
  filledStarsArray: number[] = [];
  unfilledStarsArray: number[] = [];

  constructor() {}

  ngOnInit() {
    this.fillStarsArray();
  }

  selected(i: number) {
    this.filledStars = i;
    this.unfilledStars = 5-this.filledStars;

    this.fillStarsArray();

    this.rate.emit(i);
  }

  fillStarsArray() {
    this.filledStarsArray = [];
    this.unfilledStarsArray = [];
    for (let i = 0; i < this.filledStars; i++) {
      this.filledStarsArray.push(i+1);
    }
    console.log(this.filledStarsArray);
    for (let i = this.filledStars; i < 5; i++) {
      this.unfilledStarsArray.push(i+1);
    }
    console.log(this.unfilledStarsArray);
  }
}

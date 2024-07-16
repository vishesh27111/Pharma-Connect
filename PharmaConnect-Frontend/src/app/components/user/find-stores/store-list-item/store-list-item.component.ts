import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-store-list-item',
  templateUrl: './store-list-item.component.html',
  styleUrls: ['./store-list-item.component.sass']
})
export class StoreListItemComponent implements OnInit {
  @Input() store: any;
  constructor() {}

  ngOnInit(): void {
      
  }
}

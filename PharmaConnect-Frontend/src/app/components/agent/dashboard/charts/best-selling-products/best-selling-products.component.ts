import { Component } from '@angular/core';
import { ChartConfiguration } from 'chart.js';

@Component({
  selector: 'app-best-selling-products',
  templateUrl: './best-selling-products.component.html',
  styleUrls: ['./best-selling-products.component.sass']
})
export class BestSellingProductsComponent {
  public barChartLegend = true;
  public barChartPlugins = [];

  public barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [ 'Vitamins & Herbal Supplements', 'Seasonal Items', 'Durable Medical Equipment', 'Essential Oils', 'CBD Oils' ],
    datasets: [
      { data: [ 65, 59, 30, 81, 56 ], label: '2022' },
      { data: [ 45, 68, 40, 59, 86 ], label: '2023' }
    ]
  };

  public barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
  };

  constructor() { }
}

import { Component } from '@angular/core';
import { ChartOptions } from 'chart.js';

@Component({
  selector: 'app-customer-information',
  templateUrl: './customer-information.component.html',
  styleUrls: ['./customer-information.component.sass']
})
export class CustomerInformationComponent {
  // Pie
  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: false,
  };
  public pieChartLabels = [ [ 'New Customers' ], [ 'Old Customers' ] ];
  public pieChartDatasets = [ {
    data: [ 2021, 5840 ]
  } ];
  public pieChartLegend = true;
  public pieChartPlugins = [];

  constructor() { }
}

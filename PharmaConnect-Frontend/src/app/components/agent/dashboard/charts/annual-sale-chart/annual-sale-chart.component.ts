import { Component, Input } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';

@Component({
  selector: 'app-annual-sale-chart',
  templateUrl: './annual-sale-chart.component.html',
  styleUrls: ['./annual-sale-chart.component.sass']
})
export class AnnualSaleChartComponent {
  public lineChartOptions: ChartOptions = {
    responsive: true,
  };
  @Input() chartData: ChartData<'line'> | undefined;
  public lineChartLabels = [['January'], ['February'], ['March'], ['April'], ['May'], ['June'], ['July'], ['August'], ['September'], ['October'], ['November'], ['December']];
  public lineChartData = [ {
    data: [ 30000, 50000, 45000, 40000, 42000, 30000, 34000, 23000, 32000, 40000, 37000, 46000 ],
    label: 'Sales Data (2022)',
    fill: true,
    tension: 0.5,
    borderColor: 'black',
    backgroundColor: 'rgba(255,0,0,0.3)'
  }, {
    data: [ 40000, 42000, 30000, 34000, 23000, 32000, 40000, 37000, 46000 ],
    label: 'Sales Data (2023)',
    fill: true,
    tension: 0.5,
    borderColor: 'black',
    backgroundColor: 'rgba(0,255,0,0.3)'
  } ];
  public chartType = 'line';
  public lineChartLegend = true;
  public lineChartPlugins = [];
}

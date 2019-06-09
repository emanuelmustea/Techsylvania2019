import {Component, Input, AfterViewInit,OnChanges, ChangeDetectionStrategy} from '@angular/core';
import {Chart} from "chart.js";


export const colors =  ["26,188,156", "192,57,43", "243,156,18", "22,160,133", "46,204,113", "52,152,219", "52,73,94", "230,126,34", "44,62,80", "231,76,60", "241,196,15", "142,68,173", "39,174,96", "41,128,185", "211,84,0"]
@Component({
  selector: 'lbd-chart',
  templateUrl: './lbd-chart.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LbdChartComponent implements AfterViewInit, OnChanges {
  static needsRandomColor = ["pie"];
  static currentId = 1;
  public updatedAt:Date;
  myChart: any;
  colorIndex: number = -1;
  @Input()
  public title: string;

  @Input()
  public subtitle: string;

  @Input()
  public color: number;

  @Input()
  public type:string;

  @Input()
  public data: any[];

  @Input()
  public labels: any[];

  @Input()
  public label: string;

  @Input()
  public footerIconClass: string;

  @Input()
  public height: number;

  @Input()
  public footerText: string;

  public chartId: number = Math.floor(Math.random() * (10000000) );;

  constructor() {
  }
  randomColor = () => colors[this.color || Math.floor(Math.random() * 14 )];
  orderColor = () => {
    if(this.colorIndex > 15){
      this.colorIndex = 0;
    }
    else{
      this.colorIndex++;
    }
    return colors[this.color || this.colorIndex];
  }
  applyOpacity = (colors,opacity) => colors.map(color=> `rgba(${color}, ${opacity})`);
  generateColors = (labels:any[]) =>{
    const colors = labels.map(()=>this.orderColor());
    const backgroundColor = this.applyOpacity(colors,0.7);
    const borderColor = this.applyOpacity(colors,1);
    return {backgroundColor, borderColor};
  }
  syncColors = (labels, generatedColors) =>{
    const colors = generatedColors.borderColor;
    if(labels.length > colors.length){
        return this.generateColors(labels.slice(-1* (labels.length - colors.length)))
    }
    return generatedColors
  }
  public ngAfterViewInit(): void {
    const ctx = document.getElementById('chart' + this.chartId);
    const labels =  this.labels;
    this.myChart = new Chart(ctx, {
        type: this.type || "pie",
        data: {
            label:this.label,
            datasets: [{
                label: this.label,
                data: this.data,
                ...this.generateColors(labels),
                borderWidth: 1
            }]
        },
    });
  }

  public ngOnChanges(): void {
    if(!this.myChart || JSON.stringify(this.data) == JSON.stringify(this.myChart.data.datasets[0].data)) return;
    this.updatedAt = new Date();
    this.myChart.data.labels = this.labels;
    const sync = this.syncColors(this.labels, this.myChart.data.datasets[0]);
    this.myChart.data.datasets[0] = {
      ...sync,
      label: this.label,
      data: this.data,
      borderWidth: 1
  }
    this.myChart.update();
  }
}

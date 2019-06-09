
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import {TimeAgoPipe} from 'time-ago-pipe';

import { LbdChartComponent } from './lbd-chart/lbd-chart.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
  ],
  declarations: [
    LbdChartComponent,
    TimeAgoPipe
  ],
  exports: [
    LbdChartComponent
  ]
})
export class LbdModule { }

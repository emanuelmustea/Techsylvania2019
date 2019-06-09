import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MakePaymentComponent } from '../make-payment/make-payment.component';
import { FormsModule } from '@angular/forms';

@NgModule({
    declarations: [
    MakePaymentComponent
    ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports:[
    MakePaymentComponent
  ]
})
export class SharedModuleModule { }

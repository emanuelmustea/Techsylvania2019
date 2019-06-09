import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../Controllers/TransactionService';
import { Transaction } from '../Models/Transaction';

declare var $:any;

@Component({
  selector: 'app-make-payment',
  templateUrl: './make-payment.component.html',
  styleUrls: ['./make-payment.component.scss']
})
export class MakePaymentComponent implements OnInit {
  name:string;
  value:number;
  constructor(private transactionService:TransactionService) { }
  handlePaymentSubmit(){
    if(!this.name || !this.value) return;
    const {name, value} = this;
    const transaction = TransactionService.GetDefaultTransaction();
    if(this.transactionService.AddTransaction({...transaction,name, value, accountId: 1} as Transaction)){
      this.value = null;
      this.name = "";
      return $.notify({
        icon: "pe-7s-check",
        message: "Your transaction was successfully made"
    },{
        type: "success",
        timer: 1000,
        placement: {
            from: "top",
            align: "right"
        }
    });
    }
    return $.notify({
      icon: "pe-7s-close",
      message: "There was an error and we couldn't process your transaction"
  },{
      type: "danger",
      timer: 3000,
      placement: {
          from: "top",
          align: "right"
      }
  });
  }
  ngOnInit() {
  }

}

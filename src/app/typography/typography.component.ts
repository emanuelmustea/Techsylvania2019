import { Component, OnInit } from '@angular/core';
import { colors } from '../lbd/lbd-chart/lbd-chart.component';
import { TransactionService } from '../Controllers/TransactionService';

const vouchers = [
  {
    name:"Altex",
    until:'20 June 2019',
    category:"Tech",
    code:"RockFinance",
    percent: 5,
  },
  {
    name:"Bershka",
    until:'01 July 2019',
    category:"Fashion",
    code:"RockFinance15",
    percent: 15 ,
  },
  {
    name:"H&M",
    until:"01 July 2019",
    category:"Fashion",
    code:"RockFinanceHM",
    percent: 10 ,
  },
  {
    name:"Big Belly",
    until:'09 August 2019',
    category:"Food",
    code:"RockFinance30",
    percent: 30 ,
  },
];

@Component({
  selector: 'app-typography',
  templateUrl: './typography.component.html',
  styleUrls: ['./typography.component.css']
})
export class TypographyComponent implements OnInit {
  randomColor = () => `rgb(${colors[(Math.floor(Math.random() * 14 ))]})`;
  activeVouchers:any[];
  constructor(private transactionsService: TransactionService) { 

  }
  ngOnInit() {
    this.transactionsService.GetTransactions().subscribe((transactions:any[])=>{
      const transactionsName:string[] = transactions.map((transaction:any)=>transaction.categorie.name);
      this.activeVouchers = vouchers.filter(voucher=> transactionsName.includes(voucher.category) && (new Date(voucher.until)).getTime() > (new Date()).getTime() );
    })
  }

}

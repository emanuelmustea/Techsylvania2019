import { Component, OnInit } from '@angular/core';
import { LocationStrategy, PlatformLocation, Location } from '@angular/common';
import * as Chartist from 'chartist';
import { Categorie } from '../Models/Categorie';
import { CategorieService } from '../Controllers/CategorieService';
import { TransactionService } from '../Controllers/TransactionService';

export const monthNames = ["January", "February", "March", "April", "May", "June",
"July", "August", "September", "October", "November", "December"
];

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    public categoryData:any[];
    public categoryLabels:any[];
    public monthTransactionsData:any[];
    public monthTransactionsLabels:any[];
    public dayTransactionsData:any[];
    public dayTransactionsLabels:any[];
    constructor(private categoryService: CategorieService, private transactionsService: TransactionService) { }
    async ngOnInit() {
      this.categoryService.GetCategoryByValue().subscribe((values:any[])=>{
        this.categoryData = [];
        this.categoryLabels = [];
         values.map(value=>{
        if (value.value <= 0) return;
        this.categoryData.push(value.value);
        this.categoryLabels.push(value.name);
      })
    });
    this.transactionsService.GetTransactionspPerMonth().subscribe((values:any[])=>{
      this.monthTransactionsData = [];
      this.monthTransactionsLabels = [];
       values.map(value=>{
      this.monthTransactionsData.push(value.Value);
      this.monthTransactionsLabels.push(monthNames[value.Month -1]);
    })
  });
  this.transactionsService.GetTransactionspPerDay().subscribe((values:any[])=>{
    this.dayTransactionsData = [];
    this.dayTransactionsLabels = [];
     values.map(value=>{
    this.dayTransactionsData.push(value.Value);
    this.dayTransactionsLabels.push(value.Day);
  })
});
    }

}

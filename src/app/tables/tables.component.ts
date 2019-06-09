import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../Controllers/TransactionService';
import { CategorieService } from '../Controllers/CategorieService';
import { Categorie } from '../Models/Categorie';
import { monthNames } from '../home/home.component';

declare interface TableData {
    headerRow: string[];
    dataRows: string[][];
}

@Component({
  selector: 'app-tables',
  templateUrl: './tables.component.html',
  styleUrls: ['./tables.component.css']
})
export class TablesComponent implements OnInit {
    public categories: Categorie[] = [];
    public tableData1: TableData;
    public currentValues: any[] = [];

  constructor(private transactionService: TransactionService, private categoriesService: CategorieService) { }
    getCategory = (categorieId) => this.categories.find(category=>{
        // console.log(categorieId, category.categorieId);
        return category.categorieId == categorieId
    });
    ngOnInit() {
      this.categoriesService.GetCategories().subscribe((values:Categorie[])=>{
          this.categories = values;
      })
      this.transactionService.GetTransactions().subscribe((values:any[])=>{
          let i = 1;
          if(JSON.stringify(this.currentValues) == JSON.stringify(values)) return;
               this.tableData1.dataRows= [];
            this.currentValues = [...values];
            values.reverse().map(value=> {
            const categorie = value.categorie || {name: "-"};
            const today = new Date(value.creationTime);
            const date = `${today.getDate()} ${monthNames[today.getMonth()]} ${today.getFullYear()} ${today.getHours()}:${today.getMinutes()}`;

            this.tableData1.dataRows.push([i, date, value.transactionId, categorie.name, value.name, (value.value < 0) ? "INCOME" : "OUTCOME", (value.value < 0) ? value.value*(-1) : value.value , "USD"]);
            i++;
          });
      })
      this.tableData1 = {
          headerRow: [ 'NR', "Date", "Transaction ID",'Category', 'Merchant','Type', 'Amount', "Currency"],
          dataRows: []
      };

  }

}

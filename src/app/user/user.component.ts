import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../Controllers/TransactionService';
import { AccountService } from '../Controllers/AccountService';

const buildTopOutcomes = (outcomes:any[])=>{
  const outcomesList = {};
  outcomes.map(outcome => {
      if(!outcome.categorie) return;
      if(outcomesList[outcome.categorie.categorieId]){
        outcomesList[outcome.categorie.categorieId].value -= (-1) * outcome.value;
      }else{
        outcomesList[outcome.categorie.categorieId] = {value:1 *outcome.value, name:outcome.categorie.name};
      }
  } )
  const outcomesArray = Object.keys(outcomesList).map(key=> outcomesList[key]);
  return outcomesArray.sort((l,r)=> r.value - l.value).filter((a,i)=>i<5);
}
const buildTopCompanies = (outcomes:any[])=>{
  const outcomesList = {};
  outcomes.map(outcome => {
      if(outcomesList[outcome.name]){
        outcomesList[outcome.name].value -= (-1) * outcome.value;
      }else{
        outcomesList[outcome.name] = {value:1 *outcome.value, name:outcome.name};
      }
  } )
  const outcomesArray = Object.keys(outcomesList).map(key=> outcomesList[key]);
  return outcomesArray.sort((l,r)=> r.value - l.value).filter((a,i)=>i<5);
}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  public incomings:number = 0;
  public outcomes:number = 0;
  public topOutcomes:{}[];
  public topCompanies:{}[];
  public userData:{};
  constructor(private transactionService: TransactionService, private accountService: AccountService) { }
  ngOnInit() {
    this.transactionService.GetTransactions().subscribe((values:any[])=>{
      const incomings = values.filter(value => value.value < 0);
      this.incomings = 0;
      incomings.map(incoming=>this.incomings-=incoming.value);
      const outcomes = values.filter(value=>value.value > 0);
      this.outcomes = 0;
      outcomes.map(outcome=>this.outcomes-=outcome.value);
      this.outcomes *= -1;
      this.topOutcomes = buildTopOutcomes(outcomes);
      this.topCompanies = buildTopCompanies(outcomes);
    })
    this.userData = JSON.parse(this.accountService.getLogin());
  }

}

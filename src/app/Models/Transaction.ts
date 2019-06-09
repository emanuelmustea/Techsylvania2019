//generated automatically
import { log } from 'util';
import { Injectable } from '@angular/core'
import { Categorie } from './/Categorie'
import { Account } from './/Account'
export interface Transaction
{
	transactionId : number;
	accountId : number;
	categorieId : number;
	name : string;
	value : number;
	creationTime : string;
	categorie : Categorie;
	account : Account;

}

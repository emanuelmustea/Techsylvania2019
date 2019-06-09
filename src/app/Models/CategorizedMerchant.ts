//generated automatically
import { log } from 'util';
import { Injectable } from '@angular/core'
import { Merchant } from './/Merchant'
import { Categorie } from './/Categorie'
export interface CategorizedMerchant
{
	categorizedMerchantId : number;
	categorieId : number;
	merchantId : number;
	creationTime : string;
	merchant : Merchant;
	categorie : Categorie;

}

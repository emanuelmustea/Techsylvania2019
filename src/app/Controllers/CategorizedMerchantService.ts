import {HttpClient} from '@angular/common/http';
import { ServerUrl } from '../Helpers/ServerUrl'
import { Injectable } from '@angular/core';
import { CategorizedMerchant } from '../Models/CategorizedMerchant'
import { Merchant } from '../Models/Merchant'
import { MerchantService } from './MerchantService'
import { Categorie } from '../Models/Categorie'
import { CategorieService } from './CategorieService'

@Injectable({
    providedIn : 'root'
})
export class CategorizedMerchantService
{
	public categorizedMerchants : CategorizedMerchant[];
	GetCategorizedMerchants()
	{
		return this.http.get<CategorizedMerchant[]>(ServerUrl.GetUrl()  + "CategorizedMerchants.php?cmd=getCategorizedMerchants").subscribe(data =>
		{
			this.categorizedMerchants = data;
		});
	}
	
	GetLastCategorizedMerchant()
	{
		return this.http.get<CategorizedMerchant[]>(ServerUrl.GetUrl()  + "CategorizedMerchants.php?cmd=getLastCategorizedMerchant");
	}
	
	static GetDefaultCategorizedMerchant()
	{
		return {
		categorizedMerchantId : 0,
		categorieId : 0,
		merchantId : 0,
		creationTime : '2000-01-01 00:00:00',
		merchant : MerchantService.GetDefaultMerchant(),
		categorie : CategorieService.GetDefaultCategorie()
		};
	}
	
	constructor(private http:HttpClient)
	{
		this.categorizedMerchants = [CategorizedMerchantService.GetDefaultCategorizedMerchant()];
		this.GetCategorizedMerchants();
	
	}
	
	AddCategorizedMerchant(categorizedMerchant)
	{
		return this.http.post<CategorizedMerchant>(ServerUrl.GetUrl()  + "CategorizedMerchants.php?cmd=addCategorizedMerchant", categorizedMerchant).subscribe(categorizedMerchant =>
		{
			console.log(categorizedMerchant);
			if(0 != categorizedMerchant.categorizedMerchantId)
			{
				this.categorizedMerchants.push(categorizedMerchant)
			}
		});
	}
	
	UpdateCategorizedMerchant(categorizedMerchant)
	{
		return this.http.put<CategorizedMerchant>(ServerUrl.GetUrl()  + "CategorizedMerchants.php?cmd=updateCategorizedMerchant", categorizedMerchant).subscribe(categorizedMerchant =>
		{
			console.log(categorizedMerchant);
			return categorizedMerchant;
		});
	}
	
	DeleteCategorizedMerchant(categorizedMerchant)
	{
		return this.http.delete<CategorizedMerchant>(ServerUrl.GetUrl()  + "CategorizedMerchants.php?cmd=deleteCategorizedMerchant&categorizedMerchantId=" +  categorizedMerchant.categorizedMerchantId, ).subscribe(categorizedMerchant =>
		{
			console.log(categorizedMerchant);
			return categorizedMerchant;
		});
	}
	
	GetCategorizedMerchantsByCategorizedMerchantId(categorizedMerchantId)
	{
		return this.http.get<CategorizedMerchant[]>(ServerUrl.GetUrl()  + `CategorizedMerchants.php?cmd=getCategorizedMerchantsByCategorizedMerchantId&categorizedMerchantId=${categorizedMerchantId}`);
	}
	

}

import {HttpClient} from '@angular/common/http';
import { ServerUrl } from '../Helpers/ServerUrl'
import { Injectable } from '@angular/core';
import { Merchant } from '../Models/Merchant'

@Injectable({
    providedIn : 'root'
})
export class MerchantService
{
	public merchants : Merchant[];
	GetMerchants()
	{
		return this.http.get<Merchant[]>(ServerUrl.GetUrl()  + "Merchants.php?cmd=getMerchants").subscribe(data =>
		{
			this.merchants = data;
		});
	}
	
	GetLastMerchant()
	{
		return this.http.get<Merchant[]>(ServerUrl.GetUrl()  + "Merchants.php?cmd=getLastMerchant");
	}
	
	static GetDefaultMerchant()
	{
		return {
		merchantId : 0,
		name : 'Test',
		creationTime : '2000-01-01 00:00:00'
		};
	}
	
	constructor(private http:HttpClient)
	{
		this.merchants = [MerchantService.GetDefaultMerchant()];
		this.GetMerchants();
	
	}
	
	AddMerchant(merchant)
	{
		return this.http.post<Merchant>(ServerUrl.GetUrl()  + "Merchants.php?cmd=addMerchant", merchant).subscribe(merchant =>
		{
			console.log(merchant);
			if(0 != merchant.merchantId)
			{
				this.merchants.push(merchant)
			}
		});
	}
	
	UpdateMerchant(merchant)
	{
		return this.http.put<Merchant>(ServerUrl.GetUrl()  + "Merchants.php?cmd=updateMerchant", merchant).subscribe(merchant =>
		{
			console.log(merchant);
			return merchant;
		});
	}
	
	DeleteMerchant(merchant)
	{
		return this.http.delete<Merchant>(ServerUrl.GetUrl()  + "Merchants.php?cmd=deleteMerchant&merchantId=" +  merchant.merchantId, ).subscribe(merchant =>
		{
			console.log(merchant);
			return merchant;
		});
	}
	
	GetMerchantsByMerchantId(merchantId)
	{
		return this.http.get<Merchant[]>(ServerUrl.GetUrl()  + `Merchants.php?cmd=getMerchantsByMerchantId&merchantId=${merchantId}`);
	}
	

}

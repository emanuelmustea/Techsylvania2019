import {HttpClient} from '@angular/common/http';
import { ServerUrl } from '../Helpers/ServerUrl'
import { Injectable } from '@angular/core';
import { Categorie } from '../Models/Categorie'
import Realtimify from '../Helpers/Realtimify';

@Injectable({
    providedIn : 'root'
})
export class CategorieService
{
	public categories : Categorie[];
	static GetDefaultCategorie()
	{
		return {
		categorieId : 0,
		name : 'Test',
		creationTime : '2000-01-01 00:00:00'
		};
	}
	constructor(private http:HttpClient)
	{
		this.categories = [CategorieService.GetDefaultCategorie()];
		this.GetCategories();
	
	}
	GetCategories()
	{
		return Realtimify(()=> this.http.get<Categorie[]>(ServerUrl.GetUrl()  + "Categories.php?cmd=getCategories"));
	}
	GetCategoryByValue()
	{
		return Realtimify(()=> this.http.get<Categorie[]>(ServerUrl.GetUrl()  + "Categories.php?cmd=getByValue"));
	}

	
	AddCategorie(categorie)
	{
		return this.http.post<Categorie>(ServerUrl.GetUrl()  + "Categories.php?cmd=addCategorie", categorie).subscribe(categorie =>
		{
			console.log(categorie);
			if(0 != categorie.categorieId)
			{
				this.categories.push(categorie)
			}
		});
	}
	
	UpdateCategorie(categorie)
	{
		return this.http.put<Categorie>(ServerUrl.GetUrl()  + "Categories.php?cmd=updateCategorie", categorie).subscribe(categorie =>
		{
			console.log(categorie);
			return categorie;
		});
	}
	
	DeleteCategorie(categorie)
	{
		return this.http.delete<Categorie>(ServerUrl.GetUrl()  + "Categories.php?cmd=deleteCategorie&categorieId=" +  categorie.categorieId, ).subscribe(categorie =>
		{
			console.log(categorie);
			return categorie;
		});
	}
	
	GetCategoriesByCategorieId(categorieId)
	{
		return this.http.get<Categorie[]>(ServerUrl.GetUrl()  + `Categories.php?cmd=getCategoriesByCategorieId&categorieId=${categorieId}`);
	}
	

}

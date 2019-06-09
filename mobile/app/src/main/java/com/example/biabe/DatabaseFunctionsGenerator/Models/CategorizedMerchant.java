//generated automatically
package com.example.biabe.DatabaseFunctionsGenerator.Models;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.POST;
import retrofit2.http.Body;
import java.util.Date;

public class CategorizedMerchant
{
	private Integer  categorizedMerchantId;
	private Integer  categorieId;
	private Integer  merchantId;
	private Date creationTime;
	private Merchant merchant;
	private Categorie categorie;
	
	public Integer  getCategorizedMerchantId()
	{
		return this.categorizedMerchantId;
	}
	
	public void setCategorizedMerchantId(Integer  categorizedMerchantId)
	{
		this.categorizedMerchantId = categorizedMerchantId;
	}
	
	public Integer  getCategorieId()
	{
		return this.categorieId;
	}
	
	public void setCategorieId(Integer  categorieId)
	{
		this.categorieId = categorieId;
	}
	
	public Integer  getMerchantId()
	{
		return this.merchantId;
	}
	
	public void setMerchantId(Integer  merchantId)
	{
		this.merchantId = merchantId;
	}
	
	public Date getCreationTime()
	{
		return this.creationTime;
	}
	
	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}
	
	public Merchant getMerchant()
	{
		return this.merchant;
	}
	
	public void setMerchant(Merchant merchant)
	{
		this.merchant = merchant;
	}
	
	public Categorie getCategorie()
	{
		return this.categorie;
	}
	
	public void setCategorie(Categorie categorie)
	{
		this.categorie = categorie;
	}
	
	
	public CategorizedMerchant(Integer  categorieId, Integer  merchantId)
	{
		this.categorieId = categorieId;
		this.merchantId = merchantId;
	}
	
	public CategorizedMerchant(Integer  categorieId, Integer  merchantId, Merchant merchant, Categorie categorie)
	{
		this(
			0, //CategorieId
			0 //MerchantId
		);
		this.merchant = merchant;
		this.categorie = categorie;
	
	}
	
	public CategorizedMerchant()
	{
		this(
			0, //CategorieId
			0 //MerchantId
		);
		this.categorizedMerchantId = 0;
		this.creationTime = new Date(0);
	
	}
	

}

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

public class Transaction
{
	private Integer  transactionId;
	private Integer  categorieId;
	private String name;
	private double value;
	private Date creationTime;
	private Categorie categorie;
	
	public Integer  getTransactionId()
	{
		return this.transactionId;
	}
	
	public void setTransactionId(Integer  transactionId)
	{
		this.transactionId = transactionId;
	}
	
	public Integer  getCategorieId()
	{
		return this.categorieId;
	}
	
	public void setCategorieId(Integer  categorieId)
	{
		this.categorieId = categorieId;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public double getValue()
	{
		return this.value;
	}
	
	public void setValue(double value)
	{
		this.value = value;
	}
	
	public Date getCreationTime()
	{
		return this.creationTime;
	}
	
	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}
	
	public Categorie getCategorie()
	{
		return this.categorie;
	}
	
	public void setCategorie(Categorie categorie)
	{
		this.categorie = categorie;
	}
	
	
	public Transaction(Integer  categorieId, String name, double value)
	{
		this.categorieId = categorieId;
		this.name = name;
		this.value = value;
	}
	
	public Transaction(Integer  categorieId, String name, double value, Categorie categorie)
	{
		this(
			0, //CategorieId
			"Test", //Name
			0 //Value
		);
		this.categorie = categorie;
	
	}
	
	public Transaction()
	{
		this(
			0, //CategorieId
			"Test", //Name
			0 //Value
		);
		this.transactionId = 0;
		this.creationTime = new Date(0);
	
	}
	

}

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

public class Categorie
{
	private Integer  categorieId;
	private String name;
	private Date creationTime;
	private Integer value;
	
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
	
	public Date getCreationTime()
	{
		return this.creationTime;
	}
	
	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}

	public Integer getValue() {return value;}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Categorie(String name)
	{
		this.name = name;
	}
	
	public Categorie()
	{
		this(
			"Test" //Name
		);
		this.categorieId = 0;
		this.creationTime = new Date(0);
	
	}
	

}

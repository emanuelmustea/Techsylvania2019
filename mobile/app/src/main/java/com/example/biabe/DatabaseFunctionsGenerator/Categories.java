//generated automatically
package com.example.biabe.DatabaseFunctionsGenerator;
import com.example.biabe.DatabaseFunctionsGenerator.Models.*;
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
interface CategorieService
{
	@GET("Categories.php?cmd=getCategories")
	Call<List<Categorie>> getCategories();

	@GET("Categories.php?cmd=getCategoriesByCategorieId")
	Call<List<Categorie>> getCategoriesByCategorieId(@Query("categorieId")Integer  categorieId);

	@GET("Categories.php?cmd=getByValue")
	Call<List<Categorie>> getCategoriesByValue();
	
	@POST("Categories.php?cmd=addCategorie")
	Call<Categorie> addCategorie(@Body Categorie categorie);

}

public class Categories
{
	public static List<Categorie> getCategories(Call<List<Categorie>> call)
	{
		List<Categorie> categories;
		
		categories = null;
		
		try
		{
			categories = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return categories;
	
	}
	public static List<Categorie> getCategories()
	{
		List<Categorie> categories;
		CategorieService service;
		Call<List<Categorie>> call;
		
		categories = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorieService.class);
		try
		{
			call = service.getCategories();
			categories = getCategories(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return categories;
	
	}

	public static List<Categorie> getCategoriesByCategorieId(Integer  categorieId)
	{
		List<Categorie> categories;
		CategorieService service;
		Call<List<Categorie>> call;

		categories = null;

		service = RetrofitInstance.GetRetrofitInstance().create(CategorieService.class);
		try
		{
			call = service.getCategoriesByCategorieId(categorieId);
			categories = getCategories(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}

		return categories;

	}

	public static List<Categorie> getCategoriesByValue()
	{
		List<Categorie> categories;
		CategorieService service;
		Call<List<Categorie>> call;

		categories = null;

		service = RetrofitInstance.GetRetrofitInstance().create(CategorieService.class);
		try
		{
			call = service.getCategoriesByValue();
			categories = getCategories(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}

		return categories;

	}
	
	
	public static void getCategories(Call<List<Categorie>> call, Callback<List<Categorie>> callback)
	{
		try
		{
			call.enqueue(callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	public static void getCategories(Callback<List<Categorie>> callback)
	{
		List<Categorie> categories;
		CategorieService service;
		Call<List<Categorie>> call;
		
		categories = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorieService.class);
		try
		{
			call = service.getCategories();
			getCategories(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	public static void getCategoriesByCategorieId(Integer  categorieId, Callback<List<Categorie>> callback)
	{
		List<Categorie> categories;
		CategorieService service;
		Call<List<Categorie>> call;
		
		categories = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorieService.class);
		try
		{
			call = service.getCategoriesByCategorieId(categorieId);
			getCategories(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}

	public static void getCategoriesByValue(Callback<List<Categorie>> callback)
	{
		List<Categorie> categories;
		CategorieService service;
		Call<List<Categorie>> call;

		categories = null;

		service = RetrofitInstance.GetRetrofitInstance().create(CategorieService.class);
		try
		{
			call = service.getCategoriesByValue();
			getCategories(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}


	}
	
	public static Categorie addCategorie(Categorie categorie)
	{
		CategorieService service;
		Call<Categorie> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorieService.class);
		try
		{
			call = service.addCategorie(categorie);
			categorie = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return categorie;
	
	}
	
	public static void addCategorie(Categorie categorie, Callback<Categorie> callback)
	{
		CategorieService service;
		Call<Categorie> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorieService.class);
		try
		{
			call = service.addCategorie(categorie);
			call.enqueue(callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
	
	}
	

}

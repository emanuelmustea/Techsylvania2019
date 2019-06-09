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
interface CategorizedMerchantService
{
	@GET("CategorizedMerchants.php?cmd=getCategorizedMerchants")
	Call<List<CategorizedMerchant>> getCategorizedMerchants();
	
	@GET("CategorizedMerchants.php?cmd=getCategorizedMerchantsByCategorizedMerchantId")
	Call<List<CategorizedMerchant>> getCategorizedMerchantsByCategorizedMerchantId(@Query("categorizedMerchantId")Integer  categorizedMerchantId);
	
	@POST("CategorizedMerchants.php?cmd=addCategorizedMerchant")
	Call<CategorizedMerchant> addCategorizedMerchant(@Body CategorizedMerchant categorizedMerchant);

}

public class CategorizedMerchants
{
	public static List<CategorizedMerchant> getCategorizedMerchants(Call<List<CategorizedMerchant>> call)
	{
		List<CategorizedMerchant> categorizedMerchants;
		
		categorizedMerchants = null;
		
		try
		{
			categorizedMerchants = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return categorizedMerchants;
	
	}
	public static List<CategorizedMerchant> getCategorizedMerchants()
	{
		List<CategorizedMerchant> categorizedMerchants;
		CategorizedMerchantService service;
		Call<List<CategorizedMerchant>> call;
		
		categorizedMerchants = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorizedMerchantService.class);
		try
		{
			call = service.getCategorizedMerchants();
			categorizedMerchants = getCategorizedMerchants(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return categorizedMerchants;
	
	}
	
	public static List<CategorizedMerchant> getCategorizedMerchantsByCategorizedMerchantId(Integer  categorizedMerchantId)
	{
		List<CategorizedMerchant> categorizedMerchants;
		CategorizedMerchantService service;
		Call<List<CategorizedMerchant>> call;
		
		categorizedMerchants = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorizedMerchantService.class);
		try
		{
			call = service.getCategorizedMerchantsByCategorizedMerchantId(categorizedMerchantId);
			categorizedMerchants = getCategorizedMerchants(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return categorizedMerchants;
	
	}
	
	
	public static void getCategorizedMerchants(Call<List<CategorizedMerchant>> call, Callback<List<CategorizedMerchant>> callback)
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
	public static void getCategorizedMerchants(Callback<List<CategorizedMerchant>> callback)
	{
		List<CategorizedMerchant> categorizedMerchants;
		CategorizedMerchantService service;
		Call<List<CategorizedMerchant>> call;
		
		categorizedMerchants = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorizedMerchantService.class);
		try
		{
			call = service.getCategorizedMerchants();
			getCategorizedMerchants(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	public static void getCategorizedMerchantsByCategorizedMerchantId(Integer  categorizedMerchantId, Callback<List<CategorizedMerchant>> callback)
	{
		List<CategorizedMerchant> categorizedMerchants;
		CategorizedMerchantService service;
		Call<List<CategorizedMerchant>> call;
		
		categorizedMerchants = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorizedMerchantService.class);
		try
		{
			call = service.getCategorizedMerchantsByCategorizedMerchantId(categorizedMerchantId);
			getCategorizedMerchants(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	
	public static CategorizedMerchant addCategorizedMerchant(CategorizedMerchant categorizedMerchant)
	{
		CategorizedMerchantService service;
		Call<CategorizedMerchant> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorizedMerchantService.class);
		try
		{
			call = service.addCategorizedMerchant(categorizedMerchant);
			categorizedMerchant = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return categorizedMerchant;
	
	}
	
	public static void addCategorizedMerchant(CategorizedMerchant categorizedMerchant, Callback<CategorizedMerchant> callback)
	{
		CategorizedMerchantService service;
		Call<CategorizedMerchant> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(CategorizedMerchantService.class);
		try
		{
			call = service.addCategorizedMerchant(categorizedMerchant);
			call.enqueue(callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
	
	}
	

}

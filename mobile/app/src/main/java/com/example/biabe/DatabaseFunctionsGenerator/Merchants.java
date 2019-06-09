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
interface MerchantService
{
	@GET("Merchants.php?cmd=getMerchants")
	Call<List<Merchant>> getMerchants();
	
	@GET("Merchants.php?cmd=getMerchantsByMerchantId")
	Call<List<Merchant>> getMerchantsByMerchantId(@Query("merchantId")Integer  merchantId);
	
	@POST("Merchants.php?cmd=addMerchant")
	Call<Merchant> addMerchant(@Body Merchant merchant);

}

public class Merchants
{
	public static List<Merchant> getMerchants(Call<List<Merchant>> call)
	{
		List<Merchant> merchants;
		
		merchants = null;
		
		try
		{
			merchants = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return merchants;
	
	}
	public static List<Merchant> getMerchants()
	{
		List<Merchant> merchants;
		MerchantService service;
		Call<List<Merchant>> call;
		
		merchants = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(MerchantService.class);
		try
		{
			call = service.getMerchants();
			merchants = getMerchants(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return merchants;
	
	}
	
	public static List<Merchant> getMerchantsByMerchantId(Integer  merchantId)
	{
		List<Merchant> merchants;
		MerchantService service;
		Call<List<Merchant>> call;
		
		merchants = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(MerchantService.class);
		try
		{
			call = service.getMerchantsByMerchantId(merchantId);
			merchants = getMerchants(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return merchants;
	
	}
	
	
	public static void getMerchants(Call<List<Merchant>> call, Callback<List<Merchant>> callback)
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
	public static void getMerchants(Callback<List<Merchant>> callback)
	{
		List<Merchant> merchants;
		MerchantService service;
		Call<List<Merchant>> call;
		
		merchants = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(MerchantService.class);
		try
		{
			call = service.getMerchants();
			getMerchants(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	public static void getMerchantsByMerchantId(Integer  merchantId, Callback<List<Merchant>> callback)
	{
		List<Merchant> merchants;
		MerchantService service;
		Call<List<Merchant>> call;
		
		merchants = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(MerchantService.class);
		try
		{
			call = service.getMerchantsByMerchantId(merchantId);
			getMerchants(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	
	public static Merchant addMerchant(Merchant merchant)
	{
		MerchantService service;
		Call<Merchant> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(MerchantService.class);
		try
		{
			call = service.addMerchant(merchant);
			merchant = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return merchant;
	
	}
	
	public static void addMerchant(Merchant merchant, Callback<Merchant> callback)
	{
		MerchantService service;
		Call<Merchant> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(MerchantService.class);
		try
		{
			call = service.addMerchant(merchant);
			call.enqueue(callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
	
	}
	

}

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
interface TransactionService
{
	@GET("Transactions.php?cmd=getTransactions")
	Call<List<Transaction>> getTransactions();

	@GET("Transactions.php?cmd=getSpendingAlerts")
	Call<List<Transaction>> getSpendingAlerts();

	@GET("Transactions.php?cmd=getTransactionsByTransactionId")
	Call<List<Transaction>> getTransactionsByTransactionId(@Query("transactionId")Integer  transactionId);
	
	@POST("Transactions.php?cmd=addTransaction")
	Call<Transaction> addTransaction(@Body Transaction transaction);
//getSpendingAlerts
}

public class Transactions
{
	public static List<Transaction> getTransactions(Call<List<Transaction>> call)
	{
		List<Transaction> transactions;
		
		transactions = null;
		
		try
		{
			transactions = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return transactions;
	
	}
	public static List<Transaction> getTransactions()
	{
		List<Transaction> transactions;
		TransactionService service;
		Call<List<Transaction>> call;
		
		transactions = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(TransactionService.class);
		try
		{
			call = service.getTransactions();
			transactions = getTransactions(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return transactions;
	
	}

	public static List<Transaction> getTransactionsByTransactionId(Integer  transactionId)
	{
		List<Transaction> transactions;
		TransactionService service;
		Call<List<Transaction>> call;

		transactions = null;

		service = RetrofitInstance.GetRetrofitInstance().create(TransactionService.class);
		try
		{
			call = service.getTransactionsByTransactionId(transactionId);
			transactions = getTransactions(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}

		return transactions;

	}

	public static List<Transaction> getSpendingAlerts()
	{
		List<Transaction> transactions;
		TransactionService service;
		Call<List<Transaction>> call;

		transactions = null;

		service = RetrofitInstance.GetRetrofitInstance().create(TransactionService.class);
		try
		{
			call = service.getSpendingAlerts();
			transactions = getTransactions(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}

		return transactions;

	}
	
	
	public static void getTransactions(Call<List<Transaction>> call, Callback<List<Transaction>> callback)
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
	public static void getTransactions(Callback<List<Transaction>> callback)
	{
		List<Transaction> transactions;
		TransactionService service;
		Call<List<Transaction>> call;
		
		transactions = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(TransactionService.class);
		try
		{
			call = service.getTransactions();
			getTransactions(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	public static void getTransactionsByTransactionId(Integer  transactionId, Callback<List<Transaction>> callback)
	{
		List<Transaction> transactions;
		TransactionService service;
		Call<List<Transaction>> call;
		
		transactions = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(TransactionService.class);
		try
		{
			call = service.getTransactionsByTransactionId(transactionId);
			getTransactions(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	
	public static Transaction addTransaction(Transaction transaction)
	{
		TransactionService service;
		Call<Transaction> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(TransactionService.class);
		try
		{
			call = service.addTransaction(transaction);
			transaction = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return transaction;
	
	}
	
	public static void addTransaction(Transaction transaction, Callback<Transaction> callback)
	{
		TransactionService service;
		Call<Transaction> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(TransactionService.class);
		try
		{
			call = service.addTransaction(transaction);
			call.enqueue(callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
	
	}
	

}

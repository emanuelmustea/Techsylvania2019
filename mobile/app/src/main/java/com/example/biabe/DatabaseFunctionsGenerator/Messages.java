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
interface MessageService
{
	@GET("Messages.php?cmd=getMessages")
	Call<List<Message>> getMessages();
	
	@GET("Messages.php?cmd=getMessagesByMessageId")
	Call<List<Message>> getMessagesByMessageId(@Query("messageId")Integer  messageId);
	
	@POST("Messages.php?cmd=addMessage")
	Call<Message> addMessage(@Body Message message);

}

public class Messages
{
	public static List<Message> getMessages(Call<List<Message>> call)
	{
		List<Message> messages;
		
		messages = null;
		
		try
		{
			messages = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return messages;
	
	}
	public static List<Message> getMessages()
	{
		List<Message> messages;
		MessageService service;
		Call<List<Message>> call;
		
		messages = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(MessageService.class);
		try
		{
			call = service.getMessages();
			messages = getMessages(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return messages;
	
	}
	
	public static List<Message> getMessagesByMessageId(Integer  messageId)
	{
		List<Message> messages;
		MessageService service;
		Call<List<Message>> call;
		
		messages = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(MessageService.class);
		try
		{
			call = service.getMessagesByMessageId(messageId);
			messages = getMessages(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return messages;
	
	}
	
	
	public static void getMessages(Call<List<Message>> call, Callback<List<Message>> callback)
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
	public static void getMessages(Callback<List<Message>> callback)
	{
		List<Message> messages;
		MessageService service;
		Call<List<Message>> call;
		
		messages = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(MessageService.class);
		try
		{
			call = service.getMessages();
			getMessages(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	public static void getMessagesByMessageId(Integer  messageId, Callback<List<Message>> callback)
	{
		List<Message> messages;
		MessageService service;
		Call<List<Message>> call;
		
		messages = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(MessageService.class);
		try
		{
			call = service.getMessagesByMessageId(messageId);
			getMessages(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	
	public static Message addMessage(Message message)
	{
		MessageService service;
		Call<Message> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(MessageService.class);
		try
		{
			call = service.addMessage(message);
			message = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return message;
	
	}
	
	public static void addMessage(Message message, Callback<Message> callback)
	{
		MessageService service;
		Call<Message> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(MessageService.class);
		try
		{
			call = service.addMessage(message);
			call.enqueue(callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
	
	}
	

}

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
interface NotificationService
{
	@GET("Notifications.php?cmd=getNotifications")
	Call<List<Notification>> getNotifications();
	
	@GET("Notifications.php?cmd=getNotificationsByNotificationId")
	Call<List<Notification>> getNotificationsByNotificationId(@Query("notificationId")Integer  notificationId);
	
	@POST("Notifications.php?cmd=addNotification")
	Call<Notification> addNotification(@Body Notification notification);

}

public class Notifications
{
	public static List<Notification> getNotifications(Call<List<Notification>> call)
	{
		List<Notification> notifications;
		
		notifications = null;
		
		try
		{
			notifications = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return notifications;
	
	}
	public static List<Notification> getNotifications()
	{
		List<Notification> notifications;
		NotificationService service;
		Call<List<Notification>> call;
		
		notifications = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(NotificationService.class);
		try
		{
			call = service.getNotifications();
			notifications = getNotifications(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return notifications;
	
	}
	
	public static List<Notification> getNotificationsByNotificationId(Integer  notificationId)
	{
		List<Notification> notifications;
		NotificationService service;
		Call<List<Notification>> call;
		
		notifications = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(NotificationService.class);
		try
		{
			call = service.getNotificationsByNotificationId(notificationId);
			notifications = getNotifications(call);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return notifications;
	
	}
	
	
	public static void getNotifications(Call<List<Notification>> call, Callback<List<Notification>> callback)
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
	public static void getNotifications(Callback<List<Notification>> callback)
	{
		List<Notification> notifications;
		NotificationService service;
		Call<List<Notification>> call;
		
		notifications = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(NotificationService.class);
		try
		{
			call = service.getNotifications();
			getNotifications(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	public static void getNotificationsByNotificationId(Integer  notificationId, Callback<List<Notification>> callback)
	{
		List<Notification> notifications;
		NotificationService service;
		Call<List<Notification>> call;
		
		notifications = null;
		
		service = RetrofitInstance.GetRetrofitInstance().create(NotificationService.class);
		try
		{
			call = service.getNotificationsByNotificationId(notificationId);
			getNotifications(call, callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
	
	}
	
	
	public static Notification addNotification(Notification notification)
	{
		NotificationService service;
		Call<Notification> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(NotificationService.class);
		try
		{
			call = service.addNotification(notification);
			notification = call.execute().body();
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
		
		return notification;
	
	}
	
	public static void addNotification(Notification notification, Callback<Notification> callback)
	{
		NotificationService service;
		Call<Notification> call;
		
		
		service = RetrofitInstance.GetRetrofitInstance().create(NotificationService.class);
		try
		{
			call = service.addNotification(notification);
			call.enqueue(callback);
		}
		catch(Exception ee)
		{
			System.out.println(ee.getMessage());
		}
	
	}
	

}

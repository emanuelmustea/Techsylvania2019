//generated automatically
package com.mindorks.demo.Models;
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

public class Notification
{
	private Integer  notificationId;
	private String title;
	private Date creationTime;
	
	public Integer  getNotificationId()
	{
		return this.notificationId;
	}
	
	public void setNotificationId(Integer  notificationId)
	{
		this.notificationId = notificationId;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public Date getCreationTime()
	{
		return this.creationTime;
	}
	
	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}
	
	
	public Notification(String title)
	{
		this.title = title;
	}
	
	public Notification()
	{
		this(
			"Test" //Title
		);
		this.notificationId = 0;
		this.creationTime = new Date(0);
	
	}
	

}

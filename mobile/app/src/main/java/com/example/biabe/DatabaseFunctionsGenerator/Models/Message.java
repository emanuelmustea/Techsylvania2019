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

public class Message
{
	private Integer  messageId;
	private String content;
	private Integer  source;
	private Date creationTime;
	
	public Integer  getMessageId()
	{
		return this.messageId;
	}
	
	public void setMessageId(Integer  messageId)
	{
		this.messageId = messageId;
	}
	
	public String getContent()
	{
		return this.content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public Integer  getSource()
	{
		return this.source;
	}
	
	public void setSource(Integer  source)
	{
		this.source = source;
	}
	
	public Date getCreationTime()
	{
		return this.creationTime;
	}
	
	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}
	
	
	public Message(String content, Integer  source)
	{
		this.content = content;
		this.source = source;
	}
	
	public Message()
	{
		this(
			"Test", //Content
			0 //Source
		);
		this.messageId = 0;
		this.creationTime = new Date(0);
	
	}
	

}

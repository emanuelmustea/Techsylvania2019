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

public class Rating
{
	private Integer  ratingId;
	private Integer  attractionId;
	private Integer  mark;
	private String description;
	private Date creationTime;
	private Attraction attraction;
	
	public Integer  getRatingId()
	{
		return this.ratingId;
	}
	
	public void setRatingId(Integer  ratingId)
	{
		this.ratingId = ratingId;
	}
	
	public Integer  getAttractionId()
	{
		return this.attractionId;
	}
	
	public void setAttractionId(Integer  attractionId)
	{
		this.attractionId = attractionId;
	}
	
	public Integer  getMark()
	{
		return this.mark;
	}
	
	public void setMark(Integer  mark)
	{
		this.mark = mark;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public Date getCreationTime()
	{
		return this.creationTime;
	}
	
	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}
	
	public Attraction getAttraction()
	{
		return this.attraction;
	}
	
	public void setAttraction(Attraction attraction)
	{
		this.attraction = attraction;
	}
	
	
	public Rating(Integer  attractionId, Integer  mark, String description)
	{
		this.attractionId = attractionId;
		this.mark = mark;
		this.description = description;
	}
	
	public Rating(Integer  attractionId, Integer  mark, String description, Attraction attraction)
	{
		this(
			0, //AttractionId
			0, //Mark
			"Test" //Description
		);
		this.attraction = attraction;
	
	}
	
	public Rating()
	{
		this(
			0, //AttractionId
			0, //Mark
			"Test" //Description
		);
		this.ratingId = 0;
		this.creationTime = new Date(0);
	
	}
	

}

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

public class Attraction
{
	private Integer  attractionId;
	private String name;
	private String xLoc;
	private String yLoc;
	private String image;
	private String arImage;
	private String description;
	private Date creationTime;
	
	public Integer  getAttractionId()
	{
		return this.attractionId;
	}
	
	public void setAttractionId(Integer  attractionId)
	{
		this.attractionId = attractionId;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getXLoc()
	{
		return this.xLoc;
	}
	
	public void setXLoc(String xLoc)
	{
		this.xLoc = xLoc;
	}
	
	public String getYLoc()
	{
		return this.yLoc;
	}
	
	public void setYLoc(String yLoc)
	{
		this.yLoc = yLoc;
	}
	
	public String getImage()
	{
		return this.image;
	}
	
	public void setImage(String image)
	{
		this.image = image;
	}
	
	public String getArImage()
	{
		return this.arImage;
	}
	
	public void setArImage(String arImage)
	{
		this.arImage = arImage;
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
	
	
	public Attraction(String name, String xLoc, String yLoc, String image, String arImage, String description)
	{
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.image = image;
		this.arImage = arImage;
		this.description = description;
	}
	
	public Attraction()
	{
		this(
			"Test", //Name
			"Test", //XLoc
			"Test", //YLoc
			"Test", //Image
			"Test", //ArImage
			"Test" //Description
		);
		this.attractionId = 0;
		this.creationTime = new Date(0);
	
	}
	

}

import {HttpClient} from '@angular/common/http';
import { ServerUrl } from '../Helpers/ServerUrl'
import { Injectable } from '@angular/core';
import { Notification } from '../Models/Notification'

@Injectable({
    providedIn : 'root'
})
export class NotificationService
{
	public notifications : Notification[];
	GetNotifications()
	{
		return this.http.get<Notification[]>(ServerUrl.GetUrl()  + "Notifications.php?cmd=getNotifications").subscribe(data =>
		{
			this.notifications = data;
		});
	}
	
	GetLastNotification()
	{
		return this.http.get<Notification[]>(ServerUrl.GetUrl()  + "Notifications.php?cmd=getLastNotification");
	}
	
	static GetDefaultNotification()
	{
		return {
		notificationId : 0,
		title : 'Test',
		message : 'Test',
		creationTime : '2000-01-01 00:00:00'
		};
	}
	
	constructor(private http:HttpClient)
	{
		this.notifications = [NotificationService.GetDefaultNotification()];
		this.GetNotifications();
	
	}
	
	AddNotification(notification)
	{
		return this.http.post<Notification>(ServerUrl.GetUrl()  + "Notifications.php?cmd=addNotification", notification).subscribe(notification =>
		{
			console.log(notification);
			if(0 != notification.notificationId)
			{
				this.notifications.push(notification)
			}
		});
	}
	
	UpdateNotification(notification)
	{
		return this.http.put<Notification>(ServerUrl.GetUrl()  + "Notifications.php?cmd=updateNotification", notification).subscribe(notification =>
		{
			console.log(notification);
			return notification;
		});
	}
	
	DeleteNotification(notification)
	{
		return this.http.delete<Notification>(ServerUrl.GetUrl()  + "Notifications.php?cmd=deleteNotification&notificationId=" +  notification.notificationId, ).subscribe(notification =>
		{
			console.log(notification);
			return notification;
		});
	}
	
	GetNotificationsByNotificationId(notificationId)
	{
		return this.http.get<Notification[]>(ServerUrl.GetUrl()  + `Notifications.php?cmd=getNotificationsByNotificationId&notificationId=${notificationId}`);
	}
	

}

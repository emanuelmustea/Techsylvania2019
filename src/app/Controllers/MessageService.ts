import {HttpClient} from '@angular/common/http';
import { ServerUrl } from '../Helpers/ServerUrl'
import { Injectable } from '@angular/core';
import { Message } from '../Models/Message'

@Injectable({
    providedIn : 'root'
})
export class MessageService
{
	public messages : Message[];
	GetMessages()
	{
		return this.http.get<Message[]>(ServerUrl.GetUrl()  + "Messages.php?cmd=getMessages").subscribe(data =>
		{
			this.messages = data;
		});
	}
	
	GetLastMessage()
	{
		return this.http.get<Message[]>(ServerUrl.GetUrl()  + "Messages.php?cmd=getLastMessage");
	}
	
	static GetDefaultMessage()
	{
		return {
		messageId : 0,
		content : 'Test',
		source : 0,
		creationTime : '2000-01-01 00:00:00'
		};
	}
	
	constructor(private http:HttpClient)
	{
		this.messages = [MessageService.GetDefaultMessage()];
		this.GetMessages();
	
	}
	
	AddMessage(message)
	{
		return this.http.post<Message>(ServerUrl.GetUrl()  + "Messages.php?cmd=addMessage", message).subscribe(message =>
		{
			console.log(message);
			if(0 != message.messageId)
			{
				this.messages.push(message)
			}
		});
	}
	
	UpdateMessage(message)
	{
		return this.http.put<Message>(ServerUrl.GetUrl()  + "Messages.php?cmd=updateMessage", message).subscribe(message =>
		{
			console.log(message);
			return message;
		});
	}
	
	DeleteMessage(message)
	{
		return this.http.delete<Message>(ServerUrl.GetUrl()  + "Messages.php?cmd=deleteMessage&messageId=" +  message.messageId, ).subscribe(message =>
		{
			console.log(message);
			return message;
		});
	}
	
	GetMessagesByMessageId(messageId)
	{
		return this.http.get<Message[]>(ServerUrl.GetUrl()  + `Messages.php?cmd=getMessagesByMessageId&messageId=${messageId}`);
	}
	

}

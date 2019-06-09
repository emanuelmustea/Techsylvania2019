<?php
//generated automatically
class Message
{
	var $messageId;
	var $content;
	var $source;
	var $creationTime;

	function GetMessageId()
	{
		return $this->messageId;
	}
	function SetMessageId($value)
	{
		$this->messageId = $value;
	}
	
	function GetContent()
	{
		return $this->content;
	}
	function SetContent($value)
	{
		$this->content = $value;
	}
	
	function GetSource()
	{
		return $this->source;
	}
	function SetSource($value)
	{
		$this->source = $value;
	}
	
	function GetCreationTime()
	{
		return $this->creationTime;
	}
	function SetCreationTime($value)
	{
		$this->creationTime = $value;
	}
	

	function Message($Content, $Source)
	{
		$this->messageId = 0;
	
		$this->content = $Content;
		$this->source = $Source;
	}

}
?>

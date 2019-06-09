<?php
//generated automatically
class Account
{
	var $accountId;
	var $email;
	var $password;
	var $balance;
	var $creationTime;

	function GetAccountId()
	{
		return $this->accountId;
	}
	function SetAccountId($value)
	{
		$this->accountId = $value;
	}
	
	function GetEmail()
	{
		return $this->email;
	}
	function SetEmail($value)
	{
		$this->email = $value;
	}
	
	function GetPassword()
	{
		return $this->password;
	}
	function SetPassword($value)
	{
		$this->password = $value;
	}
	
	function GetBalance()
	{
		return $this->balance;
	}
	function SetBalance($value)
	{
		$this->balance = $value;
	}
	
	function GetCreationTime()
	{
		return $this->creationTime;
	}
	function SetCreationTime($value)
	{
		$this->creationTime = $value;
	}
	

	function Account($Email, $Password, $Balance)
	{
		$this->accountId = 0;
	
		$this->email = $Email;
		$this->password = $Password;
		$this->balance = $Balance;
	}

}
?>

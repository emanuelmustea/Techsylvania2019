<?php
//generated automatically
require_once 'Categorie.php';
require_once 'Account.php';
class Transaction
{
	var $transactionId;
	var $accountId;
	var $categorieId;
	var $name;
	var $value;
	var $creationTime;
	var $categorie;
	var $account;

	function GetTransactionId()
	{
		return $this->transactionId;
	}
	function SetTransactionId($value)
	{
		$this->transactionId = $value;
	}
	
	function GetAccountId()
	{
		return $this->accountId;
	}
	function SetAccountId($value)
	{
		$this->accountId = $value;
	}
	
	function GetCategorieId()
	{
		return $this->categorieId;
	}
	function SetCategorieId($value)
	{
		$this->categorieId = $value;
	}
	
	function GetName()
	{
		return $this->name;
	}
	function SetName($value)
	{
		$this->name = $value;
	}
	
	function GetValue()
	{
		return $this->value;
	}
	function SetValue($value)
	{
		$this->value = $value;
	}
	
	function GetCreationTime()
	{
		return $this->creationTime;
	}
	function SetCreationTime($value)
	{
		$this->creationTime = $value;
	}
	
	function GetCategorie()
	{
		return $this->categorie;
	}
	function SetCategorie($value)
	{
		$this->categorie = $value;
	}
	
	function GetAccount()
	{
		return $this->account;
	}
	function SetAccount($value)
	{
		$this->account = $value;
	}
	

	function Transaction($AccountId, $CategorieId, $Name, $Value)
	{
		$this->transactionId = 0;
	
		$this->accountId = $AccountId;
		$this->categorieId = $CategorieId;
		$this->name = $Name;
		$this->value = $Value;
	}

}
?>

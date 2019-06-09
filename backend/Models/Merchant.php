<?php
//generated automatically
class Merchant
{
	var $merchantId;
	var $name;
	var $creationTime;

	function GetMerchantId()
	{
		return $this->merchantId;
	}
	function SetMerchantId($value)
	{
		$this->merchantId = $value;
	}
	
	function GetName()
	{
		return $this->name;
	}
	function SetName($value)
	{
		$this->name = $value;
	}
	
	function GetCreationTime()
	{
		return $this->creationTime;
	}
	function SetCreationTime($value)
	{
		$this->creationTime = $value;
	}
	

	function Merchant($Name)
	{
		$this->merchantId = 0;
	
		$this->name = $Name;
	}

}
?>

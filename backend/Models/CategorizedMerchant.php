<?php
//generated automatically
require_once 'Merchant.php';
require_once 'Categorie.php';
class CategorizedMerchant
{
	var $categorizedMerchantId;
	var $categorieId;
	var $merchantId;
	var $creationTime;
	var $merchant;
	var $categorie;

	function GetCategorizedMerchantId()
	{
		return $this->categorizedMerchantId;
	}
	function SetCategorizedMerchantId($value)
	{
		$this->categorizedMerchantId = $value;
	}
	
	function GetCategorieId()
	{
		return $this->categorieId;
	}
	function SetCategorieId($value)
	{
		$this->categorieId = $value;
	}
	
	function GetMerchantId()
	{
		return $this->merchantId;
	}
	function SetMerchantId($value)
	{
		$this->merchantId = $value;
	}
	
	function GetCreationTime()
	{
		return $this->creationTime;
	}
	function SetCreationTime($value)
	{
		$this->creationTime = $value;
	}
	
	function GetMerchant()
	{
		return $this->merchant;
	}
	function SetMerchant($value)
	{
		$this->merchant = $value;
	}
	
	function GetCategorie()
	{
		return $this->categorie;
	}
	function SetCategorie($value)
	{
		$this->categorie = $value;
	}
	

	function CategorizedMerchant($CategorieId, $MerchantId)
	{
		$this->categorizedMerchantId = 0;
	
		$this->categorieId = $CategorieId;
		$this->merchantId = $MerchantId;
	}

}
?>

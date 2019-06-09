<?php
//generated automatically
class Categorie
{
	var $categorieId;
	var $name;
	var $creationTime;

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
	
	function GetCreationTime()
	{
		return $this->creationTime;
	}
	function SetCreationTime($value)
	{
		$this->creationTime = $value;
	}
	

	function Categorie($Name)
	{
		$this->categorieId = 0;
	
		$this->name = $Name;
	}

}
?>

<?php
header('Access-Control-Allow-Origin: *'); 
header('Access-Control-Allow-Headers: *'); 
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
$_POST = json_decode(file_get_contents('php://input'), true);
require_once 'Models/Categorie.php';
require_once 'DatabaseOperations.php';
require_once 'Helpers.php';
function ConvertListToCategories($data)
{
	$categories = [];
	
	foreach($data as $row)
	{
		$categorie = new Categorie(
		$row["Name"] 
		);
	
		$categorie->SetCategorieId($row["CategorieId"]);
		$categorie->SetCreationTime($row["CreationTime"]);
	
		$categories[count($categories)] = $categorie;
	}
	
	return $categories;
}

function GetCategories($database)
{
	$data = $database->ReadData("SELECT * FROM Categories");
	$categories = ConvertListToCategories($data);
	return $categories;
}

function GetCategoriesByCategorieId($database, $categorieId)
{
	$data = $database->ReadData("SELECT * FROM Categories WHERE CategorieId = $categorieId");
	$categories = ConvertListToCategories($data);
	if(0== count($categories))
	{
		return [GetEmptyCategorie()];
	}
	return $categories;
}

function CompleteCategories($database, $categories)
{
	$categoriesList = GetCategories($database);
	foreach($categories as $categorie)
	{
		$start = 0;
		$end = count($categoriesList) - 1;
		do
		{
	
			$mid = floor(($start + $end) / 2);
			if($categorie->GetCategorieId() > $categoriesList[$mid]->GetCategorieId())
			{
				$start = $mid + 1;
			}
			else if($categorie->GetCategorieId() < $categoriesList[$mid]->GetCategorieId())
			{
				$end = $mid - 1;
			}
			else if($categorie->GetCategorieId() == $categoriesList[$mid]->GetCategorieId())
			{
				$start = $mid + 1;
				$end = $mid - 1;
				$categorie->SetCategorie($categoriesList[$mid]);
			}
	
		}while($start <= $end);
	}
	
	return $categories;
}

function AddCategorie($database, $categorie)
{
	$query = "INSERT INTO Categories(Name, CreationTime) VALUES(";
	$query = $query . "'" . mysqli_real_escape_string($database->connection ,$categorie->GetName()) . "', ";
	$query = $query . "NOW()"."";
	
	$query = $query . ");";
	$database->ExecuteSqlWithoutWarning($query);
	$id = $database->GetLastInsertedId();
	$categorie->SetCategorieId($id);
	$categorie->SetCreationTime(date('Y-m-d H:i:s'));
	return $categorie;
	
}

function DeleteCategorie($database, $categorieId)
{
	$categorie = GetCategoriesByCategorieId($database, $categorieId)[0];
	
	$query = "DELETE FROM Categories WHERE CategorieId=$categorieId";
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	
	if(0 != $result)
	{
		$categorie->SetCategorieId(0);
	}
	
	return $categorie;
	
}

function UpdateCategorie($database, $categorie)
{
	$query = "UPDATE Categories SET ";
	$query = $query . "Name='" . $categorie->GetName() . "'";
	$query = $query . " WHERE CategorieId=" . $categorie->GetCategorieId();
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	if(0 == $result)
	{
		$categorie->SetCategorieId(0);
	}
	return $categorie;
	
}

function TestAddCategorie($database)
{
	$categorie = new Categorie(
		'Test'//Name
	);
	
	AddCategorie($database, $categorie);
}

function GetEmptyCategorie()
{
	$categorie = new Categorie(
		''//Name
	);
	
	return $categorie;
}

if(CheckGetParameters(["cmd"]))
{
	if("getCategories" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetCategories($database));
	}

	if("getLastCategorie" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetLastCategorie($database));
	}
	
	else if("getByValue" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo GetCategorieAmount($database);
	}

	else if("getCategoriesByCategorieId" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'categorieId'
			]))
		{
			$database = new DatabaseOperations();
			echo json_encode(GetCategoriesByCategorieId($database, 
				$_GET["categorieId"]
			));
		}
	
	}

	else if("addCategorie" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'name'
		]))
		{
			$database = new DatabaseOperations();
			$categorie = new Categorie(
				$_GET['name']
			);
		
			echo json_encode(AddCategorie($database, $categorie));
		}
	
	}

}

if(CheckGetParameters(["cmd"]))
{
	if("addCategorie" == $_GET["cmd"])
	{
		if(CheckPostParameters([
			'name'
		]))
		{
			$database = new DatabaseOperations();
			$categorie = new Categorie(
				$_POST['name']
			);
	
			echo json_encode(AddCategorie($database, $categorie));
		}

	}
}

if(CheckGetParameters(["cmd"]))
{
	if("updateCategorie" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$categorie = new Categorie(
			$_POST['name']
		);
		$categorie->SetCategorieId($_POST['categorieId']);
		$categorie->SetCreationTime($_POST['creationTime']);
		
		$categorie = UpdateCategorie($database, $categorie);
		echo json_encode($categorie);

	}
}

if("DELETE" == $_SERVER['REQUEST_METHOD']
	&& CheckGetParameters(["cmd"]))
{
	if("deleteCategorie" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$categorieId = $_GET['categorieId'];
		
		$categorie = DeleteCategorie($database, $categorieId);
		echo json_encode($categorie);

	}
}


function GetLastCategorie($database)
{
	$data = $database->ReadData("SELECT * FROM Categories ORDER BY CreationTime DESC LIMIT 1");
	$categories = ConvertListToCategories($data);
	return $categories;
}

function GetCategorieAmount($database)
{
	$data = $database->ReadData("SELECT categorieId,name,creationTime,IFNULL((select sum(value) from transactions where transactions.CategorieId=Categories.CategorieId), 0) as value FROM Categories");
	//$categories = ConvertListToCategories($data);
	
//var_dump(json_encode($categories));
	return json_encode($data);
}

function GetCategorieAmountData($database)
{
	$data = $database->ReadData("SELECT categorieId,name,creationTime,IFNULL((select sum(value) from transactions where transactions.CategorieId=Categories.CategorieId), 0) as value FROM Categories");
	//$categories = ConvertListToCategories($data);
	
//var_dump(json_encode($categories));
	return ($data);
}

?>

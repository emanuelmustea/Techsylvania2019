<?php
header('Access-Control-Allow-Origin: *'); 
header('Access-Control-Allow-Headers: *'); 
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
$_POST = json_decode(file_get_contents('php://input'), true);
require_once 'Models/CategorizedMerchant.php';
require_once 'DatabaseOperations.php';
require_once 'Helpers.php';
require_once 'Merchants.php';
require_once 'Categories.php';
function ConvertListToCategorizedMerchants($data)
{
	$categorizedMerchants = [];
	
	foreach($data as $row)
	{
		$categorizedMerchant = new CategorizedMerchant(
		$row["CategorieId"], 
		$row["MerchantId"] 
		);
	
		$categorizedMerchant->SetCategorizedMerchantId($row["CategorizedMerchantId"]);
		$categorizedMerchant->SetCreationTime($row["CreationTime"]);
	
		$categorizedMerchants[count($categorizedMerchants)] = $categorizedMerchant;
	}
	
	return $categorizedMerchants;
}

function GetCategorizedMerchants($database)
{
	$data = $database->ReadData("SELECT * FROM CategorizedMerchants");
	$categorizedMerchants = ConvertListToCategorizedMerchants($data);
	$categorizedMerchants = CompleteMerchants($database, $categorizedMerchants);
	$categorizedMerchants = CompleteCategories($database, $categorizedMerchants);
	return $categorizedMerchants;
}

function GetCategorizedMerchantsByCategorizedMerchantId($database, $categorizedMerchantId)
{
	$data = $database->ReadData("SELECT * FROM CategorizedMerchants WHERE CategorizedMerchantId = $categorizedMerchantId");
	$categorizedMerchants = ConvertListToCategorizedMerchants($data);
	if(0== count($categorizedMerchants))
	{
		return [GetEmptyCategorizedMerchant()];
	}
	CompleteMerchants($database, $categorizedMerchants);
	CompleteCategories($database, $categorizedMerchants);
	return $categorizedMerchants;
}

function CompleteCategorizedMerchants($database, $categorizedMerchants)
{
	$categorizedMerchantsList = GetCategorizedMerchants($database);
	foreach($categorizedMerchants as $categorizedMerchant)
	{
		$start = 0;
		$end = count($categorizedMerchantsList) - 1;
		do
		{
	
			$mid = floor(($start + $end) / 2);
			if($categorizedMerchant->GetCategorizedMerchantId() > $categorizedMerchantsList[$mid]->GetCategorizedMerchantId())
			{
				$start = $mid + 1;
			}
			else if($categorizedMerchant->GetCategorizedMerchantId() < $categorizedMerchantsList[$mid]->GetCategorizedMerchantId())
			{
				$end = $mid - 1;
			}
			else if($categorizedMerchant->GetCategorizedMerchantId() == $categorizedMerchantsList[$mid]->GetCategorizedMerchantId())
			{
				$start = $mid + 1;
				$end = $mid - 1;
				$categorizedMerchant->SetCategorizedMerchant($categorizedMerchantsList[$mid]);
			}
	
		}while($start <= $end);
	}
	
	return $categorizedMerchants;
}

function AddCategorizedMerchant($database, $categorizedMerchant)
{
	$query = "INSERT INTO CategorizedMerchants(CategorieId, MerchantId, CreationTime) VALUES(";
	$query = $query . mysqli_real_escape_string($database->connection ,$categorizedMerchant->GetCategorieId()).", ";
	$query = $query . mysqli_real_escape_string($database->connection ,$categorizedMerchant->GetMerchantId()).", ";
	$query = $query . "NOW()"."";
	
	$query = $query . ");";
	$database->ExecuteSqlWithoutWarning($query);
	$id = $database->GetLastInsertedId();
	$categorizedMerchant->SetCategorizedMerchantId($id);
	$categorizedMerchant->SetCreationTime(date('Y-m-d H:i:s'));
	$categorizedMerchant->SetMerchant(GetMerchantsByMerchantId($database, $categorizedMerchant->GetMerchantId())[0]);
	$categorizedMerchant->SetCategorie(GetCategoriesByCategorieId($database, $categorizedMerchant->GetCategorieId())[0]);
	return $categorizedMerchant;
	
}

function DeleteCategorizedMerchant($database, $categorizedMerchantId)
{
	$categorizedMerchant = GetCategorizedMerchantsByCategorizedMerchantId($database, $categorizedMerchantId)[0];
	
	$query = "DELETE FROM CategorizedMerchants WHERE CategorizedMerchantId=$categorizedMerchantId";
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	
	if(0 != $result)
	{
		$categorizedMerchant->SetCategorizedMerchantId(0);
	}
	
	return $categorizedMerchant;
	
}

function UpdateCategorizedMerchant($database, $categorizedMerchant)
{
	$query = "UPDATE CategorizedMerchants SET ";
	$query = $query . "CategorieId=" . $categorizedMerchant->GetCategorieId().", ";
	$query = $query . "MerchantId=" . $categorizedMerchant->GetMerchantId()."";
	$query = $query . " WHERE CategorizedMerchantId=" . $categorizedMerchant->GetCategorizedMerchantId();
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	if(0 == $result)
	{
		$categorizedMerchant->SetCategorizedMerchantId(0);
	}
	return $categorizedMerchant;
	
}

function TestAddCategorizedMerchant($database)
{
	$categorizedMerchant = new CategorizedMerchant(
		0,//CategorieId
		0//MerchantId
	);
	
	AddCategorizedMerchant($database, $categorizedMerchant);
}

function GetEmptyCategorizedMerchant()
{
	$categorizedMerchant = new CategorizedMerchant(
		0,//CategorieId
		0//MerchantId
	);
	
	return $categorizedMerchant;
}

if(CheckGetParameters(["cmd"]))
{
	if("getCategorizedMerchants" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetCategorizedMerchants($database));
	}

	if("getLastCategorizedMerchant" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetLastCategorizedMerchant($database));
	}

	else if("getCategorizedMerchantsByCategorizedMerchantId" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'categorizedMerchantId'
			]))
		{
			$database = new DatabaseOperations();
			echo json_encode(GetCategorizedMerchantsByCategorizedMerchantId($database, 
				$_GET["categorizedMerchantId"]
			));
		}
	
	}

	else if("addCategorizedMerchant" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'categorieId',
			'merchantId'
		]))
		{
			$database = new DatabaseOperations();
			$categorizedMerchant = new CategorizedMerchant(
				$_GET['categorieId'],
				$_GET['merchantId']
			);
		
			echo json_encode(AddCategorizedMerchant($database, $categorizedMerchant));
		}
	
	}

}

if(CheckGetParameters(["cmd"]))
{
	if("addCategorizedMerchant" == $_GET["cmd"])
	{
		if(CheckPostParameters([
			'categorieId',
			'merchantId'
		]))
		{
			$database = new DatabaseOperations();
			$categorizedMerchant = new CategorizedMerchant(
				$_POST['categorieId'],
				$_POST['merchantId']
			);
	
			echo json_encode(AddCategorizedMerchant($database, $categorizedMerchant));
		}

	}
}

if(CheckGetParameters(["cmd"]))
{
	if("updateCategorizedMerchant" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$categorizedMerchant = new CategorizedMerchant(
			$_POST['categorieId'],
			$_POST['merchantId']
		);
		$categorizedMerchant->SetCategorizedMerchantId($_POST['categorizedMerchantId']);
		$categorizedMerchant->SetCreationTime($_POST['creationTime']);
		
		$categorizedMerchant = UpdateCategorizedMerchant($database, $categorizedMerchant);
		echo json_encode($categorizedMerchant);

	}
}

if("DELETE" == $_SERVER['REQUEST_METHOD']
	&& CheckGetParameters(["cmd"]))
{
	if("deleteCategorizedMerchant" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$categorizedMerchantId = $_GET['categorizedMerchantId'];
		
		$categorizedMerchant = DeleteCategorizedMerchant($database, $categorizedMerchantId);
		echo json_encode($categorizedMerchant);

	}
}


function GetLastCategorizedMerchant($database)
{
	$data = $database->ReadData("SELECT * FROM CategorizedMerchants ORDER BY CreationTime DESC LIMIT 1");
	$categorizedMerchants = ConvertListToCategorizedMerchants($data);
	$categorizedMerchants = CompleteMerchants($database, $categorizedMerchants);
	$categorizedMerchants = CompleteCategories($database, $categorizedMerchants);
	return $categorizedMerchants;
}

?>

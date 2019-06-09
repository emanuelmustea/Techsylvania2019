<?php
header('Access-Control-Allow-Origin: *'); 
header('Access-Control-Allow-Headers: *'); 
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
$_POST = json_decode(file_get_contents('php://input'), true);
require_once 'Models/Account.php';
require_once 'DatabaseOperations.php';
require_once 'Helpers.php';
function ConvertListToAccounts($data)
{
	$accounts = [];
	
	foreach($data as $row)
	{
		$account = new Account(
		$row["Email"], 
		$row["Password"], 
		$row["Balance"] 
		);
	
		$account->SetAccountId($row["AccountId"]);
		$account->SetCreationTime($row["CreationTime"]);
	
		$accounts[count($accounts)] = $account;
	}
	
	return $accounts;
}

function GetAccounts($database)
{
	$data = $database->ReadData("SELECT * FROM Accounts");
	$accounts = ConvertListToAccounts($data);
	return $accounts;
}

function GetAccountsByEmailPassword($database, $email, $password)
{
	$data = $database->ReadData("SELECT * FROM Accounts WHERE Email = '$email' and Password = '$password'");
	$accounts = ConvertListToAccounts($data);
	if(0== count($accounts))
	{
		return [GetEmptyAccount()];
	}
	return $accounts;
}
function GetAccountsByAccountId($database, $accountId)
{
	$data = $database->ReadData("SELECT * FROM Accounts WHERE AccountId = $accountId");
	$accounts = ConvertListToAccounts($data);
	if(0== count($accounts))
	{
		return [GetEmptyAccount()];
	}
	return $accounts;
}

function CompleteAccounts($database, $accounts)
{
	$accountsList = GetAccounts($database);
	foreach($accounts as $account)
	{
		$start = 0;
		$end = count($accountsList) - 1;
		do
		{
	
			$mid = floor(($start + $end) / 2);
			if($account->GetAccountId() > $accountsList[$mid]->GetAccountId())
			{
				$start = $mid + 1;
			}
			else if($account->GetAccountId() < $accountsList[$mid]->GetAccountId())
			{
				$end = $mid - 1;
			}
			else if($account->GetAccountId() == $accountsList[$mid]->GetAccountId())
			{
				$start = $mid + 1;
				$end = $mid - 1;
				$account->SetAccount($accountsList[$mid]);
			}
	
		}while($start <= $end);
	}
	
	return $accounts;
}

function AddAccount($database, $account)
{
	$query = "INSERT INTO Accounts(Email, Password, Balance, CreationTime) VALUES(";
	$query = $query . "'" . mysqli_real_escape_string($database->connection ,$account->GetEmail()) . "', ";
	$query = $query . "'" . mysqli_real_escape_string($database->connection ,$account->GetPassword()) . "', ";
	$query = $query . "'" . mysqli_real_escape_string($database->connection ,$account->GetBalance()) . "', ";
	$query = $query . "NOW()"."";
	
	$query = $query . ");";
	$database->ExecuteSqlWithoutWarning($query);
	$id = $database->GetLastInsertedId();
	$account->SetAccountId($id);
	$account->SetCreationTime(date('Y-m-d H:i:s'));
	return $account;
	
}

function DeleteAccount($database, $accountId)
{
	$account = GetAccountsByAccountId($database, $accountId)[0];
	
	$query = "DELETE FROM Accounts WHERE AccountId=$accountId";
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	
	if(0 != $result)
	{
		$account->SetAccountId(0);
	}
	
	return $account;
	
}

function UpdateAccount($database, $account)
{
	$query = "UPDATE Accounts SET ";
	$query = $query . "Email='" . $account->GetEmail() . "', ";
	$query = $query . "Password='" . $account->GetPassword() . "', ";
	$query = $query . "Balance='" . $account->GetBalance() . "'";
	$query = $query . " WHERE AccountId=" . $account->GetAccountId();
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	if(0 == $result)
	{
		$account->SetAccountId(0);
	}
	return $account;
	
}

function TestAddAccount($database)
{
	$account = new Account(
		'Test',//Email
		'Test',//Password
		0//Balance
	);
	
	AddAccount($database, $account);
}

function GetEmptyAccount()
{
	$account = new Account(
		'',//Email
		'',//Password
		0//Balance
	);
	
	return $account;
}

if(CheckGetParameters(["cmd"]))
{
	if("getAccounts" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetAccounts($database));
	}

	if("getLastAccount" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetLastAccount($database));
	}

	else if("getAccountsByEmailPassword" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'email',
			'password'
			]))
		{
			$database = new DatabaseOperations();
			echo json_encode(GetAccountsByEmailPassword($database, 
				$_GET["email"],
				$_GET["password"]
			));
		}
	
	}
	else if("getAccountsByAccountId" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'accountId'
			]))
		{
			$database = new DatabaseOperations();
			echo json_encode(GetAccountsByAccountId($database, 
				$_GET["accountId"]
			));
		}
	
	}

	else if("addAccount" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'email',
			'password',
			'balance'
		]))
		{
			$database = new DatabaseOperations();
			$account = new Account(
				$_GET['email'],
				$_GET['password'],
				$_GET['balance']
			);
		
			echo json_encode(AddAccount($database, $account));
		}
	
	}

}

if(CheckGetParameters(["cmd"]))
{
	if("addAccount" == $_GET["cmd"])
	{
		if(CheckPostParameters([
			'email',
			'password',
			'balance'
		]))
		{
			$database = new DatabaseOperations();
			$account = new Account(
				$_POST['email'],
				$_POST['password'],
				$_POST['balance']
			);
	
			echo json_encode(AddAccount($database, $account));
		}

	}
}

if(CheckGetParameters(["cmd"]))
{
	if("updateAccount" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$account = new Account(
			$_POST['email'],
			$_POST['password'],
			$_POST['balance']
		);
		$account->SetAccountId($_POST['accountId']);
		$account->SetCreationTime($_POST['creationTime']);
		
		$account = UpdateAccount($database, $account);
		echo json_encode($account);

	}
}

if("DELETE" == $_SERVER['REQUEST_METHOD']
	&& CheckGetParameters(["cmd"]))
{
	if("deleteAccount" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$accountId = $_GET['accountId'];
		
		$account = DeleteAccount($database, $accountId);
		echo json_encode($account);

	}
}


function GetLastAccount($database)
{
	$data = $database->ReadData("SELECT * FROM Accounts ORDER BY CreationTime DESC LIMIT 1");
	$accounts = ConvertListToAccounts($data);
	return $accounts;
}

?>

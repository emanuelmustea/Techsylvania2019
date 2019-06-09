<?php
header('Access-Control-Allow-Origin: *'); 
header('Access-Control-Allow-Headers: *'); 
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
$_POST = json_decode(file_get_contents('php://input'), true);
require_once 'Models/Transaction.php';
require_once 'Models/Merchant.php';
require_once 'DatabaseOperations.php';
require_once 'Helpers.php';
require_once 'Categories.php';
require_once 'Accounts.php';
require_once 'Notifications.php';
require_once 'Messages.php';
require_once 'CategorizedMerchants.php';
function ConvertListToTransactions($data)
{
	$transactions = [];
	
	foreach($data as $row)
	{
		$transaction = new Transaction(
		$row["AccountId"], 
		$row["CategorieId"], 
		$row["Name"], 
		$row["Value"] 
		);
	
		$transaction->SetTransactionId($row["TransactionId"]);
		$transaction->SetCreationTime($row["CreationTime"]);
	
		$transactions[count($transactions)] = $transaction;
	}
	
	return $transactions;
}

function GetTransactions($database)
{
	$data = $database->ReadData("SELECT * FROM Transactions");
	$transactions = ConvertListToTransactions($data);
	foreach($transactions as $transaction)
	{
		if($transaction->GetCategorieId() == 0)
		{
			$transaction->SetCategorieId(FindCategory($database, $transaction));
		}
	}
	$transactions = CompleteCategories($database, $transactions);
	$transactions = CompleteAccounts($database, $transactions);
	return $transactions;
}

function GetTransactionsByTransactionId($database, $transactionId)
{
	$data = $database->ReadData("SELECT * FROM Transactions WHERE TransactionId = $transactionId");
	$transactions = ConvertListToTransactions($data);
	if(0== count($transactions))
	{
		return [GetEmptyTransaction()];
	}
	CompleteCategories($database, $transactions);
	CompleteAccounts($database, $transactions);
	return $transactions;
}

function CompleteTransactions($database, $transactions)
{
	$transactionsList = GetTransactions($database);
	foreach($transactions as $transaction)
	{
		$start = 0;
		$end = count($transactionsList) - 1;
		do
		{
	
			$mid = floor(($start + $end) / 2);
			if($transaction->GetTransactionId() > $transactionsList[$mid]->GetTransactionId())
			{
				$start = $mid + 1;
			}
			else if($transaction->GetTransactionId() < $transactionsList[$mid]->GetTransactionId())
			{
				$end = $mid - 1;
			}
			else if($transaction->GetTransactionId() == $transactionsList[$mid]->GetTransactionId())
			{
				$start = $mid + 1;
				$end = $mid - 1;
				$transaction->SetTransaction($transactionsList[$mid]);
			}
	
		}while($start <= $end);
	}
	
	return $transactions;
}

function FindCategory($database, $transaction)
{
	$merchants = GetMerchants($database);
		$gasit = false;
		$merchantId = 0;
		foreach($merchants as $merchant)
		{
			if($merchant->GetName() == $transaction->GetName())
			{
				$gasit = true;
				$merchantId = $merchant->GetMerchantId();
				break;
			}
		}
		
		if(!$gasit)
		{
			$merchant = new Merchant(
				$transaction->GetName()
			);
			$merchant = AddMerchant($database, $merchant);
			$merchantId = $merchant->GetMerchantId();
		}
		
		$categorizedMerchants = GetCategorizedMerchants($database);
		$gasit = false;
		$categorizedMerchantId = 0;
		foreach($categorizedMerchants as $categorizedMerchant)
		{
			if($categorizedMerchant->GetMerchantId() == $merchant->GetMerchantId())
			{
				$gasit = true;
				$categorizedMerchantId = $categorizedMerchant->GetCategorizedMerchantId();
				$transaction->SetCategorieId($categorizedMerchant->GetCategorieId());
				UpdateTransaction($database, $transaction);
				return $categorizedMerchant->GetCategorieId();
				break;
			}
		}
		
		return 0;
}

function AddTransaction($database, $transaction)
{
	if($transaction->GetCategorieId() == 0)
	{
		$merchants = GetMerchants($database);
		$gasit = false;
		$merchantId = 0;
		foreach($merchants as $merchant)
		{
			if($merchant->GetName() == $transaction->GetName())
			{
				$gasit = true;
				$merchantId = $merchant->GetMerchantId();
				break;
			}
		}
		
		if(!$gasit)
		{
			$merchant = new Merchant(
				$transaction->GetName()
			);
			$merchant = AddMerchant($database, $merchant);
			$merchantId = $merchant->GetMerchantId();
		}
		
		$categorizedMerchants = GetCategorizedMerchants($database);
		$gasit = false;
		$categorizedMerchantId = 0;
		foreach($categorizedMerchants as $categorizedMerchant)
		{
			if($categorizedMerchant->GetMerchantId() == $merchant->GetMerchantId())
			{
				$gasit = true;
				$categorizedMerchantId = $categorizedMerchant->GetCategorizedMerchantId();
				$transaction->SetCategorieId($categorizedMerchant->GetCategorieId());
				break;
			}
		}
		
		if(!$gasit)
		{
			$message = new Message(
				"I observed that you recently have made a transactions with ".$transaction->GetName().". Should i add this to leisure category?",//Content
				2//Source
			);
			
			AddMessage($database, $message);
			
			$notification = new Notification(
				'New Transaction',//Title
				"I observed that you recently have made a transactions with ".$transaction->GetName().". Should i add this to leisure category?"//Message
			);
			
			AddNotification($database, $notification);
		}
		
		
	}
	
	
	$query = "INSERT INTO Transactions(AccountId, CategorieId, Name, Value, CreationTime) VALUES(";
	$query = $query . mysqli_real_escape_string($database->connection ,$transaction->GetAccountId()).", ";
	$query = $query . mysqli_real_escape_string($database->connection ,$transaction->GetCategorieId()).", ";
	$query = $query . "'" . mysqli_real_escape_string($database->connection ,$transaction->GetName()) . "', ";
	$query = $query . "'" . mysqli_real_escape_string($database->connection ,$transaction->GetValue()) . "', ";
	$query = $query . "NOW()"."";
	
	$query = $query . ");";
	$database->ExecuteSqlWithoutWarning($query);
	$id = $database->GetLastInsertedId();
	$transaction->SetTransactionId($id);
	$transaction->SetCreationTime(date('Y-m-d H:i:s'));
	$transaction->SetCategorie(GetCategoriesByCategorieId($database, $transaction->GetCategorieId())[0]);
	$transaction->SetAccount(GetAccountsByAccountId($database, $transaction->GetAccountId())[0]);

	if($transaction->GetCategorie()->GetName()=="Tech")
	{
		$msg = "I observed that you recently have made a transactions in tech category. We have a voucher at Altex. Are you interested?";
		$message = new Message(
			$msg,//Content
			4//Source
		);
		
		AddMessage($database, $message);
		
		$notification = new Notification(
			'Voucher',//Title
			$msg//Message
		);
		
		AddNotification($database, $notification);
	}
	else if($transaction->GetCategorie()->GetName()=="Cloathing")
	{
		AlertAboutSpending($database);
	}

	return $transaction;
	
}

function AlertAboutSpending($database)
{
	$categories = GetCategorieAmountData($database);
	//cloathing
	$income = 2000;
	$cloathing= 300;
	foreach($categories as $category)
	{
		if($category["name"]=="Salary")
		{
			$income = $category["value"];
		}
		else if($category["name"]=="Cloathing")
		{
			$cloathing = $category["value"];
		}
	}
	$percent = $cloathing*100/($income*(-1));
	$msg = "People with your income usually spends around 5% of money for Cloathing. You spend $cloathing which is $percent .";
	$message = new Message(
		$msg,//Content
		99//Source
	);
	
	AddMessage($database, $message);
	
	$notification = new Notification(
		'Alert',//Title
		$msg//Message
	);
	
	AddNotification($database, $notification);
	return GetLastTransaction($database);
}

function DeleteTransaction($database, $transactionId)
{
	$transaction = GetTransactionsByTransactionId($database, $transactionId)[0];
	
	$query = "DELETE FROM Transactions WHERE TransactionId=$transactionId";
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	
	if(0 != $result)
	{
		$transaction->SetTransactionId(0);
	}
	
	return $transaction;
	
}

function UpdateTransaction($database, $transaction)
{
	$query = "UPDATE Transactions SET ";
	$query = $query . "AccountId=" . $transaction->GetAccountId().", ";
	$query = $query . "CategorieId=" . $transaction->GetCategorieId().", ";
	$query = $query . "Name='" . $transaction->GetName() . "', ";
	$query = $query . "Value='" . $transaction->GetValue() . "'";
	$query = $query . " WHERE TransactionId=" . $transaction->GetTransactionId();
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	if(0 == $result)
	{
		$transaction->SetTransactionId(0);
	}
	return $transaction;
	
}

function TestAddTransaction($database)
{
	$transaction = new Transaction(
		0,//AccountId
		0,//CategorieId
		'Test',//Name
		0//Value
	);
	
	AddTransaction($database, $transaction);
}

function GetEmptyTransaction()
{
	$transaction = new Transaction(
		0,//AccountId
		0,//CategorieId
		'',//Name
		0//Value
	);
	
	return $transaction;
}

if(CheckGetParameters(["cmd"]))
{
	if("getTransactions" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetTransactions($database));
	}

	if("getSpendingAlerts" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(AlertAboutSpending($database));
	}

	if("getLastTransaction" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetLastTransaction($database));
	}

	else if("getTransactionValuePerDay" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetTransactionValuePerDay($database));
	}
	else if("getTransactionValuePerMonth" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetTransactionValuePerMonth($database));
	}

	else if("getTransactionsByTransactionId" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'transactionId'
			]))
		{
			$database = new DatabaseOperations();
			echo json_encode(GetTransactionsByTransactionId($database, 
				$_GET["transactionId"]
			));
		}
	
	}

	else if("addTransaction" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'accountId',
			'categorieId',
			'name',
			'value'
		]))
		{
			$database = new DatabaseOperations();
			$transaction = new Transaction(
				$_GET['accountId'],
				$_GET['categorieId'],
				$_GET['name'],
				$_GET['value']
			);
		
			echo json_encode(AddTransaction($database, $transaction));
		}
	
	}

}

if(CheckGetParameters(["cmd"]))
{
	if("addTransaction" == $_GET["cmd"])
	{
		if(CheckPostParameters([
			'accountId',
			'categorieId',
			'name',
			'value'
		]))
		{
			$database = new DatabaseOperations();
			$transaction = new Transaction(
				$_POST['accountId'],
				$_POST['categorieId'],
				$_POST['name'],
				$_POST['value']
			);
	
			echo json_encode(AddTransaction($database, $transaction));
		}

	}
}

if(CheckGetParameters(["cmd"]))
{
	if("updateTransaction" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$transaction = new Transaction(
			$_POST['accountId'],
			$_POST['categorieId'],
			$_POST['name'],
			$_POST['value']
		);
		$transaction->SetTransactionId($_POST['transactionId']);
		$transaction->SetCreationTime($_POST['creationTime']);
		
		$transaction = UpdateTransaction($database, $transaction);
		echo json_encode($transaction);

	}
}

if("DELETE" == $_SERVER['REQUEST_METHOD']
	&& CheckGetParameters(["cmd"]))
{
	if("deleteTransaction" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$transactionId = $_GET['transactionId'];
		
		$transaction = DeleteTransaction($database, $transactionId);
		echo json_encode($transaction);

	}
}


function GetLastTransaction($database)
{
	$data = $database->ReadData("SELECT * FROM Transactions ORDER BY CreationTime DESC LIMIT 1");
	$transactions = ConvertListToTransactions($data);
	$transactions = CompleteCategories($database, $transactions);
	$transactions = CompleteAccounts($database, $transactions);
	return $transactions;
}

function GetTransactionValuePerDay($database)
{
	$data = $database->ReadData("SELECT DISTINCT(DAY(CreationTime)) as Day, (SELECT SUM(Value) FROM transactions as tr WHERE DAY(tr.CreationTime)=DAY(transactions.CreationTime) and tr.value > 0) as Value FROM Transactions");
	return $data;
}

function GetTransactionValuePerMonth($database)
{
	$data = $database->ReadData("SELECT DISTINCT(MONTH(CreationTime)) as Month, (SELECT SUM(Value) FROM transactions as tr WHERE MONTH(tr.CreationTime)=MONTH(transactions.CreationTime) and tr.value > 0) as Value FROM Transactions");
	return $data;
}

?>

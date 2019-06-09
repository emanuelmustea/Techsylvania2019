<?php
header('Access-Control-Allow-Origin: *'); 
header('Access-Control-Allow-Headers: *'); 
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
$_POST = json_decode(file_get_contents('php://input'), true);
require_once 'Models/Message.php';
require_once 'DatabaseOperations.php';
require_once 'Merchants.php';
require_once 'Categories.php';
require_once 'CategorizedMerchants.php';
require_once 'Helpers.php';
function ConvertListToMessages($data)
{
	$messages = [];
	
	foreach($data as $row)
	{
		$message = new Message(
		$row["Content"], 
		$row["Source"] 
		);
	
		$message->SetMessageId($row["MessageId"]);
		$message->SetCreationTime($row["CreationTime"]);
	
		$messages[count($messages)] = $message;
	}
	
	return $messages;
}

function GetMessages($database)
{
	$data = $database->ReadData("SELECT * FROM Messages");
	$messages = ConvertListToMessages($data);
	return $messages;
}

function GetMessagesByMessageId($database, $messageId)
{
	$data = $database->ReadData("SELECT * FROM Messages WHERE MessageId = $messageId");
	$messages = ConvertListToMessages($data);
	if(0== count($messages))
	{
		return [GetEmptyMessage()];
	}
	return $messages;
}

function CompleteMessages($database, $messages)
{
	$messagesList = GetMessages($database);
	foreach($messages as $message)
	{
		$start = 0;
		$end = count($messagesList) - 1;
		do
		{
	
			$mid = floor(($start + $end) / 2);
			if($message->GetMessageId() > $messagesList[$mid]->GetMessageId())
			{
				$start = $mid + 1;
			}
			else if($message->GetMessageId() < $messagesList[$mid]->GetMessageId())
			{
				$end = $mid - 1;
			}
			else if($message->GetMessageId() == $messagesList[$mid]->GetMessageId())
			{
				$start = $mid + 1;
				$end = $mid - 1;
				$message->SetMessage($messagesList[$mid]);
			}
	
		}while($start <= $end);
	}
	
	return $messages;
}

function AddMessage($database, $message)
{
	$lastMessage = GetLastMessage($database);
	$responseMessage = NULL;
	
	if($message->GetSource()==0)
	{
		if($lastMessage[0]->GetSource() == 2 && strpos(strtolower($message->GetContent()), 'yes')!==false)
		{
			$parts = explode('. Should i add this to', $lastMessage[0]->GetContent());
			$merchantName = explode(' ', $parts[0])[10];
			$categoryName = explode(' ', $parts[1])[1];
			
			$merchants = GetMerchants($database);
			$gasit = false;
			$merchantId = 0;
			foreach($merchants as $merchant)
			{
				if(strtolower($merchant->GetName()) == strtolower($merchantName))
				{
					$gasit = true;
					$merchantId = $merchant->GetMerchantId();
					break;
				}
			}
			
			$categories = GetCategories($database);
			$gasitCategorie = false;
			$categoryId = 0;
			foreach($categories as $category)
			{
				if(strtolower($category->GetName()) == strtolower($categoryName))
				{
					$gasitCategorie = true;
					$categoryId = $category->GetCategorieId();
					break;
				}
			}
			if($gasit && $gasitCategorie)
			{
				$categorizedMerchant = new CategorizedMerchant(
					$categoryId,//CategorieId
					$merchantId//MerchantId
				);
				
				AddCategorizedMerchant($database, $categorizedMerchant);
				$responseMessage = new Message(
						$merchantName." was added in ".$categoryName.'!',//Content
						99//Source
					);
					GetCategories($database);	
			}
			
		}
		else if($lastMessage[0]->GetSource() == 2 && strpos(strtolower($message->GetContent()), 'yes')!==true)
		{
			$parts = explode('. Should i add this to', $lastMessage[0]->GetContent());
			$merchantName = explode(' ', $parts[0])[10];
			$categoryName = explode(' ', $parts[1])[1];
			$responseMessage = new Message(
				'Where should i add '.$merchantName,//Content
				3//Source
			);
		}
		else if($lastMessage[0]->GetSource() == 3)
		{
			$parts = explode(' ', $lastMessage[0]->GetContent());
			$merchantName = $parts[4];
			
			$categories = GetCategories($database);
			$gasitCategorie = false;
			$categoryId = 0;
			foreach($categories as $category)
			{
				if(strtolower($category->GetName()) == strtolower($message->GetContent()))
				{
					$gasitCategorie = true;
					$categoryId = $category->GetCategorieId();
					break;
				}
			}
			
			if(!$gasitCategorie)
			{
				$categorie = new Categorie(
					$message->GetContent()//Name
				);
				
				$categorie = AddCategorie($database, $categorie);
				$categoryId = $categorie->GetCategorieId();
			}
			
			$merchants = GetMerchants($database);
			$gasit = false;
			$merchantId = 0;
			foreach($merchants as $merchant)
			{
				if(strtolower($merchant->GetName()) == strtolower($merchantName))
				{
					$gasit = true;
					$merchantId = $merchant->GetMerchantId();
					
					$categorizedMerchant = new CategorizedMerchant(
						$categoryId,//CategorieId
						$merchantId//MerchantId
					);
					
					AddCategorizedMerchant($database, $categorizedMerchant);
					
					$responseMessage = new Message(
						"The product was added in ".$message->GetContent().'!',//Content
						99//Source
					);
					GetCategories($database);
					
					
				}
			}
		}
		else if($lastMessage[0]->GetSource() == 4 && strpos(strtolower($message->GetContent()), 'yes')!==false)
		{
			$responseMessage = new Message(
				"The voucher is RockFinance",//Content
				99//Source
			);
		}
	}
	
	$query = "INSERT INTO Messages(Content, Source, CreationTime) VALUES(";
	$query = $query . "'" . mysqli_real_escape_string($database->connection ,$message->GetContent()) . "', ";
	$query = $query . mysqli_real_escape_string($database->connection ,$message->GetSource()).", ";
	$query = $query . "NOW()"."";
	
	$query = $query . ");";
	$database->ExecuteSqlWithoutWarning($query);
	$id = $database->GetLastInsertedId();
	$message->SetMessageId($id);
	$message->SetCreationTime(date('Y-m-d H:i:s'));
	
	if($responseMessage != NULL)
		AddMessage($database, $responseMessage);
	
	return $message;
	
}

function DeleteMessage($database, $messageId)
{
	$message = GetMessagesByMessageId($database, $messageId)[0];
	
	$query = "DELETE FROM Messages WHERE MessageId=$messageId";
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	
	if(0 != $result)
	{
		$message->SetMessageId(0);
	}
	
	return $message;
	
}

function UpdateMessage($database, $message)
{
	$query = "UPDATE Messages SET ";
	$query = $query . "Content='" . $message->GetContent() . "', ";
	$query = $query . "Source=" . $message->GetSource()."";
	$query = $query . " WHERE MessageId=" . $message->GetMessageId();
	
	$result = $database->ExecuteSqlWithoutWarning($query);
	if(0 == $result)
	{
		$message->SetMessageId(0);
	}
	return $message;
	
}

function TestAddMessage($database)
{
	$message = new Message(
		'Test',//Content
		0//Source
	);
	
	AddMessage($database, $message);
}

function GetEmptyMessage()
{
	$message = new Message(
		'',//Content
		0//Source
	);
	
	return $message;
}

if(CheckGetParameters(["cmd"]))
{
	if("getMessages" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetMessages($database));
	}

	if("getLastMessage" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
			echo json_encode(GetLastMessage($database));
	}

	else if("getMessagesByMessageId" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'messageId'
			]))
		{
			$database = new DatabaseOperations();
			echo json_encode(GetMessagesByMessageId($database, 
				$_GET["messageId"]
			));
		}
	
	}

	else if("addMessage" == $_GET["cmd"])
	{
		if(CheckGetParameters([
			'content',
			'source'
		]))
		{
			$database = new DatabaseOperations();
			$message = new Message(
				$_GET['content'],
				$_GET['source']
			);
		
			echo json_encode(AddMessage($database, $message));
		}
	
	}

}

if(CheckGetParameters(["cmd"]))
{
	if("addMessage" == $_GET["cmd"])
	{
		if(CheckPostParameters([
			'content',
			'source'
		]))
		{
			$database = new DatabaseOperations();
			$message = new Message(
				$_POST['content'],
				$_POST['source']
			);
	
			echo json_encode(AddMessage($database, $message));
		}

	}
}

if(CheckGetParameters(["cmd"]))
{
	if("updateMessage" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$message = new Message(
			$_POST['content'],
			$_POST['source']
		);
		$message->SetMessageId($_POST['messageId']);
		$message->SetCreationTime($_POST['creationTime']);
		
		$message = UpdateMessage($database, $message);
		echo json_encode($message);

	}
}

if("DELETE" == $_SERVER['REQUEST_METHOD']
	&& CheckGetParameters(["cmd"]))
{
	if("deleteMessage" == $_GET["cmd"])
	{
		$database = new DatabaseOperations();
		$messageId = $_GET['messageId'];
		
		$message = DeleteMessage($database, $messageId);
		echo json_encode($message);

	}
}


function GetLastMessage($database)
{
	$data = $database->ReadData("SELECT * FROM Messages ORDER BY CreationTime DESC, MessageId DESC LIMIT 1");
	$messages = ConvertListToMessages($data);
	return $messages;
}

?>

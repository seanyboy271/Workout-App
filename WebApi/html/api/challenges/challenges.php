<?php

require_once(__DIR__ . '/../Database.php');

$conn = Database::getInstance();
header('Content-type: application/json ; charset=UTF-8');

//Post requests
if ($_SERVER['REQUEST_METHOD'] === 'POST') {


    $chalName = addslashes($_POST["chalName"]);
    $chalDesc = addslashes($_POST["chalDesc"]);
    $chalCreator = $_POST["chalCreator"];
    $chalStart = $_POST["chalStart"];
    $chalActivity = $_POST["chalActivity"];
    $chalEnd = $_POST["chalEnd"];
    $chalFail = $_POST["chalFail"];

    $sql = "INSERT INTO dbo.Challenges (Name, Description, Creator, Start, Fail, Activity, End) 
          VALUES ('{$chalName}', '{$chalDesc}', '{$chalCreator}',
     '{$chalStart}', {$chalFail}, '{$chalActivity}', '{$chalEnd}')";

    // Call a MySQL query
    // magic, be careful
    $result = $conn->execute($sql);


// Query for the Id
    $sql = 'SELECT Id FROM dbo.Challenges WHERE Name = \'' . $chalName . '\'';
    $result = $conn->query($sql);

// Fetch the results from the query's response in a readable array
    if ($row = $result->fetch(PDO::FETCH_ASSOC)) {
        // Return the JSON form of the result
        echo json_encode($row);
    } else {
        // Return a JSON fail message
        echo json_encode(array("success" => false));
    }
}

//GET REQUEST
//COMPARE TIME(NOW) TO CHALLENGE START DATE IN DATABASE
else {
	$datetime = date_create(null, timezone_open("America/New_York"))->format('Y-m-d H:i:s');
	

	$result = $conn->query('SELECT * FROM dbo.Challenges WHERE Start > \'' . $datetime . '\'');

        // Fetch the results from the query's response in a readable array
        if ($row = $result->fetchAll(PDO::FETCH_ASSOC)) {
            // Return the JSON form of the result
            echo json_encode($row);
        } else {
            // Return a JSON fail message
            echo json_encode(array("success" => false));
        }

	
}

 
	
      








?>
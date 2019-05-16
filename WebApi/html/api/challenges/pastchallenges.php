<?php

require_once(__DIR__ . '/../Database.php');

$conn = Database::getInstance();
header('Content-type: application/json ; charset=UTF-8');

//COMPARE
if ($_SERVER['REQUEST_METHOD']==='GET'){
	
	$datetime = date_create()->format('Y-m-d H:i:s');
	$result = $conn->query('SELECT * FROM dbo.Challenges WHERE End < \'' . $datetime . '\'');

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
<?php

require_once(__DIR__ . '/../Database.php');

$conn = Database::getInstance();
header('Content-type: application/json ; charset=UTF-8');

//COMPARE
if ($_SERVER['REQUEST_METHOD']==='GET'){

	$userId = $_GET["userId"];
	$datetime = date_create()->format('Y-m-d H:i:s');

	
 	if(isset($userId)){
		//$result = $conn->query('SELECT * FROM dbo.Log WHERE EndDate > \'' . $datetime . '\' AND StartDate > \'' . $datetime . '\' AND UserId = \'' . $userId . '\' AND Result IS NULL');
		$result = $conn->query('SELECT * FROM dbo.Challenges AS c INNER JOIN (
                  SELECT * FROM dbo.Log AS l
                  WHERE l.UserId = ' . $userId . ' AND Result IS NULL) AS ch
                   ON c.Id = ch.ChaId
                    AND c.Start > NOW()');

        // Fetch the results from the query's response in a readable array
        if ($row = $result->fetchAll(PDO::FETCH_ASSOC)) {
            // Return the JSON form of the result
            echo json_encode($row);
        } else {
            // Return a JSON fail message
            echo json_encode(array("success" => false));
        }
    }
	
}

?>
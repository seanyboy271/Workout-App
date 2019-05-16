<?php

require_once(__DIR__ . '/../Database.php');

// Get the instance from the Database singleton
$conn = Database::getInstance();

header('Content-type: application/json');

// If this is a POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    
 
    $userId = $_POST["userId"];
    $datetime = date_create()->format('Y-m-d H:i:s');
    $chaId =$_POST["chaId"];
    $result = $_POST["result"];

    $sql ="UPDATE dbo.Log SET Result='$result', LogDate='$datetime' WHERE UserId ='$userId' AND ChaId='$chaId'";

    // Call a MySQL query
    // magic, be careful
    $result = $conn->query($sql);

    $sql = 'SELECT Id FROM dbo.Log WHERE UserId = \'' . $userId . '\'';
    $result = $conn->query($sql);

    // Fetch the results from the query's response in a readable array
    if ($row = $result->fetch(PDO::FETCH_ASSOC)) {
        // Return the JSON form of the result
        echo json_encode($row);

    } else {
        // Return a JSON fail message
        echo json_encode(array("success" => false));
    }
}else {
	

}

?>
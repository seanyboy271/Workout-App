<?php

require_once(__DIR__ . '/../Database.php');

// Get the instance from the Database singleton
$conn = Database::getInstance();

header('Content-type: application/json');

// If this is a POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    
    $userId = $_POST["userId"];
    $teamId = $_POST["teamId"];
    $chaId = $_POST["chaId"];


    $sql = "INSERT INTO dbo.Log (UserId, TeamId, ChaId) VALUES ('$userId','$teamId','$chaId')";

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
}

?>
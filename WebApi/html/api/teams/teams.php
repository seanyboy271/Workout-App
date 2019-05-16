<?php

require_once(__DIR__ . '/../Database.php');

$conn = Database::getInstance();
header('Content-type: application/json ; charset=UTF-8');

//Post requests
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $organizer = $_POST["organizer"];
    $teamName = $_POST["teamName"];
    $teamSize = $_POST["teamSize"];
    $challengeId = $_POST["chalId"];


    $sql = 'INSERT INTO dbo.Teams (Name, Size, Organizer, ChallengeId) VALUES (\'' . $teamName . '\',\'' . $teamSize . '\' , \'' . $organizer . '\', \'' . $challengeId . '\')';
    // Call a MySQL query
    // magic, be careful

    $result = $conn->execute($sql);
    echo 'posted';

// Query for the Id
    $sql = 'SELECT Id FROM dbo.Teams WHERE Name = \'' . $teamName . '\'';
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


else{
    //Get request

    $organizer = $_GET["organizer"];
    $teamName = $_GET["teamName"];
    $teamSize = $_GET["teamSize"];
    $userId = $_GET["userId"];


    if (isset($userId)) {
        // Call a MySQL query
        $result = $conn->query('SELECT Id, Name, Size, Organizer FROM dbo.Teams t INNER JOIN (
SELECT TeamId FROM lookups.User_Team ut WHERE ut.UserId = ' . $userId . ') ut ON ut.TeamId = t.Id');

        // Fetch the results from the query's response in a readable array
        if ($row = $result->fetchAll(PDO::FETCH_ASSOC)) {
            // Return the JSON form of the result
            echo json_encode($row);
        } else {
            // Return a JSON fail message
            echo json_encode(array("success" => false));
        }
    } else if (!isset($organizer) && !isset($teamName) && !isset($teamSize) && !isset($userId)) {
        // Default get: Return all users

        // Call a MySQL query
        $result = $conn->query('SELECT * FROM dbo.Teams');

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
<?php

require_once(__DIR__ . '/../Database.php');

// Get the instance from the Database singleton
$conn = Database::getInstance();

header('Content-type: application/json ; charset=UTF-8');

// If this is a GET
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $id = $_GET['id'];
	$bio = $_GET['bio'];

    if (!isset($id) && !isset($bio)) {
        // Default get: Return all users

        // Call a MySQL query
        $result = $conn->query('SELECT * FROM dbo.Users');

        // Fetch the results from the query's response in a readable array
        if ($row = $result->fetchAll(PDO::FETCH_ASSOC)) {
            // Return the JSON form of the result
            echo json_encode($row);
        } else {
            // Return a JSON fail message
            echo json_encode(array("success" => false));
        }
    }

    else if (isset($id)){
        //Checking for a user with a specific Id
        $sql = 'SELECT Bio FROM dbo.Users WHERE Id = \''.$id.'\'';
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
}



else {
	$bio = $_POST["bio"];
	$id = $_POST["id"];

    $sql = "UPDATE dbo.Users SET bio = '{$bio}' WHERE id = {$id} LIMIT 1";
    // Call a MySQL query
    // magic, be careful
    $result = $conn->execute($sql);
	

    // Fetch the results from the query's response in a readable array
    // result is the row count
        // Return the JSON form of the result
        echo json_encode(array("success" => $result));
}
 

?>

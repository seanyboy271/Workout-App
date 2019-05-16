<?php

require_once(__DIR__ . '/../Database.php');

// Get the instance from the Database singleton
$conn = Database::getInstance();

header('Content-type: application/json');

// If this is a GET
if ($_SERVER['REQUEST_METHOD'] === 'GET') {

    $username = $_GET['username'];
    $password = $_GET['password'];
    $email = $_GET['email'];
    $id = $_GET['id'];

    if (!isset($username) && !isset($password) && !isset($email) && !isset($id)) {
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
    } else if (isset($username) && isset($password)) {
        // Login get: get user with given credentials

        // Call a MySQL query
        $sql = 'SELECT Id, Email, Username, IsCoach, IsPrivate FROM dbo.Users WHERE Username = \''.$username.'\' AND Password = \''.$password.'\'';
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
} else {
    $email = $_POST["email"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $passwordHash = $_POST["passwordHash"];
    $isCoach = $_POST["isCoach"];
    $isPrivate = $_POST["isPrivate"];

    $sql = 'INSERT INTO dbo.Users (Email, Username, Password, PasswordHash, IsCoach, IsPrivate)
                                        VALUES (\''.$email.'\', \''.$username.'\', \''.$password.'\', \''.$passwordHash.'\', b\''.$isCoach.'\', b\''.$isPrivate.'\')';

    // Call a MySQL query
    // magic, be careful
    $result = $conn->execute($sql);

    // Query for the Id
    $sql = 'SELECT Id FROM dbo.Users WHERE Username = \''.$username.'\' AND Password = \''.$password.'\'';
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
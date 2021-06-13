<?php
/*
 * Hatter application catalog
 */
require_once "dib.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

if(!isset($_GET['magic']) || $_GET['magic'] != "NechAtHa6RuzeR8x") {
    echo '<checker status="no" msg="magic" />';
    exit;
}

$pdo = pdo_connect();

getUser($pdo, $_GET['user'], $_GET['pw']);
/**
 * Ask the database for the user ID. If the user exists, the password
 * must match.
 * @param $pdo PHP Data Object
 * @param $user The user name
 * @param $password Password
 * @return id if successful or exits if not
 */
function getUser($pdo, $user, $password) {
    // Does the user exist in the database?
    $userQ = $pdo->quote($user);
    $query = "SELECT password from checkeruser where user=$userQ";

    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        // We found the record in the database
        // Check the password
        if($row['password'] != $password) {
            echo $row['password'];
            echo '<hatter status="no" msg="password error" />';
            exit;
        }

        echo '<hatter status="yes" msg="password match" />';
        exit;
    }

    echo '<hatter status="no" msg="user error" />';
    exit;
}
?>
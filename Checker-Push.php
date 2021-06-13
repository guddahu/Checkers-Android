<?php
require_once "dib.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

if(!isset($_GET['user'])) {
    echo '<checker status="no" msg="1" />';
    exit;
}
if(!isset($_GET['piece'])) {
    echo '<checker status="no" msg="2" />';
    exit;
}
if(!isset($_GET['king'])) {
    echo '<checker status="no" msg="3" />';
    exit;
}
if(!isset($_GET['x'])) {
    echo '<checker status="no" msg="4" />';
    exit;
}
if(!isset($_GET['y'])) {
    echo '<checker status="no" msg="5" />';
    exit;
}
// Process in a function
process($_GET['user'],$_GET['king'],$_GET['x'],$_GET['y'],$_GET['piece']);


/**
 * Process the query
 * @param $user the user to look for
 * @param $password the user password
 */
function process($usr,$king,$x,$y,$piece) {
    // Connect to the database
    $pdo = pdo_connect();

    $userQ = $pdo->quote($usr);
    $kingQ = $pdo->quote($king);
    $xQ = $pdo->quote($x);
    $yQ = $pdo->quote($y);
    $pieceQ = $pdo->quote($piece);

    $query = "UPDATE CheckerLive SET `user`=$userQ,piece=$pieceQ,x=$xQ,y=$yQ,king=$kingQ";
    $pdo->query($query);

    $flag = $pdo;

    if(!$flag) {
        echo '<checker status="no" />';
        exit;
    }
    else {
        echo '<checker status="yes" />';
        exit;
    }
}
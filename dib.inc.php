<?php

function pdo_connect() {
    try {
        // Production server
        $dbhost="mysql:host=mysql-user.cse.msu.edu;dbname=rajadit1";
        $user = "rajadit1";
        $password = "A59940112";
        return new PDO($dbhost, $user, $password);
    } catch(PDOException $e) {
        echo '<checker status="no" msg="Unable to select database" />';
        exit;
    }
}
?>
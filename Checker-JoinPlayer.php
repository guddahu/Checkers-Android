<?php
require_once "dib.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

if(!isset($_GET['user'])) {
    echo '<checker status="no" msg="magic" />';
    exit;
}

$pdo = pdo_connect();

join_user($pdo, $_GET['user']);
/**
 * Ask the database for the user ID. If the user exists, the password
 * must match.
 * @param $pdo PHP Data Object
 * @param $user The user name
 * @param $password Password
 * @return id if successful or exits if not
 */
function join_user($pdo, $user) {
    // Does the user exist in the database?
    $userQ = $pdo->quote($user);
    $query = "SELECT player1 from checker_online_players";

    $querylastSet = "SELECT LastPlayer from checker_online_players";
    $LastPlayers = $pdo->query($querylastSet);
    $LastPlayer = $LastPlayers->fetch();

    $players = $pdo->query($query);
    $player = $players->fetch();

    $query2 = "SELECT player2 from checker_online_players";
    $ifnulls = $pdo->query($query2);
    $ifnulled = $ifnulls->fetch();

    if($ifnulled['player2'] != 'NULL'){
        $query1 = "UPDATE checker_online_players SET player1 = 'NULL'";
        $query2 = "UPDATE checker_online_players SET player2 = 'NULL'";
        //$instruct1 =  $pdo->query($query1);
        $instruct2 =  $pdo->query($query2);

        $query3 = "UPDATE checker_online_players SET player1 = $userQ";
        $instruct3 =  $pdo->query($query3);

        echo '<checker status="yes" msg="password error" />';
        exit;
    }else{
        $query3 = "UPDATE checker_online_players SET player2 = $userQ";
        $instruct3 =  $pdo->query($query3);

        echo '<checker status="yes" msg="password error" />';
        exit;

    }


//        // We found the record in the database
//        // Check the password
//        if($LastPlayer['LastPlayer'] == "player2") {
//            $query = "UPDATE checker_online_players SET player1 = $user";
//            $instruct =  $pdo->query($query);
//
//            $Lastquery = "UPDATE checker_online_players SET LastPlayer = 'player1'";
//            $instructLast =  $pdo->query($Lastquery);
//
//
//            if(!$instruct){
//                echo '<checker status="no1" msg="player connect 1 failed" />';
//                exit;
//            }
//
//            echo '<checker status="yes" msg="password error" />';
//            exit;
//        }else{
//            $query = "UPDATE checker_online_players SET player2 = $userQ";
//            $instruct =  $pdo->query($query);
//
//            $Lastquery = "UPDATE checker_online_players SET LastPlayer = 'player2'";
//            $instructLast =  $pdo->query($Lastquery);
//
//            if(!$instruct){
//                echo '<checker status="no" msg="player connect 2 failed" />';
//                exit;
//            }
//
//            echo '<checker status="yes" msg="player connected 2" />';
//            exit;
//        }





    echo '<checker status="no" msg="join error" />';
    exit;
}
?>
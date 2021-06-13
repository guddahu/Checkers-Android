<?php
require_once "dib.inc.php";

echo '<?xml version="1.0" encoding="UTF-8" ?>';
if(!isset($_GET['user'])) {
    echo '<checker status="no" msg="Get user error1" />';
    exit;
}
// Connect to the database
$pdo = pdo_connect();
Match_Player($pdo,$_GET['user']);


/**
 * Process the query
 * @param $user the user to look for
 * @param $password the user password
 */
function Match_Player($pdo,$user) {


    $playerQuery1 = "SELECT player1 FROM checker_online_players";
    $player1 = $pdo->query($playerQuery1);
    $playerQuery2 = "SELECT player2 FROM checker_online_players";
    $player2 = $pdo->query($playerQuery2);

    $player1 = $player1->fetch();
    $player2 = $player2->fetch();

    //$player1 = $player1['player1'];


    if($player2['player2'] == 'NULL'){
        $player1 = $player1['player1'];

        echo "<checker status='yes' msg='start3' op=\"$player1\"/>";
        exit;
    }


    if ($player2['player2'] != 'NULL') {
        $drop = "DROP TABLE `CheckerLive`";
        $create = "CREATE TABLE `CheckerLive` (
`user` VARCHAR(255),
`piece` VARCHAR(32),
`x` VARCHAR(32),
`y` VARCHAR(32),
`king` VARCHAR(32)
)";
        $player2 = $player2['player2'];
        //$player21 = $pdo->quote($user);
        $ini = "INSERT INTO `CheckerLive`(`user`, `piece`, `x`, `y`, `king`) VALUES ('$player2', '0', '0','0','0')";

        $pdo->query($drop);
        $pdo->query($create);
        $pdo->query($ini);


        if ($player1['player1'] == $user) {
            //$player2 = $player2['player2'];
            echo "<checker status='yes' msg='start1' op=\"$player2\"/>";
        } else {
            $player1 = $player1['player1'];
            echo "<checker status='yes' msg='start2' op=\"$player1\"/>";
        }

        exit;
    }
     else {
         echo "<checker status='yes' msg='start3' />";
        exit;
    }
    exit;

}
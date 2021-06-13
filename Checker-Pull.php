<?php
require_once "dib.inc.php";

echo '<?xml version="1.0" encoding="UTF-8" ?>';



// Process in a function
process();


/**
 * Process the query
 * @param $user the user to look for
 * @param $password the user password
 */
function process() {
    // Connect to the database
    $pdo = pdo_connect();

        $user ="SELECT user FROM CheckerLive";
        $piece = "SELECT piece FROM CheckerLive";
        $x = "SELECT x FROM CheckerLive";
        $y = "SELECT y FROM CheckerLive";
        $King = "SELECT king FROM CheckerLive";

        $usr = $pdo->query($user);
        $usr = $usr->fetch();
        $usr = $usr['user'];

        $Piece = $pdo->query($piece);
        $Piece = $Piece->fetch();
        $Piece = $Piece['piece'];

        $x1 = $pdo->query($x);
        $x1 = $x1->fetch();
        $x1 = $x1['x'];

        $y1 = $pdo->query($y);
        $y1 = $y1->fetch();
        $y1 = $y1['y'];

        $King1 = $pdo->query($King);
        $King1 = $King1->fetch();
        $King1 = $King1['king'];

        echo "<checker status='yes' user=\"$usr\" piece=\"$Piece\" x=\"$x1\" y=\"$y1\" king=\"$King1\"/>";
        exit;

}
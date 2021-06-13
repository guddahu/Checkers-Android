<?php
require_once "dib.inc.php";

echo '<?xml version="1.0" encoding="UTF-8" ?>';

// Ensure the xml post item exists
if(!isset($_POST['xml'])) {
    echo '<checker status="no" msg="missing XML" />';
    exit;
}

processXml(stripslashes($_POST['xml']));

echo '<checker status="yes"/>';

/**
 * Process the XML query
 * @param $xmltext the provided XML
 */
function processXml($xmltext) {
    // Load the XML
    $xml = new XMLReader();
    if(!$xml->XML($xmltext)) {
        echo '<checker status="no" msg="invalid XML" />';
        exit;
    }


    // Connect to the database
    $pdo = pdo_connect();

    // Read to the start tag
    while($xml->read()) {
        if($xml->nodeType == XMLReader::ELEMENT && $xml->name == "checker") {
            // We have the hatter tag
            $magic = $xml->getAttribute("magic");
            if($magic != "NechAtHa6RuzeR8x") {
                echo '<checker status="no" msg="magic" />';
                exit;
            }

            // Read to the hatting tag
            while($xml->read()) {
                if($xml->nodeType == XMLReader::ELEMENT &&
                    $xml->name == "checkered") {

                    $name = $xml->getAttribute("username");
                    $pass = $xml->getAttribute("password");

                    $nameQ = $pdo->quote($name);
                    $passQ = $pdo->quote($pass);

                    $query = <<<QUERY
REPLACE INTO checkeruser(user , password)
VALUES($nameQ, $passQ)
QUERY;
                    if(!$pdo->query($query)) {
                        echo '<checker status="no" msg="insertfail">' . $query . '</checker>';
                        exit;
                    }

                    echo '<checker status="yes"/>';
                    exit;
                }
            }

        }
    }
}


echo '<checker save="no" msg="invalid XML" />';

?>
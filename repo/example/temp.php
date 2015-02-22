<?php
$a = array("apo" => "EXO");
$b = array("aaaa", "bbbbbb");
$x = 75;
$y = 25; 

function addition() {
       global $a;
       echo $a['apo'];
       //$GLOBALS['z'] = $GLOBALS['x'] + $GLOBALS['y'];
}

addition();
?>

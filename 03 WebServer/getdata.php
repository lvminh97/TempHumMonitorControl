<?php
require_once "functions.php";
$res = getLatestData();
$res['fan'] = getParam("fan");
$res['mist'] = getParam("mist");
$res['servo'] = getParam("servo");
echo json_encode($res);
?>
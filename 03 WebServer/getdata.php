<?php
require_once "functions.php";
$res = array('co' => getParam('co'),
				'co_limit' => getParam('co_limit'),
				'temp' => getParam('temp'),
				'hum' => getParam('hum'));
echo json_encode($res);
?>
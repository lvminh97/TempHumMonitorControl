<?php
	require_once "functions.php";
	// setTimeZone();
	// $time = date("Y-m-d H:i:s");
	
	$hasData = true;

	if(isset($_GET['temp']))
		$temp = $_GET['temp'];
	else
		$hasData = false;

	if(isset($_GET['humi']))
		$humi = $_GET['humi'];
	else
		$hasData = false;

	if(isset($_GET['lux']))
		$lux = $_GET['lux'];
	else
		$hasData = false;

	if($hasData === true)
		insertData($temp, $humi, $lux);

	$control = array('fan' => getParam("fan"),
						'mist' => getParam("mist"),
						'servo' => getParam("servo"));
	echo json_encode($control);
?>
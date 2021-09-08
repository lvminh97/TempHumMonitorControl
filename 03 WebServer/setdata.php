<?php
require_once "functions.php";
if(isset($_GET['fan'])){
	setParam("fan", $_GET['fan']);
	echo "{\"fan\":\"{$_GET['fan']}\"}";
}
else if(isset($_GET['mist'])){
	setParam("mist", $_GET['mist']);
	echo "{\"mist\":\"{$_GET['mist']}\"}";
}
else if(isset($_GET['servo'])){
	setParam("servo", $_GET['servo']);
	echo "{\"servo\":\"{$_GET['servo']}\"}";
}
?>
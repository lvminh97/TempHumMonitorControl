<?php
require_once "functions.php";
if(isset($_GET['co_limit'])){
	setParam('co_limit', $_GET['co_limit']);
	echo "SetLimitOK";
}
elseif(isset($_GET['quat_dieukhien'])){
	setParam("quat_dieukhien", $_GET['quat_dieukhien']);
	if($_GET['quat_dieukhien'] == "1"){
		echo "BatOK";
	}
	else if($_GET['quat_dieukhien'] == "0"){
		echo "TatOK";
	}
	else if($_GET['quat_dieukhien'] == "2"){
		echo "AutoOK";
	}
}
?>
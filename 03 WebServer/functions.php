<?php
require_once "DB/DB.php";

function setTimeZone(){
    date_default_timezone_set('Asia/Ho_Chi_Minh');
}

function getParam($param){
    $db = new DB;
    $data = $db->select("control", "*", "param='$param'");
    if(count($data) == 1){
        return $data[0]['value'];
    }
    else return null;
}

function setParam($param, $value){
	$db = new DB;
	$db->update("control", array('value' => $value), "param='$param'");
}

function getHistory($id){
    setTimeZone();
    $date = date("Y-m-d");
    $db = new DB;
    $query = $db->select("history", "*", "id='$id' AND date='$date'");
    if(count($query) == 0){
        return 0;
    }
    else{
        return $query[0]->value;
    }
}

function setHistory($id, $value){
    setTimeZone();
    $date = date("Y-m-d");
    $db = new DB;
    $query = $db->select("history", "*", "id='$id' AND date='$date'");
    if(count($query) == 0){
        $db->insert("history", array('id' => $id,
                                        'date' => $date,
                                        'value' => $value));
    }
    else{
        $value += $query[0]->value;
        $db->update("history", array('value' => $value), "id='$id' AND date='$date'");
    }
}

function insertData($temp, $humi, $lux){
    setTimeZone();
    $time = date("Y-m-d H:i:s");
    $db = new DB;
    $db->insert("data", array('time' => $time, 'temp' => $temp, 'humi' => $humi, 'lux' => $lux));
}

function getLatestData(){
    $db = new DB;
    $data = $db->select("data", "*", "1", "time DESC LIMIT 1");
    return $data[0];
}

function timeCompare($t1, $t2){
    $t1 = explode(":", $t1);
    $t2 = explode(":", $t2);
    $d = ($t1[0] * 60 + $t1[1]) - ($t2[0] * 60 + $t2[1]);
    if($d == 0) return 0;
    elseif($d > 0) return 1;
    else return -1;
}

function timeLength($t1, $t2){
    return strtotime($t1) - strtotime($t2);
}

?>
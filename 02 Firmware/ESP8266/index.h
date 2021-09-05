#ifndef INDEX_H
#define INDEX_H

const char index_page[] PROGMEM = R"=====(
<html>
<head>
<title>Config Mode</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
h1{
  color: red;
  margin-left: 40px;
  margin-bottom: 50px
}
.inp{
  width: 300px;
  height: 30px;
  margin-bottom: 15px;
}
.btn{
  margin-left: 80px;
  padding: 10px 40px; 
  background: #50CFFD; 
  color: #fff;
  cursor: pointer;
  border: none
}
.btn:hover{
  opacity: 0.7
}
</style>
</head>
<body>
<form method="POST" action="/save_config" style="width: 400px; margin: auto; margin-top: 190px; border: solid 5px #50CFFD; border-radius: 20px;">
<h1>WIFI CONFIG</h1>
<input name="ssid" placeholder="WIFI SSID" class="inp">
<input type="password" name="pass" placeholder="WIFI PASSWORD" class="inp">
<input name="server" placeholder="SERVER ADDRESS" class="inp">
<button type="submit" class="btn">Save</button>
</form>
</body>
</html>
)=====";

#endif

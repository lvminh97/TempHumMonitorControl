#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <WiFiClient.h>
#include "index.h"

char ESPSSID[20]  = "ESP0238592";
char ESPPASS[20]  = "123456789";
char HOSTNAME[20] = "minhsanslab.com";
int  PORT = 80;

ESP8266WebServer server(80);
WiFiClient esp;

StaticJsonDocument<100> doc;

char buff[40] = "";
int buffPos = 0;

bool isConfigMode = false;

void setup() {
  Serial.begin(9600);
  WiFi.mode(WIFI_AP_STA);
  WiFi.softAP(ESPSSID, ESPPASS);
  server.on("/", get_index_page);
  server.on("/save_config", save_config);
}

void loop() {
  checkDataReceive();
}

void checkDataReceive(){
  if(Serial.available()){
    while(Serial.available()){
      char c = Serial.read();
      buff[buffPos++] = c;
      if(c == '}'){
        process(buff);
        buffPos = 0;
//        Serial.println(resp.substring(resp.indexOf("stt:")));
      }
    }
  }
}

void get_index_page(){
  server.send(200, "text/html", index_page);
}

void save_config(){
  int i;
  server.send(200, "text/plain", "Setup successful!");
  WiFi.begin(server.arg("ssid"), server.arg("pass"));
  for(i = 0; i < server.arg("server").length(); i++){
    HOSTNAME[i] = server.arg("server")[i];
  }
  HOSTNAME[i] = 0;
  delay(1000);
  isConfigMode = false;
  Serial.println("{message:end_config_mode}\r\n");
}

void process(char *buff){
  DeserializationError error = deserializeJson(doc, buff);
  if(error){
    // json error 
  }
  else{
    const char *cmd = doc["cmd"];
    if(cmd == NULL){
      //       
    }
    else{
      if(strcmp(cmd, "CONFIG") == 0){
        Serial.println("{message:in_config_mode}\r\n");
        isConfigMode = true;
        server.begin();
        while(isConfigMode == true){
          server.handleClient();
          delay(5);
        }
      }
    }
  }
}

String sendRequest(char *buff){
  esp.connect(HOSTNAME, PORT);
  if(esp.connected()){
    char* tmp = buff + 1;
    tmp[buffPos - 2] = 0;
    esp.print(String("GET /index.php?") 
      + String(tmp) 
      + String(" HTTP/1.0\r\nHost: ") 
      + HOSTNAME + String("\r\n\r\n"));
  }
  unsigned long times = millis();
  String resp = "";
  while(millis() - times < 4000){
    if(esp.available()){
      while(esp.available()){
        resp += (char) esp.read();
      }
      break;
    }
  }
  esp.stop(); 
  return resp;
}

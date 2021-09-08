package com.jmctvs.smarthome2.Utilities;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.jmctvs.smarthome2.Model.Account;
import com.jmctvs.smarthome2.Model.Sign;

import java.util.HashMap;

public class Utilities extends Application {
    private static Account accountObj = null;

    private static String exam = "";
    private static String pack = "";

    private static String quesData = "";
    private static HashMap<String, String> ansData;
    private static String answerData = "";

    private static Sign sign;

    private static String currentScreen = "";

//    public static String HOST = "http://minhsanslab.com:81/thibanglaixe";
//    public static String HOST = "http://192.168.0.103:81/thibanglaixe";
//    public static String AI_HOST = "http://192.168.0.103:51001";

    public static String HOST = "http://192.168.0.5/api";
    public static String AI_HOST = "http://192.168.0.5:51001";

    public static Account getAccountObj() {
        return accountObj;
    }

    public static void setAccountObj(Account accountObj) {
        Utilities.accountObj = accountObj;
    }

    public static String getExam() {
        return exam;
    }

    public static void setExam(String exam) {
        Utilities.exam = exam;
    }

    public static String getPack() {
        return pack;
    }

    public static void setPack(String pack) {
        Utilities.pack = pack;
    }

    public static String getQuesData() {
        return quesData;
    }

    public static void setQuesData(String quesData) {
        Utilities.quesData = quesData;
    }

    public static HashMap<String, String> getAnsData() {
        return ansData;
    }

    public static void setAnsData(HashMap<String, String> ansData) {
        Utilities.ansData = ansData;
    }

    public static String getAnswerData() {
        return answerData;
    }

    public static void setAnswerData(String answerData) {
        Utilities.answerData = answerData;
    }

    public static String getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(String currentScreen) {
        Utilities.currentScreen = currentScreen;
    }

    public static Sign getSign() {
        return sign;
    }

    public static void setSign(Sign sign) {
        Utilities.sign = sign;
    }

    public static void drawBox(Bitmap bp, int xmin, int ymin, int xmax, int ymax){
        Canvas canvas = new Canvas(bp);
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(10);
        p.setAntiAlias(true);
        p.setFilterBitmap(true);
        p.setDither(true);
        p.setColor(Color.RED);
        canvas.drawLine(xmin, ymin, xmax, ymin, p);
        canvas.drawLine(xmax, ymin, xmax, ymax, p);
        canvas.drawLine(xmax, ymax, xmin, ymax, p);
        canvas.drawLine(xmin, ymax, xmin, ymin, p);
    }
}

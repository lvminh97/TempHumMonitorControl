package com.jmctvs.smarthome2.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jmctvs.smarthome2.Utilities.Utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Sign {
    private Bitmap sign;
    private String time;
    private String title;

    public Sign(String img, String time, String title) {
        if(!img.equals("")) {
            try {
                URL url = new URL(Utilities.HOST + "/" + img);
                this.sign = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else this.sign = null;
        this.time = time;
        this.title = title;
    }

    public Bitmap getSign() {
        return sign;
    }

    public void setSign(Bitmap sign) {
        this.sign = sign;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

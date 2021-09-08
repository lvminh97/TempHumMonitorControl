package com.jmctvs.smarthome2.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jmctvs.smarthome2.Utilities.Utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Question {
    private String quesId, ques, ans1, ans2, ans3, ans4, correct_ans, important, detail;
    private Bitmap ques_img;

    public Question(String quesId, String ques, String ans1, String ans2, String ans3, String ans4, String correct_ans, String img, String important, String detail) {
        this.quesId = quesId;
        this.ques = ques;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.correct_ans = correct_ans;
        this.important = important;
        this.detail = detail;
        if(!img.equals("")) {
            try {
                URL url = new URL(Utilities.HOST + "/" + img);
                this.ques_img = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else this.ques_img = null;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Bitmap getQues_img() {
        return ques_img;
    }

    public void setQues_img(Bitmap ques_img) {
        this.ques_img = ques_img;
    }
}

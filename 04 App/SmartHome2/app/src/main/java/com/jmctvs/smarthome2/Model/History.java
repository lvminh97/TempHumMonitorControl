package com.jmctvs.smarthome2.Model;

public class History {
    private String answer_data, correct, wrong, pack, exam, time;

    public History(String ansData, String correct, String wrong, String pack, String exam, String time) {
        this.answer_data = ansData;
        this.correct = correct;
        this.wrong = wrong;
        this.pack = pack;
        this.exam = exam;
        this.time = time;
    }

    public String getAnswer_data() {
        return answer_data;
    }

    public void setAnswer_data(String answer_data) {
        this.answer_data = answer_data;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

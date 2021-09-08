package com.jmctvs.smarthome2.Model;

public class Account {
    private String uid;
    private String email;
    private String fullname;

    public Account(String uid, String email, String fullname) {
        this.uid = uid;
        this.email = email;
        this.fullname = fullname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

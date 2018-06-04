package com.antonybaasan.receiptkeeper.restdata.security;

public class FbUserInfo {
    private String uid;
    private String name;
    private String email;

    private String token;

    public FbUserInfo(String uid, String name, String email, String token) {
        this.setToken(token);
        this.setUid(uid);
        this.setName(name);
        this.setEmail(email);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

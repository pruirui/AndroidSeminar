package com.xiao.musicplayer.model;

import com.xiao.musicplayer.net.HttpTask;

public class User {
    private String phone;
    private String password;
    private String username;
    private String image;
    private int status;

    public User(String phone, String password, String username, String image, int status) {
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.image = image;
        this.status = status;
    }
    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
        this.status = 0;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void updateInfo(){
        this.phone = "11322989983";
        this.password = "pass_word";
        this.username = "shaq_z";
        this.image ="/sdcard/Download/wifi.PNG";
        this.status = 1;

    }
}

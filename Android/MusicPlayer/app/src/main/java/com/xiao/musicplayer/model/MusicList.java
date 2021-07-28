package com.xiao.musicplayer.model;

import java.io.Serializable;
import java.util.Date;

public class MusicList implements Serializable {
    int list_id;
    String u_phone;
    String list_name;
    Date time;


    public MusicList(int list_id, String u_phone, String list_name, Date time) {
        this.list_id = list_id;
        this.u_phone = u_phone;
        this.list_name = list_name;
        this.time = time;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

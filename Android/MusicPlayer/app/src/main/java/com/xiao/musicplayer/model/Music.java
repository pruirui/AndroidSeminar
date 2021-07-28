package com.xiao.musicplayer.model;

import java.util.Date;

public class Music {
    private Integer music_id;
    private String name;
    private String url;
    private String singer;
    private Date time;
    //是否公开(0为私密，1为公开，2为待审核)
    private Integer status;
    private String u_phone;
    private boolean isLocal;
    private int duration;


    public Music(Integer music_id, String name, String url, String singer, Date time, Integer status, String u_phone,boolean isLocal,int duration) {
        this.music_id = music_id;
        this.name = name;
        this.url = url;
        this.singer = singer;
        this.time = time;
        this.status = status;
        this.u_phone = u_phone;
        this.duration = duration;
        this.isLocal = isLocal;
    }

    public Music(){

    }
    public boolean isLocal() {
        return isLocal;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
    public Integer getMusic_id() {
        return music_id;
    }

    public void setMusic_id(Integer music_id) {
        this.music_id = music_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }
}

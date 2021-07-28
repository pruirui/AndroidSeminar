package com.xiao.musicplayer.net;


import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import java.lang.ref.WeakReference;

public abstract class HttpMessageHandler extends Handler {
    private WeakReference<AppCompatActivity> activity;



    public WeakReference<AppCompatActivity> getActivity() {
        return activity;
    }

    public void setActivity(WeakReference<AppCompatActivity> activity) {
        this.activity = activity;
    }

    public HttpMessageHandler(AppCompatActivity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        control(msg);
    }

    /**
     * 子类重新此方法即可
     * @param msg
     */
    public abstract void control(Message msg);

}

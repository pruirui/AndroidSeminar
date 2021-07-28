package com.xiao.musicplayer.net.handler;

import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.net.HttpMessageHandler;

public class DeleteMusicHandler extends HttpMessageHandler {

    public DeleteMusicHandler(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void control(Message msg) {

    }
}

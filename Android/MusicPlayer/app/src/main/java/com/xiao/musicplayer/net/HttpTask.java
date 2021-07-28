package com.xiao.musicplayer.net;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 用于进行Http请求的异步任务类（POST请求）
 * 使用：
 * 构造方法传入一个用于接收请求结果的Handler和要进行请求的URL（String）
 * execute方法传入要进行请求的参数（只能传一个String型参数）
 */
public class HttpTask extends AsyncTask<String, Integer, String> {
    private HttpMessageHandler handler;
    private String URL;

    public static final String CONNECT_OR_READ_TIMEOUT = "timeout";
    public static final String HTTP_REQUEST_RESULT = "result";

    public HttpTask(String URL, HttpMessageHandler handler) {
        this.URL = URL;
        this.handler = handler;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "{ \"res\":\""+CONNECT_OR_READ_TIMEOUT+"\",\"data\":[]}";
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(URL).openConnection();
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.getOutputStream().write(strings[0].getBytes(StandardCharsets.UTF_8));
            result = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putString(HTTP_REQUEST_RESULT, result);
        Message message = Message.obtain();
        message.setData(bundle);
        handler.sendMessage(message);
        Log.d("TTT_send", String.valueOf(strings[0]));
        Log.d("TTT_result", result);
        return result;
    }
}

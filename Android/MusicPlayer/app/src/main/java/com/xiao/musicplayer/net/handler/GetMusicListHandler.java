package com.xiao.musicplayer.net.handler;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.MainActivity;
import com.xiao.musicplayer.R;
import com.xiao.musicplayer.model.MusicList;
import com.xiao.musicplayer.model.User;
import com.xiao.musicplayer.net.HttpMessageHandler;
import com.xiao.musicplayer.net.HttpTask;
import com.xiao.musicplayer.util.ConstUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetMusicListHandler extends HttpMessageHandler {

    public GetMusicListHandler(AppCompatActivity activity) {
        super(activity);
    }


    @Override
    public void control(Message msg) {
        try {
            String result = msg.getData().getString(HttpTask.HTTP_REQUEST_RESULT);
            JSONObject json = new JSONObject(result);
            JSONArray dataArr = json.getJSONArray("data");
            JSONObject data = null;
            if(dataArr.length()>0){
                data = dataArr.getJSONObject(0);
            }
            String res = json.getString("res");
            switch (res) {
                case "false":
                    Toast.makeText(super.getActivity().get(), "出错了" , Toast.LENGTH_LONG).show();
                    break;
                case HttpTask.CONNECT_OR_READ_TIMEOUT:
                    Toast.makeText(super.getActivity().get(), "网络开小差儿啦~~~", Toast.LENGTH_LONG).show();
                    break;
                case "true":
                    if(data == null){
                        Toast.makeText(super.getActivity().get(), "服务器错误，无法获得歌单", Toast.LENGTH_LONG).show();
                        break;
                    }
                    ConstUtil.musicLists = new ArrayList<>();
                    for (int i = 0;i<dataArr.length();i++){
                        JSONObject _temp = dataArr.getJSONObject(i);
                        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(_temp.getString("time"));
                        ConstUtil.musicLists.add(
                                new MusicList(_temp.getInt("list_id"),
                                        _temp.getString("u_phone"),_temp.getString("list_name"),time)
                        );
                    }
                    break;
            }
        }catch (JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

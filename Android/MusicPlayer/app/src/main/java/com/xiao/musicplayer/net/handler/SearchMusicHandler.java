package com.xiao.musicplayer.net.handler;

import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.MusicActivity;
import com.xiao.musicplayer.SearchActivity;
import com.xiao.musicplayer.model.Music;
import com.xiao.musicplayer.net.HttpMessageHandler;
import com.xiao.musicplayer.net.HttpTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchMusicHandler extends HttpMessageHandler {
    private WeakReference<SearchActivity> activity;

    public SearchMusicHandler(SearchActivity activity) {
        super(activity);
        this.activity = new WeakReference<>(activity);
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
                    List<Music> musics = new ArrayList<>();
                    for (int i = 0;i<dataArr.length();i++){
                        JSONObject _temp = dataArr.getJSONObject(i);

                        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(_temp.getString("time"));
                        musics.add(
                                new Music(_temp.getInt("music_id"),_temp.getString("name"),
                                        _temp.getString("url"),_temp.getString("singer"),
                                        time,_temp.getInt("status"),
                                        _temp.getString("u_phone"),false,0)
                        );
                    }
                    activity.get().initListView(musics);
                    break;
            }
        }catch (JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

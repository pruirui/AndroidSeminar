package com.xiao.musicplayer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.model.Music;
import com.xiao.musicplayer.model.MusicList;
import com.xiao.musicplayer.model.adapter.MusicAdvanceAdapter;
import com.xiao.musicplayer.net.HttpTask;
import com.xiao.musicplayer.net.handler.GetMusicHandler;
import com.xiao.musicplayer.net.handler.SearchMusicHandler;
import com.xiao.musicplayer.util.ConstUtil;

import java.util.List;

public class SearchActivity extends AppCompatActivity{

    private ListView lvMusicList;

    private List<Music> musics;// 数据

    private MusicAdvanceAdapter adapter;

    private MusicList musicList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);

        String data = getIntent().getStringExtra("key");
        String type = getIntent().getStringExtra("type");

        Log.e("AAAAAAA",data);

        lvMusicList =  findViewById(R.id.music_list_music_activity_layout);

        musicList = (MusicList)getIntent().getSerializableExtra("musiclist");

        //搜索歌曲数据
        if(type.equals("name")){
            new HttpTask(ConstUtil.SEARCH_NAME_URL,new SearchMusicHandler(this)).execute(("name="+data));
        }else{
            new HttpTask(ConstUtil.SEARCH_SINGER_URL,new SearchMusicHandler(this)).execute(("singer="+data));
        }


    }

    public void initListView(List<Music> musics){
        this.musics = musics;
        adapter = new MusicAdvanceAdapter(this,R.layout.music_item_search,musics);
        lvMusicList.setAdapter(adapter);
        lvMusicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


}

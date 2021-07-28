package com.xiao.musicplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.model.Music;
import com.xiao.musicplayer.model.MusicList;
import com.xiao.musicplayer.model.adapter.MusicAdvanceAdapter;
import com.xiao.musicplayer.net.HttpTask;
import com.xiao.musicplayer.net.handler.GetMusicHandler;
import com.xiao.musicplayer.net.handler.GetMusicListHandler;
import com.xiao.musicplayer.util.ConstUtil;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity{

    private ListView lvMusicList;

    private List<Music> musics;// 数据

    private MusicAdvanceAdapter adapter;

    private MusicList musicList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_layout);

        lvMusicList =  findViewById(R.id.music_list_music_activity_layout);

        musicList = (MusicList)getIntent().getSerializableExtra("musiclist");

        //过去歌曲数据
        new HttpTask(ConstUtil.GET_MUSIC_BY_LIST,new GetMusicHandler(this)).execute(("list_id="+musicList.getList_id()));

    }

    public void initListView(List<Music> musics){
        this.musics = musics;
        adapter = new MusicAdvanceAdapter(this,R.layout.music_item_advance,musics);
        lvMusicList.setAdapter(adapter);
        lvMusicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


}

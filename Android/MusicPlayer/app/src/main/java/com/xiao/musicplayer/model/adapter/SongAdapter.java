package com.xiao.musicplayer.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xiao.musicplayer.R;
import com.xiao.musicplayer.model.Music;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Music> {
    public int resId;
    public SongAdapter(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
        resId=resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music song=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resId,parent,false);
        TextView tvTile= (TextView) view.findViewById(R.id.music_name_music_item);
        TextView tvSinger= (TextView) view.findViewById(R.id.singer_music_item);
        tvTile.setText("歌曲："+song.getName());
        tvSinger.setText("歌手："+song.getSinger());
        int m=song.getDuration()/60000;
        int s=(song.getDuration()-m*60000)/1000;
        return view;
    }
}

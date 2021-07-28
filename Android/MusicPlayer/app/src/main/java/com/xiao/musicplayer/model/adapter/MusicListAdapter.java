package com.xiao.musicplayer.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.R;
import com.xiao.musicplayer.model.Music;
import com.xiao.musicplayer.model.MusicList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MusicListAdapter extends ArrayAdapter<MusicList> {
    public int resId;
    public List<MusicList> items;
    public Context context;


    public MusicListAdapter(Context context, int resource, List<MusicList> objects) {
        super(context, resource, objects);
        this.resId=resource;
        this.items = objects;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MusicList musicList = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resId,parent,false);
        TextView tvName = view.findViewById(R.id.music_list_name_item);
        TextView tvTime = view.findViewById(R.id.time_music_list_item);
        tvName.setText(musicList.getList_name());
        tvTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(musicList.getTime()));
        return view;
    }
}

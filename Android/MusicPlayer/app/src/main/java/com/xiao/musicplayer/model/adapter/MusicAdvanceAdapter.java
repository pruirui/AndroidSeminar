package com.xiao.musicplayer.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xiao.musicplayer.R;
import com.xiao.musicplayer.model.Music;

import java.util.List;

public class MusicAdvanceAdapter extends ArrayAdapter<Music> {
    private int resource;
    private List<Music> mlist;

    public MusicAdvanceAdapter(@NonNull Context context, int resource, List<Music> mlist) {
        super(context, resource, mlist);
        this.resource = resource;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Music song = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        TextView tvMusicName = view.findViewById(R.id.music_name_music_item_advance);
        TextView tvSinger = view.findViewById(R.id.singer_music_item_advance);
        tvMusicName.setText(song.getName());
        tvSinger.setText(song.getSinger());
        int currentPosition = position;

        return view;
    }
}

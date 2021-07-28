package com.xiao.musicplayer.model.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.R;
import com.xiao.musicplayer.model.Music;
import com.xiao.musicplayer.net.HttpTask;
import com.xiao.musicplayer.net.handler.LoginHttpHandler;
import com.xiao.musicplayer.util.ConstUtil;
import com.xiao.musicplayer.util.StringUtil;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {
    public int resId;
    public List<Music> items;
    public Context context;


    public MusicAdapter(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
        this.resId=resource;
        this.items = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music song = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resId,parent,false);
        TextView tvMusicName = view.findViewById(R.id.music_name_music_item);
        TextView tvSinger = view.findViewById(R.id.singer_music_item);
        Button btDownload = view.findViewById(R.id.down_load_music_item);
        Button btAddMusicList = view.findViewById(R.id.add_musiclist_music_item);
        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *
                 *
                 *
                 *
                 */
            }
        });
        btAddMusicList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToMusicList(song);
            }
        });

        tvMusicName.setText(song.getName());
        tvSinger.setText(song.getSinger());

        return view;
    }

    /**
     * 显示music list
     */
    private void addToMusicList(Music music) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        @SuppressLint("InflateParams") final View alert = LayoutInflater.from(getContext()).inflate(R.layout.music_list_alertdialog_layout,null);

        final ListView lvMusiclists = alert.findViewById(R.id.music_list_alertdialog);
        lvMusiclists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 *
                 *
                 *
                 *
                 *
                 */
            }
        });
        MusicListAdapter adapter = new MusicListAdapter(getContext(),R.layout.music_list_item,ConstUtil.musicLists);
        lvMusiclists.setAdapter(adapter);
        builder.setView(alert);
        builder.show();
    }

}

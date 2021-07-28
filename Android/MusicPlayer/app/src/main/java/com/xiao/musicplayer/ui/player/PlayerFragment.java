package com.xiao.musicplayer.ui.player;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xiao.musicplayer.MainActivity;
import com.xiao.musicplayer.R;
import com.xiao.musicplayer.model.Music;
import com.xiao.musicplayer.model.adapter.MusicAdapter;
import com.xiao.musicplayer.model.adapter.MusicListAdapter;
import com.xiao.musicplayer.util.ConstUtil;
import com.xiao.musicplayer.util.MusicUtil;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerFragment extends Fragment {

    private MainActivity mainActivity;
    private ObjectAnimator mAnimator;

    private TextView tvMusicName;
    private SeekBar sbSeek;
    private TextView tvLeft;
    private TextView tvRight;
    private ListView lsAudios;
    private boolean seekBarChange = false;
    private int status = 0;//0 是循环播放 1 是单曲循环 2 是 随机播放
    private int currentPositon;
    private boolean isPlay = false;
    private Button btPalyMode;

    public MediaPlayer mediaPlayer = null;



    public List<Music> currentList;

    private View root;
    private ImageView ivAlbumPic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_player, container, false);
        initAudioList();
        initView();
        initAnimator();
        return root;
    }

    private void initAnimator() {
        mAnimator = ObjectAnimator.ofFloat(ivAlbumPic, "rotation", 0.0f, 360.0f);
        mAnimator.setDuration(3000);//设定转一圈的时间
        mAnimator.setRepeatCount(Animation.INFINITE);//设定无限循环
        mAnimator.setRepeatMode(ObjectAnimator.RESTART);// 循环模式
        mAnimator.setInterpolator(new LinearInterpolator());// 匀速
        mAnimator.start();//动画开始
        mAnimator.pause();//动画暂停
    }
    void initView(){
        tvMusicName = root.findViewById(R.id.music_info_tv_fragment_player);
        tvLeft = root.findViewById(R.id.start_time_fragment_player);
        tvRight = root.findViewById(R.id.end_time_fragment_player);
        sbSeek = root.findViewById(R.id.seekbar_fragment_player);
        btPalyMode = root.findViewById(R.id.btn_play_mode_fragment_player);
        ivAlbumPic = root.findViewById(R.id.album_center);

        btPalyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++status;
                status %= 3;
                switch (status){
                    case 0:
                        btPalyMode.setBackgroundResource(R.drawable.loop_playback);
                        break;
                    case 1:
                        btPalyMode.setBackgroundResource(R.drawable.single_loop_play);
                        break;
                    case 2:
                        btPalyMode.setBackgroundResource(R.drawable.random_playback);
                        break;
                }
            }
        });
        Button btSkipPrevious = root.findViewById(R.id.btn_skip_previous);
        btSkipPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null) return;
                switch (status){
                    case 0:
                        currentPositon--;
                        currentPositon%=currentList.size();
                        initMediaPlayer(currentList.get(currentPositon));
                        break;
                    case 1:
                        initMediaPlayer(currentList.get(currentPositon));
                        break;
                    case 2:
                        currentPositon = new Random().nextInt(currentList.size());
                        initMediaPlayer(currentList.get(currentPositon));
                        break;
                }
            }
        });

        Button btSkipNext = root.findViewById(R.id.btn_skip_next);
        btSkipNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case 0:
                        currentPositon++;
                        currentPositon%=currentList.size();
                        initMediaPlayer(currentList.get(currentPositon));
                        break;
                    case 1:
                        initMediaPlayer(currentList.get(currentPositon));
                        break;
                    case 2:
                        currentPositon = new Random().nextInt(currentList.size());
                        initMediaPlayer(currentList.get(currentPositon));
                        break;
                }
            }
        });
        Button btAddToMusiclist = root.findViewById(R.id.btn_add_musiclist_fragment_player);
        btAddToMusiclist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) return;
                addToMusicList(currentList.get(currentPositon));
            }
        });

        tvMusicName = root.findViewById(R.id.music_info_tv_fragment_player);
        tvRight = root.findViewById(R.id.end_time_fragment_player);
        tvLeft = root.findViewById(R.id.start_time_fragment_player);
        sbSeek = root.findViewById(R.id.seekbar_fragment_player);
        sbSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarChange = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarChange = false;
                mediaPlayer.seekTo(seekBar.getProgress());
                int m = mediaPlayer.getCurrentPosition() / 60000;
                int s = (mediaPlayer.getCurrentPosition() - m * 60000) / 1000;
                tvLeft.setText(m + ":" + s );
            }
        });

        Button btPlay = root.findViewById(R.id.btn_play_fragment_player);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) return;
                if (isPlay){
                    isPlay = false;
                    mediaPlayer.pause();
                    mAnimator.pause();
                    btPlay.setBackgroundResource(R.drawable.play);
                }else {
                    isPlay = true;
                    mediaPlayer.start();
                    mAnimator.resume();
                    btPlay.setBackgroundResource(R.drawable.pause);
                }
            }
        });


        lsAudios = root.findViewById(R.id.current_music_list);
        MusicAdapter adapter = new MusicAdapter(getActivity(), R.layout.music_item_show, currentList);
        lsAudios.setAdapter(adapter);
        lsAudios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music song = currentList.get(position);
                initMediaPlayer(song);
            }
        });

    }

    /**
     * 显示music list
     */
    private void addToMusicList(Music music) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") final View alert = getActivity().getLayoutInflater().inflate(R.layout.music_list_alertdialog_layout, null);
        final ListView lvMusiclists = alert.findViewById(R.id.music_list_alertdialog);


        Log.e("TEST  addToMusicList","in "+builder+" list:"+lvMusiclists);

        lvMusiclists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 *
                 *将music 加入 选中的歌单
                 *
                 *
                 *
                 */
            }
        });
        MusicListAdapter adapter = new MusicListAdapter(getContext(),R.layout.music_list_item, ConstUtil.musicLists);
        lvMusiclists.setAdapter(adapter);
        builder.setView(alert);
        builder.show();
    }
    private void initAudioList() {
        currentList = MusicUtil.getMusicData(getActivity());
    }

    private void initMediaPlayer(Music song) {

        try {
            if (mediaPlayer == null)
                mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    switch (status){
                        case 0:
                            currentPositon++;
                            currentPositon%=currentList.size();
                            initMediaPlayer(currentList.get(currentPositon));
                            break;
                        case 1:
                            initMediaPlayer(currentList.get(currentPositon));
                            break;
                        case 2:
                            currentPositon = new Random().nextInt(currentList.size());
                            initMediaPlayer(currentList.get(currentPositon));
                            break;
                    }
                }
            });
            mediaPlayer.reset();
            if(!song.isLocal()){
                song.setUrl(MusicUtil.downloadMusic(song));
                //放到数据库
                song.setLocal(true);
            }
            mediaPlayer.setDataSource(song.getUrl());
            mediaPlayer.prepare();
            mediaPlayer.start();
            int m = song.getDuration() / 60000;
            int s = (song.getDuration() - m * 60000) / 1000;
            tvRight.setText(m + ":" + s );
            tvMusicName.setText(song.getName());
            sbSeek.setMax(song.getDuration());

            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if (seekBarChange) return;
                    sbSeek.setProgress(mediaPlayer.getCurrentPosition());
                    int m = mediaPlayer.getCurrentPosition() / 60000;
                    int s = (mediaPlayer.getCurrentPosition() - m * 60000) / 1000;
                    tvLeft.setText(m + ":" + s );
                }
            };
           new Thread(new Runnable() {
               @Override
               public void run() {
                   while(true){
                       handler.sendEmptyMessage(0);
                       try {
                           Thread.sleep(1000);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
               }
           }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
package com.xiao.musicplayer.util;


import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


import com.xiao.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐扫描工具
 *
 * @author llw
 */
public class MusicUtil {
    /**
     * 扫描系统里面的音频文件，返回一个list集合
     */
    public static List<Music> getMusicData(Context context) {
        MediaScannerConnection.scanFile(context, new String[]{Environment
                .getExternalStorageDirectory().getAbsolutePath()}, null, null);

        // 媒体库查询语句（写一个工具类MusicUtils）
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.MIME_TYPE + "=?",
                new String[]{"audio/mpeg"}, null);
        String fileName, title, singer,size, filePath = "";
        int duration, m, s;
        List<Music> musicList = new ArrayList<Music>();
        Music song;

        if (cursor.moveToFirst()) {
            do{
                fileName = cursor.getString(1);
                title = cursor.getString(2);
                duration = cursor.getInt(3);
                singer = cursor.getString(4);
                size = (cursor.getString(6) == null) ? "未知" : cursor.getInt(6) / 1024 / 1024 + "MB";
                if (cursor.getString(7) != null) filePath = cursor.getString(7);

                song = new Music(null,title,filePath,singer,null,0,null,true,duration);
                //大于30秒的
                if (duration > 30000) {
                    musicList.add(song);
                }
            }while (cursor.moveToNext());
            // 释放资源
            cursor.close();
        }
        return musicList;
    }


    public static String downloadMusic(Music song){

        return null;
    }

}

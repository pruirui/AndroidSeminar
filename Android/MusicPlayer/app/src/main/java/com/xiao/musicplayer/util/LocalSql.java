package com.xiao.musicplayer.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.xiao.musicplayer.model.Music;


public class LocalSql extends SQLiteOpenHelper {
    Context cont;
    static String name_sql;
    static SQLiteDatabase db;

    /**
     * link and create
     * @param name_of_sql
     */
    public static void linkToDB(String name_of_sql){
        name_sql = name_of_sql;
        db = SQLiteDatabase.openOrCreateDatabase(name_sql, null, null);

        Cursor cursor = db.rawQuery("select * from sqlite_master where type = 'table' and name = 't_cmpt_cp'",null);
        if (cursor.getCount() ==0){
            db.execSQL("CREATE TABLE LOCAL_MUSIC (PATH VARCHAR PRIMARY KEY, NAME VARCHAR, SINGER VARCHAR ) ");
            db.execSQL("CREATE TABLE MUSIC_MUSICLIST (PATH VARCHAR, LIST_ID VARCHAR ) ");
        }else {
            db.execSQL("drop table LOCAL_MUSIC");
            db.execSQL("CREATE TABLE LOCAL_MUSIC (PATH VARCHAR PRIMARY KEY, NAME VARCHAR, SINGER VARCHAR ) ");

        }

    }

    /**
     * inserting
     */
    public static void musicInsertToDB(String new_path, String new_name, String new_singer){
        db.execSQL("INSERT INTO LOCAL_MUSIC VALUES (?, ?, ?)",new Object[] {new_path,new_name,new_singer});
    }
    public static void listInsertToDB(String new_path, String new_list_id ){
        db.execSQL("INSERT INTO MUSIC_MUSICLIST VALUES (?, ?)",new Object[] {new_path,new_list_id});
    }

    /**
     * search
     * WARNING:check null!!!!!!!!
     */
    public Music[] listToMusicSearch(String list_id){
        Cursor cursor = db.rawQuery("select * from MUSIC_MUSICLIST where LIST_ID = ? ",new String[] {list_id} );
        Music musics [] = new Music[cursor.getCount()];
        cursor.moveToFirst();
        int count = 0;
        if(cursor.moveToFirst()){
            do{
                musics[count].setName(cursor.getString(cursor.getColumnIndex("NAME")));
                musics[count].setUrl(cursor.getString(cursor.getColumnIndex("PATH")));
                musics[count].setSinger(cursor.getString(cursor.getColumnIndex("SINGER")));
                musics[count].setLocal(true);
            }while (cursor.moveToNext());
        }
        return musics;
    }




    public LocalSql(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        cont = context;
    }




    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE LOCAL_MUSIC(_id INTEGER PRIMARY KEY AUTOINCREMENT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldV, int newV){

    }

}
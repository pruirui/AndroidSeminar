package com.xiao.musicplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.xiao.musicplayer.model.User;
import com.xiao.musicplayer.util.UriToPathUtil;
import com.xiao.musicplayer.util.ConstUtil;
import com.xiao.musicplayer.util.HttpRe;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    int public_status;
    View public_button;
    View unpublic_button;
    View upload_button;
    View warning_text;
    EditText music_name;
    EditText singer_name;
    String music_path;

    String music_n;
    String singer_n;


    Map<String, Object> map = new HashMap<String, Object>();



    /**
     * 点击打开文件选择器
     */
    public void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // 代表所有文件类型
        //可选择性打开的文件类型设置
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //设置回传结果
        this.startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            music_path = UriToPathUtil.getImageAbsolutePath(this, uri);
            Log.e("path",music_path);

            //*
            music_n = music_name.getText().toString();
            singer_n = singer_name.getText().toString();

            //*/

            map.put("name", music_n);
            map.put("singer", singer_n);
            map.put("status", public_status);
            map.put("u_phone", ConstUtil.user.getPhone());

            try {
                new Thread(new Runnable() {
                    public void run(){
                        try {
                            HttpRe.uploadFile(music_path,map,ConstUtil.ADD_MUSIC_URL);

                        } catch (Exception e) {
                            Log.e("uploadinth","wrong!!!");
                        }
                    }
                }).start();


                onBackPressed();
            } catch (Exception e) {
                Log.e("upload","wrong!!!");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        View return_button = this.findViewById(R.id.return_upload);
        public_button = this.findViewById(R.id.state_public_upload);
        unpublic_button = this.findViewById(R.id.state_unpublic_upload);
        final Drawable d =  public_button.getBackground();
        music_name = this.findViewById(R.id.text_name_music_upload);
        singer_name = this.findViewById(R.id.text_name_singer_upload);
        upload_button = this.findViewById(R.id.upload_music_upload);
        warning_text = this.findViewById(R.id.warning_text_upload);


        public_status = -1;


        public_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                public_status = 2;
                public_button.setBackgroundColor(Color.rgb(	192,255, 62));
                unpublic_button.setBackground(d);
            }
        });
        unpublic_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                public_status = 1;
                unpublic_button.setBackgroundColor(Color.rgb(	192,255, 62));
                public_button.setBackground(d);
            }
        });

        upload_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                music_n = music_name.getText().toString();
                singer_n = singer_name.getText().toString();
                if(music_n.equals("") || singer_n.equals("")|| public_status == -1){
                    warning_text.setVisibility(View.VISIBLE);
                    return;
                }
                pickFile();

            }
        });

        return_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                onBackPressed();
            }
        });
    }
}
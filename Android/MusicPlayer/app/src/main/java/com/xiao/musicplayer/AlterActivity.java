package com.xiao.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.net.HttpTask;
import com.xiao.musicplayer.net.handler.AlterHttpHandler;
import com.xiao.musicplayer.util.ConstUtil;
import com.xiao.musicplayer.util.HttpRe;
import com.xiao.musicplayer.util.StringUtil;
import com.xiao.musicplayer.util.UriToPathUtil;

import java.util.HashMap;
import java.util.Map;

public class AlterActivity extends AppCompatActivity {
    int public_status;
    View info_button;
    View image_button;
    View warning_text;
    EditText username;
    EditText password;
    String image_path;

    String name;
    String pass;


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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            image_path = UriToPathUtil.getImageAbsolutePath(this, uri);
            Log.e("path", image_path);

            //*/
            map.put("phone", ConstUtil.user.getPhone());

            try {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            HttpRe.uploadFile(image_path, map, ConstUtil.ALTER_IMAGEURL);
                        } catch (Exception e) {
                            Log.e("uploadinth","wrong!!!");
                        }
                    }
                }).start();
//                onBackPressed();
            } catch (Exception e) {
                Log.e("upload","wrong!!!");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter);


        View return_button = this.findViewById(R.id.return_upload);
        image_button = this.findViewById(R.id.alter_image);
        info_button = this.findViewById(R.id.alter_info_button);
        Log.e("a", info_button.toString());

        username = this.findViewById(R.id.text_name_music_upload);
        password = this.findViewById(R.id.text_name_singer_upload);
        warning_text = this.findViewById(R.id.warning_text_upload);



        image_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                pickFile();
            }
        });

        Log.e("a","a");
        info_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.e("a","b");

                name = username.getText().toString();
                pass = password.getText().toString();
                if(name.equals("") || pass.equals("")){
                    warning_text.setVisibility(View.VISIBLE);

                }else{
                    Log.e("a", StringUtil.transformToAlter(name, ConstUtil.user.getPhone(), pass));
                    new HttpTask(ConstUtil.ALTERINFOURL, new AlterHttpHandler(AlterActivity.this, R.id.alter_info_button)).execute(StringUtil.transformToAlter(name, ConstUtil.user.getPhone(), pass));

                }
//                onBackPressed();
            }
        });
        Log.e("a","c");

        return_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                onBackPressed();
            }
        });
    }
}

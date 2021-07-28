package com.xiao.musicplayer.net.handler;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.AdminstrationActivity;
import com.xiao.musicplayer.MainActivity;
import com.xiao.musicplayer.R;
import com.xiao.musicplayer.model.User;
import com.xiao.musicplayer.util.ConstUtil;
import com.xiao.musicplayer.net.HttpMessageHandler;
import com.xiao.musicplayer.net.HttpTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginHttpHandler extends HttpMessageHandler {

    private int view;
    private AlertDialog alertDialog;

    public LoginHttpHandler(AppCompatActivity activity,@IdRes int view,AlertDialog alertDialog){
        super(activity);
        this.view = view;
        this.alertDialog = alertDialog;
    }



    @Override
    public void control(Message msg) {
        try {
            String result = msg.getData().getString(HttpTask.HTTP_REQUEST_RESULT);
            JSONObject json = new JSONObject(result);
            JSONArray dataArr = json.getJSONArray("data");
            JSONObject data = null;
            if(dataArr.length()>0){
                data = dataArr.getJSONObject(0);
            }

            String res = json.getString("res");
            switch (view) {
                case R.id.login_btn:
                    switch (res) {
                        case "false":
                            Toast.makeText(super.getActivity().get(), "账号或者密码错误" , Toast.LENGTH_LONG).show();
                            break;
                        case HttpTask.CONNECT_OR_READ_TIMEOUT:
                            Toast.makeText(super.getActivity().get(), "网络开小差儿啦~~~", Toast.LENGTH_LONG).show();
                            break;
                        case "true":
                            if(data == null){
                                Toast.makeText(super.getActivity().get(), "服务器错误，登入不成功", Toast.LENGTH_LONG).show();
                                break;
                            }
                            if(data == null){
                                Toast.makeText(super.getActivity().get(), "服务器错误，登入不成功", Toast.LENGTH_LONG).show();
                                break;
                            }
                            ConstUtil.user = new User(data.getString("phone"),data.getString("password"),data.getString("username"),data.getString("image"),data.getInt("status"));
                            Toast.makeText(super.getActivity().get(), "登录成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            if(ConstUtil.user.getStatus()==1){
                                intent.setClass(super.getActivity().get(), AdminstrationActivity.class);
                            }else{
                                intent.setClass(super.getActivity().get(), MainActivity.class);
                            }
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            super.getActivity().get().startActivity(intent);
                            break;

                    }
                    break;
                case R.id.register_btn:
                    switch (res) {
                        case "true":
                            Toast.makeText(super.getActivity().get(), "注册成功！", Toast.LENGTH_LONG).show();
                            break;
                        case HttpTask.CONNECT_OR_READ_TIMEOUT:
                            Toast.makeText(super.getActivity().get(), "网络开小差儿啦~~~", Toast.LENGTH_LONG).show();
                            break;
                        case "false":
                            Toast.makeText(super.getActivity().get(), "电话号码已被注册！" , Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }
            if (alertDialog != null)
                alertDialog.dismiss();
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}

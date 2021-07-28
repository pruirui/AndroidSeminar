package com.xiao.musicplayer.net.handler;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.model.User;
import com.xiao.musicplayer.net.HttpMessageHandler;
import com.xiao.musicplayer.net.HttpTask;
import com.xiao.musicplayer.util.ConstUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AlterHttpHandler extends HttpMessageHandler {

    private int view;

    public AlterHttpHandler(AppCompatActivity activity, @IdRes int view){
        super(activity);
        this.view = view;
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
            switch (res) {
                case HttpTask.CONNECT_OR_READ_TIMEOUT:
                    Toast.makeText(super.getActivity().get(), "网络开小差儿啦~~~", Toast.LENGTH_LONG).show();
                         break;
                case "false":
                       Toast.makeText(super.getActivity().get(), "修改用户信息失败了哎··" , Toast.LENGTH_LONG).show();
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
                     Log.e("aa", ConstUtil.user.getUsername());
                     Toast.makeText(super.getActivity().get(), "修改成功啦~！", Toast.LENGTH_LONG).show();
                     break;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}

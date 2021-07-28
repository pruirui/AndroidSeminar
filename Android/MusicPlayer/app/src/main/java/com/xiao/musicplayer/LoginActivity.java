package com.xiao.musicplayer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiao.musicplayer.model.User;
import com.xiao.musicplayer.util.ConstUtil;
import com.xiao.musicplayer.net.HttpTask;
import com.xiao.musicplayer.util.StringUtil;
import com.xiao.musicplayer.net.handler.LoginHttpHandler;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private long lastKeyDownTime;
    private EditText username;
    private EditText passWord;
    private CheckBox saveAccount;
    private boolean isSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        this.getSupportActionBar().hide();
        initView();//初始化视图控件
        readUser();//读取用户账户信息
    }



    /**
     * register 控件点击事件
     */
    private void doRegister() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") final View alert = getLayoutInflater().inflate(R.layout.register_alertdialog_layout, null);
        final EditText name = alert.findViewById(R.id.r_name_et);
        final EditText username = alert.findViewById(R.id.r_phone_et);
        final EditText password = alert.findViewById(R.id.r_password_et);
        final Button register = alert.findViewById(R.id.register_btn);
        builder.setView(alert);
        AlertDialog dialog = builder.create();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = name.getText().toString();
                String usernames = username.getText().toString();
                String passwords = password.getText().toString();

                if (StringUtil.isEmpty(names) || !StringUtil.isNumber(usernames) || StringUtil.isEmpty(passwords)){
                    Toast.makeText(LoginActivity.this, "数据格式有误，请重新输入", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this, "正在注册，请稍后...", Toast.LENGTH_LONG).show();
                    new HttpTask(ConstUtil.REGISTERURL, new LoginHttpHandler(LoginActivity.this,R.id.register_btn,dialog)).execute(StringUtil.transformToRegister(names, usernames, passwords));
                }
            }
        });
        dialog.show();

    }

    /**
     * login 控件点击事件
     */
    private void doLogin() {
        saveUser();//保存用户账户信息
        String phone = username.getText().toString();
        String password = passWord.getText().toString();
        if (StringUtil.isEmpty(password) || !StringUtil.isNumber(phone) || !StringUtil.isPhone(phone)){
            Toast.makeText(LoginActivity.this, "数据格式有误，请重新输入", Toast.LENGTH_LONG).show();
        }else{
            new HttpTask(ConstUtil.LOGINURL, new LoginHttpHandler(this, R.id.login_btn,null)).execute(StringUtil.transformToLogin(phone, password));
        }
    }

    /**
     * 读取用户账户信息
     */
    private void readUser() {
        username.setText(sharedPreferences.getString("username", ""));
        passWord.setText(sharedPreferences.getString("password", ""));
        saveAccount.setChecked(sharedPreferences.getBoolean("isSave", false));
    }

    /**
     * 保存用户账户信息
     */
    private void saveUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isSave) {
            editor.putString("username", username.getText().toString());
            editor.putString("password", passWord.getText().toString());
        } else {
            editor.putString("username", "");
            editor.putString("password", "");
        }
        editor.putBoolean("isSave", saveAccount.isChecked());
        editor.apply();
    }


    /**
     * 用于接收HttpAsyncTask的返回值并进行处理的内部类
     */
    private static class HandlerMessage extends Handler {
        WeakReference<LoginActivity> weakReference;
        int view;

        HandlerMessage(@IdRes int view, LoginActivity login) {
            super();
            this.view = view;
            weakReference = new WeakReference<>(login);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                weakReference.get().doAfterTask(view, msg.getData().getString(HttpTask.HTTP_REQUEST_RESULT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 经过HttpAsyncTask类访问服务器后的结果处理方法
     *
     * @param view 触发事件的控件id
     * @param result 服务器访问结果
     */
    private void doAfterTask(@IdRes int view, String result) throws JSONException {
        JSONObject json = new JSONObject(result);
        JSONObject data = json.getJSONObject("data");
        String ret = json.getString("ret");
        switch (view) {
            case R.id.login_btn:
                switch (ret) {
                    case "false":
                        Toast.makeText(this, "账号或者密码错误" + data, Toast.LENGTH_LONG).show();
                        break;
                    case HttpTask.CONNECT_OR_READ_TIMEOUT:
                        Toast.makeText(this, "网络开小差儿啦~~~", Toast.LENGTH_LONG).show();
                        break;
                    case "true":
                        ConstUtil.user = new User(data.getString("uid"),data.getString("password"));
                        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.register_btn:
                switch (ret) {
                    case "true":
                        Toast.makeText(this, "注册成功！", Toast.LENGTH_LONG).show();
                        break;
                    case HttpTask.CONNECT_OR_READ_TIMEOUT:
                        Toast.makeText(this, "网络开小差儿啦~~~", Toast.LENGTH_LONG).show();
                        break;
                    case "false":
                        Toast.makeText(this, "电话号码已被注册！" + data, Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }
    }
    /**
     * saveAccount 控件点击事件
     */
    private void doSaveAccount(boolean b) {
        isSave = b;
    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        username = findViewById(R.id.username_et);
        passWord = findViewById(R.id.password_et);
        saveAccount = findViewById(R.id.saveAccount_cb);
        ImageButton login = findViewById(R.id.login_btn);
        TextView register = findViewById(R.id.register_tv);

        sharedPreferences = getSharedPreferences(ConstUtil.LocalReferenceName, MODE_PRIVATE);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    username.setHint("");
                 else
                    username.setHint(R.string.username_hint);
            }
        });

        passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    passWord.setHint("");
                else
                    passWord.setHint(R.string.password_hint);
            }
        });
        saveAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                doSaveAccount(isChecked);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastKeyDownTime < 1500) {
                System.exit(0);
            }
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            lastKeyDownTime = System.currentTimeMillis();
        }
        return true;
    }
}

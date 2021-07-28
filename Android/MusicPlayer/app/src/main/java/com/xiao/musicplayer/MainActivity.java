package com.xiao.musicplayer;

import android.Manifest;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.xiao.musicplayer.basic.FixFragmentNavigator;
import com.xiao.musicplayer.net.HttpTask;
import com.xiao.musicplayer.net.handler.GetMusicListHandler;
import com.xiao.musicplayer.ui.functions.FunctionsFragment;
import com.xiao.musicplayer.ui.musiclists.MusicListFragment;
import com.xiao.musicplayer.ui.my_information.MyImformationFragment;
import com.xiao.musicplayer.ui.player.PlayerFragment;
import com.xiao.musicplayer.util.ConstUtil;
import com.xiao.musicplayer.util.MusicUtil;
import com.xiao.musicplayer.util.StringUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private long lastKeyDownTime;

    BottomNavigationView navView;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();
        initView();
        initValue();
        permissionsRequest();
    }

    private void initValue() {
        Map<String,String> map = new HashMap<>();
        map.put("u_phone",ConstUtil.user.getPhone());
        new HttpTask(ConstUtil.MUSICLISTURL,
                new GetMusicListHandler(this)).execute(StringUtil.transformToPOST(map));
    }

    void initView(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_music, R.id.navigation_music_list, R.id.navigation_function, R.id.navigation_my_information)
//                .build();
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        //fragment复用
        //获取页面容器NavHostFragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //获取导航控制器
        navController = NavHostFragment.findNavController(fragment);
        //创建自定义的Fragment导航器
        FixFragmentNavigator fragmentNavigator =
                new FixFragmentNavigator(this, fragment.getChildFragmentManager(), fragment.getId());
        //获取导航器提供者
        NavigatorProvider provider = navController.getNavigatorProvider();
        //把自定义的Fragment导航器添加进去
        provider.addNavigator(fragmentNavigator);
        //手动创建导航图
        NavGraph navGraph = initNavGraph(provider, fragmentNavigator);
        //设置导航图
        navController.setGraph(navGraph);
        //底部导航设置点击事件
        navView.setOnNavigationItemSelectedListener(item -> {
            navController.navigate(item.getItemId());
            return true;
        });
    }


    //手动创建导航图，把4个目的地添加进来
    private NavGraph initNavGraph(NavigatorProvider provider, FixFragmentNavigator fragmentNavigator) {
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //用自定义的导航器来创建目的地
        FragmentNavigator.Destination destination1 = fragmentNavigator.createDestination();
        destination1.setId(R.id.navigation_music);
        destination1.setClassName(PlayerFragment.class.getCanonicalName());
        destination1.setLabel(getResources().getString(R.string.title_player));
        navGraph.addDestination(destination1);

        FragmentNavigator.Destination destination2 = fragmentNavigator.createDestination();
        destination2.setId(R.id.navigation_music_list);
        destination2.setClassName(MusicListFragment.class.getCanonicalName());
        destination2.setLabel(getResources().getString(R.string.title_music_list));
        navGraph.addDestination(destination2);

        FragmentNavigator.Destination destination3 = fragmentNavigator.createDestination();
        destination3.setId(R.id.navigation_functions);
        destination3.setClassName(FunctionsFragment.class.getCanonicalName());
        destination3.setLabel(getResources().getString(R.string.title_function));
        navGraph.addDestination(destination3);

        FragmentNavigator.Destination destination4 = fragmentNavigator.createDestination();
        destination4.setId(R.id.navigation_my_information);
        destination4.setClassName(MyImformationFragment.class.getCanonicalName());
        destination4.setLabel(getResources().getString(R.string.title_my_info));
        navGraph.addDestination(destination4);

        navGraph.setStartDestination(R.id.navigation_music);

        return navGraph;
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
    /**
     * 重写返回键事件
     * fix: ComponentActivity.onBackPressed -> ... -> NavController.popBackStack()
     * 自定义导航器后，会引起NavController管理的回退栈出问题
     */
    @Override
    public void onBackPressed() {
        int currentId = navController.getCurrentDestination().getId();
        int startId = navController.getGraph().getStartDestination();
        //如果当前目的地不是HomeFragment，则先回到HomeFragment
        if (currentId != startId) {
            navView.setSelectedItemId(startId);
        } else {
        }
    }
    /**
     * 动态权限请求
     */
    private void permissionsRequest() {

        PermissionX.init(this).permissions(
                //写入文件
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                        scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白");
                    }
                })
                .setDialogTintColor(R.color.white, R.color.black)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            MusicUtil.getMusicData(MainActivity.this);
                        } else {
                            Toast.makeText(MainActivity.this,"您拒绝了如下权限：" + deniedList,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

package com.haomai.ywj.yobook;


import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.util.Log;
import android.view.MenuItem;

import com.haomai.ywj.yobook.base.BaseActivity;
import com.haomai.ywj.yobook.fragment.ImFragment;
import com.haomai.ywj.yobook.fragment.MainFragment;
import com.haomai.ywj.yobook.fragment.SettingFragment;
import com.haomai.ywj.yobook.service.UdpReceiverService;

public class MainActivity extends BaseActivity implements MainFragment.OnFragmentInteractionListener {

    public MainFragment mainFragment;
    public ImFragment imFragment;
    public SettingFragment settingFragment;

    public FragmentTransaction fragmentTransaction;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListenter();

        Intent intent = new Intent(this, UdpReceiverService.class);
        Log.v("WANGRUI", "准备启动service");
        startService(intent);
        Log.v("WANGRUI", "开启service...............");

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("tag", "service连接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("tag", "service连接失败");
        }
    };


    @Override
    public void initView() {
        bottomNavigationView= (BottomNavigationView) findViewById(R.id.navigation);
    }
    @Override
    public void initData() {
        fragmentTransaction = getFragmentManager().beginTransaction();
        if(mainFragment==null){
            mainFragment = MainFragment.newInstance("变电站", "1");
            imFragment = ImFragment.newInstance("变电站", "2");
            settingFragment = new SettingFragment();

            fragmentTransaction.add(R.id.frame_page,mainFragment);
            fragmentTransaction.add(R.id.frame_page,imFragment);
            fragmentTransaction.add(R.id.frame_page,settingFragment);

            fragmentTransaction.hide(mainFragment);
            fragmentTransaction.hide(settingFragment);
            fragmentTransaction.commit();

        }
    }

    @Override
    public void initListenter() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentTransaction = getFragmentManager().beginTransaction();

                switch (item.getItemId()){
                    case R.id.navigation_dashboard:
                        fragmentTransaction.show(mainFragment);
                        fragmentTransaction.hide(imFragment);
                        fragmentTransaction.hide(settingFragment);
                        break;
                    case R.id.navigation_home://导入数据
                        fragmentTransaction.hide(mainFragment);
                        fragmentTransaction.show(imFragment);
                        fragmentTransaction.hide(settingFragment);
                        break;
                    case R.id.navigation_notifications://笔记
                        fragmentTransaction.hide(mainFragment);
                        fragmentTransaction.hide(imFragment);
                        fragmentTransaction.show(settingFragment);
                        break;
                    default:
                        break;

                }
                fragmentTransaction.commit();
                return true;
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("WANGRUI", "stopservice 被调用。。。。。。。。。");
        stopService(new Intent("com.reciver1.rc"));
    }
}

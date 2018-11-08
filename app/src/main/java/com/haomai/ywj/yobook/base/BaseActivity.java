package com.haomai.ywj.yobook.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2018/9/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract void initView();
    public abstract void initData();
    public abstract void initListenter();

}

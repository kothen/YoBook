package com.haomai.ywj.yobook.api.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/9/7.
 */

public class MainAdapter extends FragmentPagerAdapter  {
    private FragmentManager fragmentManager;
    private List<Fragment> fragments;
    private List<String> titles;
    public MainAdapter(FragmentManager fm,List<Fragment> list,List<String> titles) {
        super(fm);
        fragments=list;
        fragmentManager=fm;
        this.titles=titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

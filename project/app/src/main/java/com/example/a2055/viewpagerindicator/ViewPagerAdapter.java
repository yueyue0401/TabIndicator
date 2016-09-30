package com.example.a2055.viewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    String[] mDatas;
    public ViewPagerAdapter(FragmentManager fm, String[] mDatas) {
        super(fm);
        this.mDatas = mDatas;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ViewPagerFragment.STRING_OBJ, mDatas[position]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }
}

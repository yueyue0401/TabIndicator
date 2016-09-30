package com.example.a2055.viewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity implements TabIndicator.TabtouchListener {
    private String[] mData = new String[] {"第1頁","第2頁","第3頁","第4頁","第5頁","第6頁","第7頁","第8頁"};
    private TabIndicator tabIndicator;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabIndicator = (TabIndicator) findViewById(R.id.pagerTab);

        // set the tab's name.
        tabIndicator.setTabTitle(mData);

        // set the click listener.
        tabIndicator.registerListener(this);

        setViewPager();
    }

    private void setViewPager() {
        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mData));

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabIndicator.scroll(position, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabTouch(int position) {
        pager.setCurrentItem(position);
    }
}

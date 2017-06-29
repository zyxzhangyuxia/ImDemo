package com.kykj.demoim.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kykj.demoim.R;
import com.kykj.demoim.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vectoria on 2017/6/29.
 */

public class HomeActivity  extends BaseActivity{

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabstrip)
    PagerTabStrip tabStrip ;
    @Bind(R.id.toolBar)
    Toolbar toolBar;

    ArrayList<View> viewContainter = new ArrayList<>();
    ArrayList<String> titleContainer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }


    private void init(){
        toolBar.setTitle("TestDemo");
        toolBar.setLogo(getResources().getDrawable(R.mipmap.ic_launcher));
        setSupportActionBar(toolBar);
        tabStrip.setTextSpacing(200);
        //取消tab下面的长横线
        tabStrip.setDrawFullUnderline(false);
        //设置tab的背景色
        tabStrip.setBackgroundColor(Color.GRAY);
        //设置当前tab页签的下划线颜色
        tabStrip.setTabIndicatorColor(Color.parseColor("#ff00ff"));


        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_msg, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_contacts, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.layout_live, null);
        //viewpager开始添加view
        viewContainter.add(view1);
        viewContainter.add(view2);
        viewContainter.add(view3);
        //页签项
        titleContainer.add("消息");
        titleContainer.add("联系人");
        titleContainer.add("直播");
        viewPager.setAdapter(new PagerAdapter() {

            //viewpager中的组件数量
            @Override
            public int getCount() {
                return viewContainter.size();
            }
            //滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                ((ViewPager) container).removeView(viewContainter.get(position));
            }
            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewContainter.get(position));
                return viewContainter.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @RequiresApi(api = 26)
            @Override
            public CharSequence getPageTitle(int position) {
                return titleContainer.get(position);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
            }
        });

    }



    /**
     * 启动该Activity
     * @param context
     * @param extras
     */
    public static void startActivity(Context context,Intent extras){
        Intent intent = new Intent();
        intent.setClass(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
}


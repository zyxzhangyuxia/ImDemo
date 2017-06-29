package com.kykj.demoim.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kykj.demoim.R;
import com.kykj.demoim.ui.fragment.ContentFragment;

/**
 * Created by vectoria on 2017/6/29.
 */

public class ContentFragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 1;
    private final Context context;

    public ContentFragmentAdapter(FragmentManager fragmentManager, Context context, int item_count) {
        super(fragmentManager);
        NUM_ITEMS = item_count;
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        return ContentFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.tab) + " " + String.valueOf(position + 1);
    }
}

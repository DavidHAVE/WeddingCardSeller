package com.mustika.weddingcardseller.weddingcardseller.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.mustika.weddingcardseller.weddingcardseller.fragment.OrderFragment;
import com.mustika.weddingcardseller.weddingcardseller.fragment.OrderHistoryFragment;

/**
 * Created by David on 29/09/2017.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    int mNumOfTabs;

    public FragmentPagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new OrderFragment();
        } else{
            return new OrderHistoryFragment();
        }
    }

    @Override
    public int getCount() {
//        return 3;
        return mNumOfTabs;
    }
}

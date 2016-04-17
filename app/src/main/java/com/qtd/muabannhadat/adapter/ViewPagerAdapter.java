package com.qtd.muabannhadat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Ngo Hado on 16-Apr-16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fms;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fms) {
        super(fm);
        this.fms = fms;
    }

    @Override
    public Fragment getItem(int position) {
        return fms.get(position);
    }

    @Override
    public int getCount() {
        return fms.size();
    }
}

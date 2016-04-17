package com.qtd.muabannhadat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qtd.muabannhadat.fragment.BoardFragment;
import com.qtd.muabannhadat.fragment.HomesFavoriteFragment;
import com.qtd.muabannhadat.fragment.SearchFavoriteFragment;

/**
 * Created by Dell on 4/12/2016.
 */
public class FavoritePagerAdapter extends FragmentPagerAdapter {
    private String[] tabs = {"Nhóm", "Danh sách", "Tìm kiếm"};

    public FavoritePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BoardFragment();
            case 1:
                return new HomesFavoriteFragment();
            case 2:
                return new SearchFavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}

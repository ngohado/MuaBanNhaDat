package com.qtd.muabannhadat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.constant.AppConstant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ngo Hado on 19-Apr-16.
 */
public class Tab2Fragment extends Fragment {

    @Bind(R.id.tabLayout_favorite)
    TabLayout tabLayout;

    @Bind(R.id.viewPager_favorite)
    ViewPager viewPager;

    View view;

    public Tab2Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_2_layout, container, false);
        ButterKnife.bind(this, view);
        setupViewpager();
        return view;
    }

    private void setupViewpager() {
        viewPager.setAdapter(new Tab2Adapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    class Tab2Adapter extends FragmentStatePagerAdapter {
        private String[] tabs = {"Bản Đồ", "Tìm kiếm"};

        public Tab2Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new FilterFragment();
                case 0:
                    Fragment fm = new NormalMapFragment();
                    Bundle bundle = new Bundle();
                    bundle.putDouble(AppConstant.LATITUDE, 21.012219);
                    bundle.putDouble(AppConstant.LONGITUDE, 105.8199264);
                    bundle.putBoolean("ALL", true);
                    fm.setArguments(bundle);
                    return fm;
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
}

package com.qtd.muabannhadat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.ViewPagerAdapter;
import com.qtd.muabannhadat.constant.AppConstant;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PreviewActivity extends AppCompatActivity {

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Bind(R.id.indicator)
    CirclePageIndicator indicator;

    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        String[] dataReceive = getIntent().getStringArrayExtra(AppConstant.IMAGE1);
        List<Fragment> fragments = new ArrayList<>();
        for (String string : dataReceive) {
            Fragment fm = new Fragment();
            Bundle b = new Bundle();
            b.putString(AppConstant.IMAGE1, string);
            fm.setArguments(b);
            fragments.add(fm);
        }
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }
}

package com.qtd.muabannhadat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.fragment.EarthMapFragment;
import com.qtd.muabannhadat.fragment.NormalMapFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    Fragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initData();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, mapFragment).commit();
    }

    private void initData() {
        int price = getIntent().getIntExtra(AppConstant.PRICE, 0);
        double lat = getIntent().getDoubleExtra(AppConstant.LATITUDE, 0);
        double lng = getIntent().getDoubleExtra(AppConstant.LONGITUDE, 0);
        Bundle bundle = new Bundle();
        bundle.putDouble(AppConstant.LATITUDE, lat);
        bundle.putDouble(AppConstant.LONGITUDE, lng);
        bundle.putInt(AppConstant.PRICE, price);
        if (getIntent().getIntExtra(AppConstant.MAP_TYPE, 0) == 1) {
            mapFragment = new NormalMapFragment();
        } else {
            mapFragment = new EarthMapFragment();
        }
        mapFragment.setArguments(bundle);
    }

    @OnClick(R.id.iv_back)
    public void onIconBack() {
        finish();
    }

    @OnClick(R.id.tv_back)
    public void onTextViewBack() {
        finish();
    }
}

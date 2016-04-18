package com.qtd.muabannhadat.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.ItemHomeHasHeartAdapter;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.request.RequestRepeatApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dell on 4/18/2016.
 */
public class ResultSearchActivity extends AllApartmentsActivity {
    private String status;
    private String lowPrice;
    private String highPrice;
    private String numberOfRoom;
    private String lowArea;
    private String highArea;
    private String district;
    private String street;
    private String kind1;

    @Override
    protected void initComponent() {
        Intent intent = getIntent();
        status = intent.getStringExtra(AppConstant.STATUS);
        lowPrice = intent.getStringExtra(AppConstant.PRICE_LOW_RANGE);
        int low = 0;
        if (lowPrice != "Bất kỳ") {
            if (lowPrice.contains("M")) {
                lowPrice = lowPrice.substring(1, lowPrice.indexOf("M"));
                low = (int) (Float.parseFloat(String.valueOf(lowPrice)) * 1000000);
                lowPrice = low + "";
            } else {
                lowPrice = lowPrice.substring(1);
                lowPrice = lowPrice.replace('.', '-');
                String temp[] = lowPrice.split("-");
                lowPrice = "";
                for (int i = 0; i < temp.length; i++) {
                    lowPrice += temp[i];
                }
            }
        }
        highPrice = intent.getStringExtra(AppConstant.PRICE_HIGH_RANGE);
        int high = 0;
        if (highPrice != "Bất kỳ") {
            if (highPrice.contains("M")) {
                highPrice = highPrice.substring(1, highPrice.indexOf("M"));
                high = (int) (Float.parseFloat(String.valueOf(highPrice)) * 1000000);
                highPrice = high + "";
            } else {
                highPrice = highPrice.substring(1);
                highPrice = highPrice.replace('.', '-');
                String temp[] = highPrice.split("-");
                highPrice = "";
                for (int i = 0; i < temp.length; i++) {
                    highPrice += temp[i];
                }
            }
        }
        numberOfRoom = intent.getStringExtra(AppConstant.ROOM);
        lowArea = intent.getStringExtra(AppConstant.AREA_LOW_RANGE);
        highArea = intent.getStringExtra(AppConstant.AREA_HIGH_RANGE);
        district = intent.getStringExtra(AppConstant.DISTRICT);
        street = intent.getStringExtra(AppConstant.STREET);
        kind1 = intent.getStringExtra(AppConstant.KIND);

        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put(AppConstant.STATUS, status);
            object.put(AppConstant.KIND, kind1);
            object.put(AppConstant.PRICE_LOW_RANGE, lowPrice);
            object.put(AppConstant.PRICE_HIGH_RANGE, highPrice);
            object.put(AppConstant.ROOM, numberOfRoom);
            object.put(AppConstant.AREA_LOW_RANGE, lowArea);
            object.put(AppConstant.AREA_HIGH_RANGE, highArea);
            object.put(AppConstant.DISTRICT, district);
            object.put(AppConstant.STREET, street);
            json = object.toString();
        } catch (JSONException e) {
            DebugLog.d(e.toString());
        }

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        getSupportActionBar().setTitle("Kết quả tìm kiếm");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        progressBar.setEnabled(true);

        apartments = new ArrayList<>();
        itemHomeAdapter = new ItemHomeHasHeartAdapter(apartments);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(itemHomeAdapter);

        Utility.isNetworkAvailable(this, findViewById(R.id.relativeLayout_allApartments), true);
        requestApartment = new RequestRepeatApi(this, json, ApiConstant.METHOD_FILTER, this, findViewById(R.id.relativeLayout_allApartments));
        requestApartment.executeRequest();
    }
}

package com.qtd.muabannhadat.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.ItemHomeHasHeartAdapter;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.request.RequestRepeatApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.SharedPrefUtils;
import com.qtd.muabannhadat.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dell on 4/14/2016.
 */
public class AllApartmentsActivity extends AppCompatActivity implements ResultRequestCallback{
    @Bind(R.id.recyclerView_apartments)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar_all_apartments)
    Toolbar toolbar;

    @Bind(R.id.progressBar_allApartments)
    ProgressBar progressBar;

    protected ItemHomeHasHeartAdapter itemHomeAdapter;
    protected ArrayList<Apartment> apartments;
    protected String kind = "";
    protected RequestRepeatApi requestApartment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apartments);
        ButterKnife.bind(this);
        initComponent();
    }

    protected void initComponent() {
        Intent intent = getIntent();
        kind = intent.getStringExtra(AppConstant.KIND);
        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put(AppConstant.KIND, kind);
            json = object.toString();
        } catch (JSONException e) {
            DebugLog.d(e.toString());
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        getSupportActionBar().setTitle(kind);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        apartments = new ArrayList<>();
        itemHomeAdapter = new ItemHomeHasHeartAdapter(apartments);

        recyclerView.setAdapter(itemHomeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        Utility.isNetworkAvailable(this, findViewById(R.id.relativeLayout_allApartments), true);
        requestApartment = new RequestRepeatApi(this, json, ApiConstant.METHOD_GET_ALL_APARTMENT_BY_KIND, this, findViewById(R.id.relativeLayout_allApartments));
        requestApartment.executeRequest();
    }

    @Override
    public void onSuccess(String result) {
        displayHome(result);
    }

    protected void displayHome(String result) {
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                apartments.add(new Apartment(object.getInt(AppConstant.A_ID), object.getString(AppConstant.CITY), object.getString(AppConstant.ADDRESS), object.getInt(AppConstant.PRICE), object.getString("URL")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        itemHomeAdapter.notifyItemRangeInserted(0, apartments.size());

        progressBar.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);

        BaseRequestApi requestApi = new BaseRequestApi(AllApartmentsActivity.this, String.format("{\"%s\":%d}", AppConstant.USER_ID, SharedPrefUtils.getInt(AppConstant.ID, -1)), ApiConstant.METHOD_GET_FAVORITE_HOMES, new ResultRequestCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    List<Integer> arr = new ArrayList<>();
                    for (int i = 0 ; i < jsonArray.length() ; i++) {
                        arr.add(jsonArray.getJSONObject(i).getInt(AppConstant.A_ID));
                    }

                    for (Apartment a : apartments) {
                        for (int i : arr)
                            if (a.getId() == i)
                                a.isLiked = true;
                    }

                    itemHomeAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
        requestApi.executeRequest();
    }

    @Override
    public void onFailed(String error) {
        Log.d("failed", error);
    }
}

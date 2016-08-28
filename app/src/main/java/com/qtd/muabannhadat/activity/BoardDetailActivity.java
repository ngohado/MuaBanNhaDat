package com.qtd.muabannhadat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.ItemHomeAdapter;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.request.RequestRepeatApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dell on 4/17/2016.
 */
public class BoardDetailActivity extends AppCompatActivity implements ResultRequestCallback {
    @Bind(R.id.toolbar_create_board)
    Toolbar toolbar;

    @Bind(R.id.recyclerView_board_detail)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout_board_detail)
    SwipeRefreshLayout refreshLayout;

    private ItemHomeAdapter adapter;
    private ArrayList<Apartment> apartments;
    private int boardID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        ButterKnife.bind(this);
        initComponent();
    }

    private void initComponent() {
        Intent intent = getIntent();
        boardID = intent.getIntExtra("BoardID", -1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra("BoardName"));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        apartments = new ArrayList<>();
        adapter = new ItemHomeAdapter(apartments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
    }

//    @OnClick(R.id.btn_check_board)
//    void btnCheckBoardOnClick() {
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board_detail, menu);
        return true;
    }


    @Override
    public void onSuccess(String result) {
        apartments.clear();
        adapter.notifyDataSetChanged();
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Apartment apartment = new Apartment(object.getInt("A_ID"), object.getString("Address"), object.getString("City"), object.getInt("Price"), object.getString("FirstImage"));
                apartments.add(apartment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailed(String error) {
        DebugLog.d(error);
        refreshLayout.setRefreshing(false);
    }

    public void refreshData() {
        JSONObject object = new JSONObject();
        try {
            object.put("UserID", SharedPrefUtils.getInt("ID", -1));
            object.put("BoardID", boardID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestRepeatApi requestApi = new RequestRepeatApi(this, object.toString(), ApiConstant.METHOD_GET_FAVORITE_HOMES_BY_BOARD, this, findViewById(R.id.linearLayout_board_detail));
        requestApi.executeRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshLayout.setRefreshing(true);
        refreshData();
    }
}


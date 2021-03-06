package com.qtd.muabannhadat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.ItemNotificationAdapter;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.model.Notification;
import com.qtd.muabannhadat.request.RequestRepeatApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.ServiceUtils;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dell on 4/18/2016.
 */
public class NotificationFragment extends Fragment implements ResultRequestCallback {
    @Bind(R.id.recyclerView_notification)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout_notification)
    SwipeRefreshLayout refreshLayout;

    private View view;
    private ItemNotificationAdapter adapter;
    private ArrayList<Notification> notifications;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        initComponent();
        return view;
    }

    private void initComponent() {
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        notifications = new ArrayList<>();
        adapter = new ItemNotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    public void refreshData() {
        if (!ServiceUtils.getInstance(view.getContext()).isNetworkConnected()) {
            refreshLayout.setRefreshing(false);
            return;
        }

        int id = SharedPrefUtils.getInt("ID", -1);
        if (id != -1) {
            JSONObject object = new JSONObject();
            try {
                object.put("UserID", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestRepeatApi requestRepeatApi = new RequestRepeatApi(view.getContext(), object.toString(), ApiConstant.METHOD_GET_NOTIFICATION, this, view);
            requestRepeatApi.executeRequest();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshLayout.setRefreshing(true);
        refreshData();
    }

    @Override
    public void onSuccess(String result) {
        notifications.clear();
        adapter.notifyDataSetChanged();
        try {
            JSONArray array = new JSONArray(result);
            for (int i = array.length() - 1; i >= 0; i--) {
                JSONObject object = array.getJSONObject(i);
                Notification notification = new Notification(object.getInt(AppConstant.A_ID), object.getString("ImageFirst"), object.getString(AppConstant.KIND), object.getInt(AppConstant.PRICE), object.getString("Time"));
                notifications.add(notification);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailed(String error) {
        refreshLayout.setRefreshing(false);
        DebugLog.d(error);
    }
}

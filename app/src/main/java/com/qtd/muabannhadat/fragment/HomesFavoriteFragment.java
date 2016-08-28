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
import android.widget.RelativeLayout;

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
 * Created by Dell on 4/12/2016.
 */
public class HomesFavoriteFragment extends Fragment implements ResultRequestCallback {
    @Bind(R.id.recyclerView_fragmentHomes)
    RecyclerView recyclerView;

    @Bind(R.id.layout_fragment_board)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.layoutNoHouses)
    RelativeLayout layoutNoHouses;

    private ItemHomeAdapter tileHomeAdapter;
    private ArrayList<Apartment> apartments;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homes_favorite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initComponent();
    }

    private void initComponent() {
        apartments = new ArrayList<>();
        tileHomeAdapter = new ItemHomeAdapter(apartments);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(tileHomeAdapter);

        if (apartments.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            layoutNoHouses.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            layoutNoHouses.setVisibility(View.INVISIBLE);
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    private void refreshData() {
        int id = SharedPrefUtils.getInt("ID", -1);
        if (id != -1) {
            JSONObject object = new JSONObject();
            try {
                object.put("UserID", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestRepeatApi requestRepeatApi = new RequestRepeatApi(view.getContext(), object.toString(), ApiConstant.METHOD_GET_FAVORITE_HOMES, this, view);
            requestRepeatApi.executeRequest();
        }
    }

    @Override
    public void onSuccess(String result) {
        apartments.clear();
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Apartment apartment = new Apartment(object.getInt("A_ID"), object.getString("Address"), object.getString("City"), object.getInt("Price"), object.getString("ImageFirst"));
                apartments.add(apartment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tileHomeAdapter.notifyItemRangeInserted(0, apartments.size());
        refreshLayout.setRefreshing(false);

        if (apartments.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            layoutNoHouses.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            layoutNoHouses.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFailed(String error) {
        DebugLog.d(error);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshLayout.setRefreshing(true);
        refreshData();
    }
}

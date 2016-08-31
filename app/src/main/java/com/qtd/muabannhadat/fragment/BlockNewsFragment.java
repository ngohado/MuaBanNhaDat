package com.qtd.muabannhadat.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.viewholder.ItemCategoryViewHolder;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.model.ApartmentCategory;
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

public class BlockNewsFragment extends Fragment implements ResultRequestCallback {
    private LinearLayout recyclerView;

    ProgressBar progressBar;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.block_fragment_news, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        if (Utility.isNetworkAvailable(view.getContext(), view, true))
            new RequestRepeatApi(view.getContext(), "{}", ApiConstant.METHOD_FIRST_VIEW, this, view).executeRequest();
    }

    private void initView() {
        recyclerView = (LinearLayout) view.findViewById(R.id.recyclerView);

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_fragmentNews);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }

    private void displayHomes(String temp, JSONArray list, List<Integer> arr) {
        ArrayList<Apartment> apartments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                JSONObject object = list.getJSONObject(i);
                Apartment apartment = new Apartment(object.getInt("id"), object.getString("address"), object.getString("City"), object.getInt("price"), object.getString("image"));
                for (int in : arr)
                    if (in == apartment.getId()) {
                        apartment.isLiked = true;
                        break;
                    }
                apartments.add(apartment);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ApartmentCategory category = new ApartmentCategory(temp, apartments);
        recyclerView.addView(new ItemCategoryViewHolder(getContext(), category));

        progressBar.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSuccess(final String mainResult) {
        BaseRequestApi requestApi = new BaseRequestApi(getContext(), String.format("{\"%s\":%d}", AppConstant.USER_ID, SharedPrefUtils.getInt(AppConstant.ID, 3)), ApiConstant.METHOD_GET_FAVORITE_HOMES, new ResultRequestCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    List<Integer> arr = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arr.add(jsonArray.getJSONObject(i).getInt(AppConstant.A_ID));
                    }
                    handleMainResponse(mainResult, arr);
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

    public void handleMainResponse(String mainResult, List<Integer> arr) {
        JSONArray array;
        try {
            array = new JSONArray(mainResult);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String temp = obj.getString("title");
                JSONArray list = obj.getJSONArray("home");
                if (list != null && list.length() >= 5)
                    displayHomes(temp, list, arr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String error) {
        DebugLog.d(error);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
}


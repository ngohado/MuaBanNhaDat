package com.qtd.muabannhadat.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.ItemHomeAdapter;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.model.ApartmentCategory;
import com.qtd.muabannhadat.request.RequestRepeatApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BlockNewsFragment extends Fragment implements ResultRequestCallback{
    private RecyclerView recyclerView;
    private ItemHomeAdapter itemHomeAdapter;
    private ArrayList<ApartmentCategory> listApartmentCategory = new ArrayList<>();
    private RequestRepeatApi requestRepeatApi;

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
        Utility.isNetworkAvailable(view.getContext(), view, true);
        requestRepeatApi = new RequestRepeatApi(view.getContext(), "{}", ApiConstant.METHOD_FIRST_VIEW, this,view);
        requestRepeatApi.executeRequest();
        //new GetHomesAsyncTask().execute();
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        itemHomeAdapter = new ItemHomeAdapter(listApartmentCategory);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(itemHomeAdapter);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_fragmentNews);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        progressBar.setEnabled(true);
    }



    private void displayHomes(String temp, JSONArray list) {
        ArrayList<Apartment> apartments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                JSONObject object = list.getJSONObject(i);
                Apartment apartment = new Apartment(object.getInt("id"), " ", " ", (float) object.getDouble("size"), "Ha Noi", "Dong da", "Street", object.getString("address"), object.getInt("price"), "", 2, 103f, 102f, object.getString("image"));
                apartments.add(apartment);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ApartmentCategory category = new ApartmentCategory(temp, apartments);
        listApartmentCategory.add(category);
        itemHomeAdapter.notifyDataSetChanged();
        progressBar.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSuccess(String result) {
        try {
            JSONArray array = new JSONArray(result);
            JSONObject obj = array.getJSONObject(0);
            String temp = obj.getString("title");
            JSONArray list = obj.getJSONArray("home");
            displayHomes(temp, list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String error) {
        DebugLog.d(error);
    }
}


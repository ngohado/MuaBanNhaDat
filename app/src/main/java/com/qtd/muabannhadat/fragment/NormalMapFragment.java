package com.qtd.muabannhadat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.qtd.muabannhadat.activity.ApartmentDetailActivity;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.subview.MarkerItemView;
import com.qtd.muabannhadat.util.DebugLog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ngo Hado on 15-Apr-16.
 */
public class NormalMapFragment extends BaseMapFragment {
    List<Apartment> apartments;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseRequestApi requestApi = new BaseRequestApi(getActivity(), "", ApiConstant.METHOD_GET_ALL_APARTMENTS, new ResultRequestCallback() {
            @Override
            public void onSuccess(String result) {
                handleResponse(result);
            }

            @Override
            public void onFailed(String error) {
                DebugLog.e(error);
                Toast.makeText(getActivity(), "Xảy ra lỗi , vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
        if (getArguments().getBoolean("ALL", false)) {
            requestApi.executeRequest();
        } else {
            apartments = new ArrayList<>();
            Apartment a = new Apartment();
            a.setPrice(getArguments().getInt(AppConstant.PRICE,0));
            a.setLatitude(getArguments().getDouble(AppConstant.LATITUDE, 0));
            a.setLongitude(getArguments().getDouble(AppConstant.LONGITUDE, 0));
            apartments.add(a);
            addMarkers();
        }
    }

    private void handleResponse(String result) {
        try {
            JSONArray array = new JSONArray(result);
            apartments = new ArrayList<>();
            Apartment apartment;
            for (int i = 0 ; i < array.length() ; i++) {
                apartment = new Apartment();
                apartment.setId(array.getJSONObject(i).getInt(AppConstant.A_ID));
                apartment.setLatitude(array.getJSONObject(i).getDouble(AppConstant.LATITUDE));
                apartment.setLongitude(array.getJSONObject(i).getDouble(AppConstant.LONGITUDE));
                apartment.setPrice(array.getJSONObject(i).getInt(AppConstant.PRICE));
                apartments.add(apartment);
            }
            addMarkers();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int setMapType() {
        return MAP_TYPE_NORMAL;
    }

    @Override
    public float setInitLevelZoom() {
        return 14f;
    }

    @Override
    public LatLng setInitLocation() {
        double lat = getArguments().getDouble(AppConstant.LATITUDE, 21.0031177);
        double lng = getArguments().getDouble(AppConstant.LONGITUDE, 105.8201408);
        return new LatLng(lat, lng);
    }

    @Override
    public View setViewMarker(String title) {
        if (title != null) {
            View view = new MarkerItemView(getActivity(), title);
            return view;
        }
        return null;
    }

    @Override
    public void addMarkers() {
        if (apartments == null || apartments.get(0).getLatitude() == 0)
            return;
        for (Apartment a : apartments) {
            DebugLog.i("Lat: " + a.getLatitude() + ", Lng: " + a.getLongitude());
            drawMarker(new LatLng(a.getLatitude(), a.getLongitude()), getBitmapFromView(String.valueOf(a.getPrice())));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (Apartment a : apartments) {
            if (a.getLatitude() == marker.getPosition().latitude &&
                    a.getLongitude() == marker.getPosition().longitude) {
                Intent intent = new Intent(getActivity(), ApartmentDetailActivity.class);
                intent.putExtra(ApartmentDetailActivity.ID_APARTMENT, a.getId());
                getActivity().startActivity(intent);
                return true;
            }
        }
        return false;
    }
}

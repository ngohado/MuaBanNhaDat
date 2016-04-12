package com.qtd.muabannhadat.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.ItemHomeAdapter;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.model.ApartmentCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class BlockNewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ItemHomeAdapter itemHomeAdapter;
    private ArrayList<ApartmentCategory> listApartmentCategory = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.block_fragment_news, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        if (isNetworkAvailable()) {
            new GetHomesAsyncTask().execute();
        }
//        ArrayList<Apartment> apartments = new ArrayList<>();
//        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
//        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
//        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
//        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
//        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
//        ApartmentCategory demo = new ApartmentCategory("demo", apartments);
//        itemHomeAdapter.addItem(listApartmentCategory.size(), demo);
    }


    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        itemHomeAdapter = new ItemHomeAdapter(listApartmentCategory);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(itemHomeAdapter);
    }

    class GetHomesAsyncTask extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                final String SOAP_ACTION = ApiConstant.NAME_SPACE + ApiConstant.METHOD_FIRST_VIEW;
                SoapObject request = new SoapObject(ApiConstant.NAME_SPACE, ApiConstant.METHOD_FIRST_VIEW);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transportSE = new HttpTransportSE(ApiConstant.MAIN_URL);
                transportSE.call(SOAP_ACTION, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            JSONArray array = null;
            try {
                array = new JSONArray(result);
                JSONObject obj = array.getJSONObject(0);
                String temp = obj.getString("title");
                JSONArray list = obj.getJSONArray("home");
                displayHomes(temp, list);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void displayHomes(String temp, JSONArray list) {
        ArrayList<Apartment> apartments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                JSONObject object = list.getJSONObject(i);
                Apartment apartment = new Apartment(object.getString("id"), " ", " ", (float) object.getDouble("size"), "Ha Noi", "Dong da", "Street", object.getString("address"), object.getInt("price"), "", 2, 103f, 102f);
                apartments.add(apartment);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ApartmentCategory category = new ApartmentCategory(temp, apartments);
        itemHomeAdapter.addItem(listApartmentCategory.size(), category);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


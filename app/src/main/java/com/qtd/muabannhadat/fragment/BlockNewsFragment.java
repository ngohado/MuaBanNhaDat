package com.qtd.muabannhadat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.ItemHomeAdapter;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.model.ApartmentCategory;

import java.util.ArrayList;

public class BlockNewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ItemHomeAdapter itemHomeAdapter;
    private ArrayList<ApartmentCategory> listApartmentCategory = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.block_fragment_news, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        itemHomeAdapter = new ItemHomeAdapter(listApartmentCategory);
        recyclerView.setAdapter(itemHomeAdapter);

        ArrayList<Apartment> apartments = new ArrayList<>();
        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
        apartments.add(new Apartment("12","12","12",12f,"12","12","12","12","12",1000));
        ApartmentCategory demo = new ApartmentCategory("demo", apartments);
        itemHomeAdapter.addItem(listApartmentCategory.size(), demo);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }
}

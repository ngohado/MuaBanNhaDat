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
import com.qtd.muabannhadat.model.Home;
import com.qtd.muabannhadat.model.HomeCategory;

import java.util.ArrayList;

public class BlockNewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ItemHomeAdapter itemHomeAdapter;
    private ArrayList<HomeCategory> listHomeCategory = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.block_fragment_news, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        itemHomeAdapter = new ItemHomeAdapter(listHomeCategory);
        recyclerView.setAdapter(itemHomeAdapter);

        ArrayList<Home> homes = new ArrayList<>();
        homes.add(new Home("12","12","12",12f,"12","12","12","12","12",1000));
        homes.add(new Home("12","12","12",12f,"12","12","12","12","12",1000));
        homes.add(new Home("12","12","12",12f,"12","12","12","12","12",1000));
        homes.add(new Home("12","12","12",12f,"12","12","12","12","12",1000));
        homes.add(new Home("12","12","12",12f,"12","12","12","12","12",1000));
        HomeCategory demo = new HomeCategory("demo", homes);
        itemHomeAdapter.addItem(listHomeCategory.size(), demo);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }
}

package com.qtd.muabannhadat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.viewholder.ItemHomeViewHolder;
import com.qtd.muabannhadat.model.Apartment;

import java.util.ArrayList;

/**
 * Created by Dell on 4/14/2016.
 */
public class ItemHomeAdapter extends RecyclerView.Adapter<ItemHomeViewHolder> {
    private ArrayList<Apartment> apartments;

    public ItemHomeAdapter(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
    }

    @Override
    public ItemHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_home, parent, false);
        return new ItemHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHomeViewHolder holder, int position) {
        Apartment apartment = apartments.get(position);
        holder.setupWith(apartment);
    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }
}

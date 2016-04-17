package com.qtd.muabannhadat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.viewholder.ItemHomeHasHeartViewHolder;
import com.qtd.muabannhadat.model.Apartment;

import java.util.ArrayList;

/**
 * Created by Dell on 4/17/2016.
 */
public class ItemHomeHasHeartAdapter extends RecyclerView.Adapter<ItemHomeHasHeartViewHolder> {
    private ArrayList<Apartment> apartments;

    public ItemHomeHasHeartAdapter(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
    }

    @Override
    public ItemHomeHasHeartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_home_has_heart, parent, false);
        return new ItemHomeHasHeartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHomeHasHeartViewHolder holder, int position) {
        Apartment apartment = apartments.get(position);
        holder.setupWith(apartment);
    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }
}

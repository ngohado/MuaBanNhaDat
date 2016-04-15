package com.qtd.muabannhadat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.viewholder.TileHomeViewHolder;
import com.qtd.muabannhadat.model.Apartment;

import java.util.ArrayList;

/**
 * Created by Dell on 4/14/2016.
 */
public class ItemTileHomeAdapter extends RecyclerView.Adapter<TileHomeViewHolder> {
    private ArrayList<Apartment> apartments;

    public ItemTileHomeAdapter(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
    }

    @Override
    public TileHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tile_home, parent, false);
        return new TileHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TileHomeViewHolder holder, int position) {
        Apartment apartment = apartments.get(position);
        holder.setupWith(apartment);
    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }
}

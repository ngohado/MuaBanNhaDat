package com.qtd.muabannhadat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.viewholder.ItemCategoryViewHolder;
import com.qtd.muabannhadat.model.ApartmentCategory;

import java.util.ArrayList;

/**
 * Created by Dell on 4/9/2016.
 */
public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryViewHolder> {
    private ArrayList<ApartmentCategory> list;
    public ItemCategoryAdapter(ArrayList<ApartmentCategory> homeCategories) {
        this.list = homeCategories;
    }

    @Override
    public ItemCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_category_home, parent, false);
        return new ItemCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemCategoryViewHolder holder, int position) {
        ApartmentCategory apartmentCategory = list.get(position);
        holder.setupWith(apartmentCategory);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

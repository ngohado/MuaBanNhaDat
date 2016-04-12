package com.qtd.muabannhadat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.viewholder.CategoryViewHolder;
import com.qtd.muabannhadat.model.ApartmentCategory;

import java.util.ArrayList;

/**
 * Created by Dell on 4/9/2016.
 */
public class ItemHomeAdapter extends RecyclerView.Adapter<CategoryViewHolder>{
    private ArrayList<ApartmentCategory> list;

    public ItemHomeAdapter(ArrayList<ApartmentCategory> homeCategories) {
        this.list = homeCategories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_home, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        ApartmentCategory apartmentCategory = list.get(position);
        holder.setupWith(apartmentCategory);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(int position, ApartmentCategory apartmentCategory){
        list.add(position, apartmentCategory);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
}

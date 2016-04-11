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
//        holder.getTileHome0().getIvHome().setImageResource(list.get(position));
        holder.getTileHome0().getTvCost().setText(list.get(position).getApartments().get(0).getPrice());
        holder.getTileHome0().getTvAddress().setText(list.get(position).getApartments().get(0).getAddress());
        holder.getTileHome0().getTvCity().setText(list.get(position).getApartments().get(0).getCity());

//        holder.getTileHome1().getIvHome().setImageResource(list.get(position));
        holder.getTileHome1().getTvCost().setText(list.get(position).getApartments().get(1).getPrice());
        holder.getTileHome1().getTvAddress().setText(list.get(position).getApartments().get(1).getAddress());
        holder.getTileHome1().getTvCity().setText(list.get(position).getApartments().get(1).getCity());

//  holder.getTileHome2().getIvHome().setImageResource(list.get(position));
        holder.getTileHome2().getTvCost().setText(list.get(position).getApartments().get(2).getPrice());
        holder.getTileHome2().getTvAddress().setText(list.get(position).getApartments().get(2).getAddress());
        holder.getTileHome2().getTvCity().setText(list.get(position).getApartments().get(2).getCity());

//       holder.getTileHome3().getIvHome().setImageResource(list.get(position));
        holder.getTileHome3().getTvCost().setText(list.get(position).getApartments().get(3).getPrice());
        holder.getTileHome3().getTvAddress().setText(list.get(position).getApartments().get(3).getAddress());
        holder.getTileHome3().getTvCity().setText(list.get(position).getApartments().get(3).getCity());

//        holder.getTileHome4().getIvHome().setImageResource(list.get(position));
        holder.getTileHome4().getTvCost().setText(list.get(position).getApartments().get(3).getPrice());
        holder.getTileHome4().getTvAddress().setText(list.get(position).getApartments().get(3).getAddress());
        holder.getTileHome4().getTvCity().setText(list.get(position).getApartments().get(3).getCity());

        holder.getBtnSeeAll().setText("Xem toàn bộ " + list.get(position).getName());
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

package com.qtd.muabannhadat.adapter.viewholder;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.model.Apartment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 4/14/2016.
 */
public class ItemHomeViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.imv_tileHome)
    ImageView imageView;

    @Bind(R.id.tv_costTileHome)
    TextView tvCost;

    @Bind(R.id.tv_address_tileHome)
    TextView tvAddress;

    @Bind(R.id.tv_cityTileHome)
    TextView tvCity;

    protected View view;

    public ItemHomeViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.imv_tileHome)
    void ImvOnClick() {

    }

    public void setupWith(Apartment apartment) {
        Glide.with(view.getContext()).load(Uri.parse(apartment.getImageFirst())).into(imageView);
        tvAddress.setText(apartment.getAddress());
        tvCity.setText(apartment.getCity());
        tvCost.setText(String.valueOf(apartment.getPrice()));
    }

}

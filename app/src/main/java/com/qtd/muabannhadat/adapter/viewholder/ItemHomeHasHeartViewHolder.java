package com.qtd.muabannhadat.adapter.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.activity.ApartmentDetailActivity;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.model.Apartment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 4/17/2016.
 */
public class ItemHomeHasHeartViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.imv_tileHome)
    ImageView imageView;

    @Bind(R.id.tv_costTileHome)
    TextView tvCost;

    @Bind(R.id.tv_address_tileHome)
    TextView tvAddress;

    @Bind(R.id.tv_cityTileHome)
    TextView tvCity;

    @Bind(R.id.imv_favorite)
    ImageView imageViewHeart;

    int id = 0;
    protected View view;

    public ItemHomeHasHeartViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.imv_tileHome)
    void ImvOnClick() {
        Intent intent = new Intent(view.getContext(), ApartmentDetailActivity.class);
        intent.putExtra(AppConstant.A_ID, id);
        view.getContext().startActivity(intent);
    }

    public void setupWith(Apartment apartment) {
        id = apartment.getId();
        Glide.with(view.getContext()).load(Uri.parse(apartment.getImageFirst())).into(imageView);
        tvAddress.setText(apartment.getAddress());
        tvCity.setText(apartment.getCity());
        tvCost.setText(String.valueOf(apartment.getPrice()));
    }

}

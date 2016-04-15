package com.qtd.muabannhadat.adapter.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.activity.AllApartmentsActivity;
import com.qtd.muabannhadat.activity.ApartmentDetailActivity;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.model.ApartmentCategory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 4/9/2016.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_title)
    public TextView tvTitle;

    @Bind(R.id.imv1)
    public ImageView ivHome1;

    @Bind(R.id.txtCost1)
    public TextView tvCost1;

    @Bind(R.id.txtAddress1)
    public TextView tvAddress1;

    @Bind(R.id.txtCity1)
    public TextView tvCity1;

    @Bind(R.id.imv2)
    public ImageView ivHome2;

    @Bind(R.id.txtCost2)
    public TextView tvCost2;

    @Bind(R.id.txtAddress2)
    public TextView tvAddress2;

    @Bind(R.id.txtCity2)
    public TextView tvCity2;

    @Bind(R.id.imv3)
    public ImageView ivHome3;

    @Bind(R.id.txtCost3)
    public TextView tvCost3;

    @Bind(R.id.txtAddress3)
    public TextView tvAddress3;

    @Bind(R.id.txtCity3)
    public TextView tvCity3;

    @Bind(R.id.imv4)
    public ImageView ivHome4;

    @Bind(R.id.txtCost4)
    public TextView tvCost4;

    @Bind(R.id.txtAddress4)
    public TextView tvAddress4;

    @Bind(R.id.txtCity4)
    public TextView tvCity4;

    @Bind(R.id.imv5)
    public ImageView ivHome;

    @Bind(R.id.txtCost5)
    public TextView tvCost;

    @Bind(R.id.txtAddress5)
    public TextView tvAddress;

    @Bind(R.id.txtCity5)
    public TextView tvCity;

    @Bind(R.id.imv_favorite1)
    public ImageView imvFavorite1;

    @Bind(R.id.imv_favorite2)
    public ImageView imvFavorite2;

    @Bind(R.id.imv_favorite3)
    public ImageView imvFavorite3;

    @Bind(R.id.imv_favorite4)
    public ImageView imvFavorite4;

    @Bind(R.id.imv_favorite5)
    public ImageView imvFavorite5;
    @Bind(R.id.btnSeeAll)
    public Button btnSeeAll;

    View view;
    ApartmentCategory apartmentCategory;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void setupWith(ApartmentCategory a) {
        apartmentCategory = a;
        tvTitle.setText(a.getName());
        btnSeeAll.setText("Xem tất cả " + a.getName());

        tvCost.setText(String.valueOf(a.getApartments().get(0).getPrice()));
        tvCost1.setText(String.valueOf(a.getApartments().get(1).getPrice()));
        tvCost2.setText(String.valueOf(a.getApartments().get(2).getPrice()));
        tvCost3.setText(String.valueOf(a.getApartments().get(3).getPrice()));
        tvCost4.setText(String.valueOf(a.getApartments().get(4).getPrice()));

        tvAddress.setText(a.getApartments().get(0).getAddress());
        tvAddress1.setText(a.getApartments().get(1).getAddress());
        tvAddress2.setText(a.getApartments().get(2).getAddress());
        tvAddress3.setText(a.getApartments().get(3).getAddress());
        tvAddress4.setText(a.getApartments().get(4).getAddress());

        tvCity.setText(a.getApartments().get(0).getCity());
        tvCity1.setText(a.getApartments().get(1).getCity());
        tvCity2.setText(a.getApartments().get(2).getCity());
        tvCity3.setText(a.getApartments().get(3).getCity());
        tvCity4.setText(a.getApartments().get(4).getCity());

        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(0).getImageFirst())).into(ivHome);
        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(1).getImageFirst())).into(ivHome1);
        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(2).getImageFirst())).into(ivHome2);
        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(3).getImageFirst())).into(ivHome3);
        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(4).getImageFirst())).into(ivHome4);
    }


    @OnClick(R.id.imv1)
    public void ImageView1OnClick() {
        startApartmentDetailActivity(apartmentCategory.getApartments().get(0));
    }

    @OnClick(R.id.imv2)
    public void ImageView2OnClick() {
        startApartmentDetailActivity(apartmentCategory.getApartments().get(1));
    }

    @OnClick(R.id.imv3)
    public void ImageView3OnClick() {
        startApartmentDetailActivity(apartmentCategory.getApartments().get(2));
    }

    @OnClick(R.id.imv4)
    public void ImageView4OnClick() {
        startApartmentDetailActivity(apartmentCategory.getApartments().get(3));
    }

    @OnClick(R.id.imv5)
    public void ImageView5OnClick() {
        startApartmentDetailActivity(apartmentCategory.getApartments().get(4));
    }

    @OnClick(R.id.imv_favorite1)
    public void ImvFavorite1OnClick() {
        imvFavorite1.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));
    }

    @OnClick(R.id.imv_favorite2)
    public void ImvFavorite2OnClick() {
        imvFavorite2.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));

    }

    @OnClick(R.id.imv_favorite3)
    public void ImvFavorite3OnClick() {
        imvFavorite3.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));

    }

    @OnClick(R.id.imv_favorite4)
    public void ImvFavorite4OnClick() {
        imvFavorite4.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));

    }

    @OnClick(R.id.imv_favorite5)
    public void ImvFavorite5OnClick() {
//        imvFavorite5.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));
        Intent intent = new Intent(view.getContext(), AllApartmentsActivity.class);
        intent.putExtra("Kind", apartmentCategory.getName());
        view.getContext().startActivity(intent);
    }

    @OnClick(R.id.btnSeeAll)
    public void BtnSeeAllOnClick() {

    }

    private void startApartmentDetailActivity(Apartment apartment) {
        Intent intent = new Intent(view.getContext(), ApartmentDetailActivity.class);
        intent.putExtra(ApartmentDetailActivity.ID_APARTMENT, apartment.getId());
        view.getContext().startActivity(intent);
    }
}

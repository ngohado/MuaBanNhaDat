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
import com.qtd.muabannhadat.activity.LoginActivity;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.dialog.BoardListDialog;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.model.ApartmentCategory;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 4/9/2016.
 */
public class ItemCategoryViewHolder extends RecyclerView.ViewHolder {

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

    public ItemCategoryViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void setupWith(ApartmentCategory a) {
        apartmentCategory = a;
        tvTitle.setText(a.getName());
        btnSeeAll.setText("Xem tất cả " + a.getName());

        tvCost.setText(String.valueOf(a.getApartments().get(4).getPrice()));
        tvCost1.setText(String.valueOf(a.getApartments().get(0).getPrice()));
        tvCost2.setText(String.valueOf(a.getApartments().get(1).getPrice()));
        tvCost3.setText(String.valueOf(a.getApartments().get(2).getPrice()));
        tvCost4.setText(String.valueOf(a.getApartments().get(3).getPrice()));

        tvAddress.setText(a.getApartments().get(4).getAddress());
        tvAddress1.setText(a.getApartments().get(0).getAddress());
        tvAddress2.setText(a.getApartments().get(1).getAddress());
        tvAddress3.setText(a.getApartments().get(2).getAddress());
        tvAddress4.setText(a.getApartments().get(3).getAddress());

        tvCity.setText(a.getApartments().get(4).getCity());
        tvCity1.setText(a.getApartments().get(0).getCity());
        tvCity2.setText(a.getApartments().get(1).getCity());
        tvCity3.setText(a.getApartments().get(2).getCity());
        tvCity4.setText(a.getApartments().get(3).getCity());

        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(4).getImageFirst())).into(ivHome);
        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(0).getImageFirst())).into(ivHome1);
        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(1).getImageFirst())).into(ivHome2);
        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(2).getImageFirst())).into(ivHome3);
        Glide.with(view.getContext()).load(Uri.parse(a.getApartments().get(3).getImageFirst())).into(ivHome4);

        if (apartmentCategory.getApartments().get(0).isLiked)
            imvFavorite1.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));

        if (apartmentCategory.getApartments().get(1).isLiked)
            imvFavorite2.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));

        if (apartmentCategory.getApartments().get(2).isLiked)
            imvFavorite3.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));

        if (apartmentCategory.getApartments().get(3).isLiked)
            imvFavorite4.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));

        if (apartmentCategory.getApartments().get(4).isLiked)
            imvFavorite5.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));
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
        if (SharedPrefUtils.getInt(AppConstant.ID, -1) == -1)
            startLogin();
        if (apartmentCategory.getApartments().get(0).isLiked)
            return;
        openDialog(0);
    }
    BoardListDialog dialog ;
    private void openDialog(final int i) {
        dialog = new BoardListDialog(view.getContext(), apartmentCategory.getApartments().get(i).getId(), new ResultRequestCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject o = new JSONObject(result);
                    if (!o.getString("Respone").equalsIgnoreCase("Success")) {

                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (i) {
                    case 0:
                        imvFavorite1.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));
                        apartmentCategory.getApartments().get(i).isLiked = true;
                        break;
                    case 1:
                        imvFavorite2.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));
                        apartmentCategory.getApartments().get(i).isLiked = true;
                        break;
                    case 2:
                        imvFavorite3.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));
                        apartmentCategory.getApartments().get(i).isLiked = true;
                        break;
                    case 3:
                        imvFavorite4.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));
                        apartmentCategory.getApartments().get(i).isLiked = true;
                        break;
                    case 4:
                        imvFavorite5.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_favorite_white_36dp));
                        apartmentCategory.getApartments().get(i).isLiked = true;
                        break;
                }
                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onFailed(String error) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void startLogin() {
        view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
    }

    @OnClick(R.id.imv_favorite2)
    public void ImvFavorite2OnClick() {
        if (SharedPrefUtils.getInt(AppConstant.ID, -1) == -1)
            startLogin();
        if (apartmentCategory.getApartments().get(1).isLiked)
            return;
        openDialog(1);
    }

    @OnClick(R.id.imv_favorite3)
    public void ImvFavorite3OnClick() {
        if (SharedPrefUtils.getInt(AppConstant.ID, -1) == -1)
            startLogin();
        if (apartmentCategory.getApartments().get(2).isLiked)
            return;
        openDialog(2);
    }

    @OnClick(R.id.imv_favorite4)
    public void ImvFavorite4OnClick() {
        if (SharedPrefUtils.getInt(AppConstant.ID, -1) == -1)
            startLogin();
        if (apartmentCategory.getApartments().get(3).isLiked)
            return;
        openDialog(3);
    }

    @OnClick(R.id.imv_favorite5)
    public void ImvFavorite5OnClick() {
        if (SharedPrefUtils.getInt(AppConstant.ID, -1) == -1)
            startLogin();
        if (apartmentCategory.getApartments().get(4).isLiked)
            return;
        openDialog(4);
    }

    @OnClick(R.id.btnSeeAll)
    public void BtnSeeAllOnClick() {
        Intent intent = new Intent(view.getContext(), AllApartmentsActivity.class);
        intent.putExtra("Kind", apartmentCategory.getName());
        view.getContext().startActivity(intent);
    }

    private void startApartmentDetailActivity(Apartment apartment) {
        Intent intent = new Intent(view.getContext(), ApartmentDetailActivity.class);
        intent.putExtra(ApartmentDetailActivity.ID_APARTMENT, apartment.getId());
        view.getContext().startActivity(intent);
    }
}

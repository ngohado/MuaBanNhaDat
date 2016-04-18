package com.qtd.muabannhadat.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.activity.ApartmentDetailActivity;
import com.qtd.muabannhadat.model.Notification;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 4/18/2016.
 */
public class NotificationViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.imv_noti)
    ImageView imv;

    @Bind(R.id.tv_content_noti)
    TextView tvContent;

    @Bind(R.id.tv_time_noti)
    TextView tvTime;

    private View view;
    private int idApartment = 0;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        view = itemView;
    }

    @OnClick(R.id.layout_noti)
    void onClick() {
        Intent intent = new Intent(view.getContext(), ApartmentDetailActivity.class);
        intent.putExtra(ApartmentDetailActivity.ID_APARTMENT, idApartment);
        view.getContext().startActivity(intent);
    }

    public void setupWith(Notification notification) {
        Glide.with(view.getContext()).load(notification.getImage()).into(imv);
        tvContent.setText(notification.getContent());
        tvTime.setText(notification.getPrice());
        idApartment = notification.getIdApartment();
    }
}

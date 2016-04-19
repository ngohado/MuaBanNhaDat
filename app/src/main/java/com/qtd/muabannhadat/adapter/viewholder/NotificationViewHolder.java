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
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.StringUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    @Bind(R.id.tv_price_noti)
    TextView tvPrice;

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
        tvPrice.setText(NumberFormat.getNumberInstance(Locale.GERMAN).format(notification.getPrice()) + "$");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a", Locale.US);
        try {
            DebugLog.d(StringUtil.getTimeAgo(simpleDateFormat.parse(notification.getTime()).getTime()));
            tvTime.setText(StringUtil.getTimeAgo(simpleDateFormat.parse(notification.getTime()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        idApartment = notification.getIdApartment();
    }
}

package com.qtd.muabannhadat.subview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hado on 23-Aug-16.
 */
public class SuggestView extends RelativeLayout {
    Context context;

    @Bind(R.id.iv_suggest)
    ImageView imageView;

    @Bind(R.id.tv_price)
    TextView tvPrice;

    public SuggestView(Context context, String url, String price) {
        super(context);
        this.context = context;
        inflate(context, R.layout.item, this);
        ButterKnife.bind(this);
        setupWith(url, price);
    }

    public void setupWith(String url, String price) {
        Glide.with(context).load(url).into(imageView);
        tvPrice.setText(price);
    }

}
package com.qtd.muabannhadat.subview;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qtd.muabannhadat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ngo Hado on 15-Apr-16.
 */
public class MarkerItemView extends RelativeLayout {

    @Bind(R.id.tv_price)
    TextView tvPrice;

    public MarkerItemView(Context context, String title) {
        super(context);
        inflate(context, R.layout.mark_item, this);
        ButterKnife.bind(this);
        tvPrice.setText("$" + title);
    }
}

package com.qtd.muabannhadat.subview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.model.Home;
import com.qtd.muabannhadat.util.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ngo Hado on 12-Apr-16.
 */
public class DescriptionView extends LinearLayout{
    Context context;

    @Bind(R.id.tv_status)
    TextView tvStatus;

    @Bind(R.id.tv_status)
    TextView tvKind;

    @Bind(R.id.tv_status)
    TextView tvSize;

    @Bind(R.id.tv_status)
    TextView tvStreet;

    @Bind(R.id.tv_status)
    TextView tvDistrict;

    @Bind(R.id.tv_status)
    TextView tvAddress;

    public DescriptionView(Context context) {
        super(context);
        this.context = context;
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.description_layout, (ViewGroup) getRootView(), false);
        ButterKnife.bind(view);
    }

    public void setupWith(Home home) {
        if (home == null)
            return;
        StringUtil.displayText(home.getStatus(), tvStatus);
        StringUtil.displayText(home.getKind(), tvKind);
        StringUtil.displayText(home.getStatus(), tvSize);
        StringUtil.displayText(home.getStatus(), tvStreet);
        StringUtil.displayText(home.getStatus(), tvDistrict);
        StringUtil.displayText(home.getStatus(), tvAddress);
    }

}

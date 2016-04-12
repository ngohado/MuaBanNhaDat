package com.qtd.muabannhadat.subview;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.util.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ngo Hado on 12-Apr-16.
 */
public class DescriptionView extends LinearLayout {
    Context context;

    @Bind(R.id.tv_status_des)
    TextView tvStatus;

    @Bind(R.id.tv_kind_des)
    TextView tvKind;

    @Bind(R.id.tv_size_des)
    TextView tvSize;

    @Bind(R.id.tv_street_des)
    TextView tvStreet;

    @Bind(R.id.tv_district_des)
    TextView tvDistrict;

    @Bind(R.id.tv_address_des)
    TextView tvAddress;

    @Bind(R.id.tv_rooms_des)
    TextView tvRooms;

    public DescriptionView(Context context) {
        super(context);
        this.context = context;
        inflate(context, R.layout.description_layout, this);
        ButterKnife.bind(this);
    }

    public void setupWith(Apartment home) {
        if (home == null)
            return;
        StringUtil.displayText(home.getStatus(), tvStatus);
        StringUtil.displayText(home.getKind(), tvKind);
        StringUtil.displayText(String.valueOf(home.getArea()), tvSize);
        StringUtil.displayText(String.valueOf(home.getNumberOfRoom()), tvRooms);
        StringUtil.displayText(home.getStreet(), tvStreet);
        StringUtil.displayText(home.getDistrict(), tvDistrict);
        StringUtil.displayText(home.getAddress(), tvAddress);
    }

}

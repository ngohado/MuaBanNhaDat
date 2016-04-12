package com.qtd.muabannhadat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.subview.DescriptionView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApartmentDetailActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_kind)
    TextView tvKind;

    @Bind(R.id.tv_status)
    TextView tvStatus;

    @Bind(R.id.tv_price)
    TextView tvPrice;

    @Bind(R.id.tv_size)
    TextView tvSize;

    @Bind(R.id.tv_address)
    TextView tvAddress;

    @Bind(R.id.tv_city)
    TextView tvCity;

    @Bind(R.id.tv_intro)
    TextView tvIntro;

    @Bind(R.id.layout_more)
    LinearLayout layoutMore;

    DescriptionView descriptionView;

    int idApartment;

    boolean isShowDescriptionView = false;

    public static final String DATA = "DATA";

    public static final String ID_APARTMENT = "DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getBundleData();
        initData();
    }

    private void getBundleData() {
        Bundle bundle = getIntent().getBundleExtra(DATA);
        if (bundle == null) {
            idApartment = -1;
            return;
        }
        idApartment = getIntent().getBundleExtra(DATA).getInt(ID_APARTMENT, -1);
    }

    private void initData() {
        if (idApartment != -1) {
            descriptionView = new DescriptionView(this);
            descriptionView.setupWith(new Apartment(1,"Bán", "Chung cư", 30, "Hà Nội", "Đống Đa", "Thái Hà", "", 200000, "agaga", 2, 36, 14));
            layoutMore.addView(descriptionView, 2);
        }
    }

    @OnClick(R.id.tv_description_title)
    public void onTextViewDescriptionClicked() {
        if (descriptionView == null)
            return;

        if (isShowDescriptionView) {
            descriptionView.setVisibility(View.VISIBLE);
        } else {
            descriptionView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_loadmore)
    public void onButtonLoadMoreClicked() {

    }

    @OnClick(R.id.tv_direction)
    public void onButtonDirectionClicked() {

    }

    @OnClick(R.id.tv_mapview)
    public void onButtonMapViewClicked() {

    }

    @OnClick(R.id.tv_earthview)
    public void onButtonEarthViewClicked() {

    }

    @OnClick(R.id.iv_love)
    public void onButtonFavoriteClicked() {

    }

    @OnClick(R.id.iv_share)
    public void onButtonShareClicked() {

    }

    @OnClick(R.id.iv_back)
    public void onButtonBackClicked() {
        finish();
    }
}

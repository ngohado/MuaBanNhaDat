package com.qtd.muabannhadat.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.request.GetApartmentInfoRequest;
import com.qtd.muabannhadat.subview.DescriptionView;
import com.qtd.muabannhadat.subview.UserContactView;
import com.qtd.muabannhadat.util.NetworkUtil;
import com.qtd.muabannhadat.util.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApartmentDetailActivity extends AppCompatActivity implements ResultRequestCallback<Apartment> {
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

    UserContactView primaryUser;
    UserContactView user1;
    UserContactView user2;

    int idApartment;

    boolean isShowDescriptionView = false;

    public static final String DATA = "DATA";

    public static final String ID_APARTMENT = "ID_APARTMENT";

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initView();
        getBundleData();
        initData();
    }

    private void initView() {
        descriptionView = new DescriptionView(this);
        primaryUser = new UserContactView(this);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Đang tải...");

        layoutMore.addView(descriptionView, 2);
        layoutMore.addView(primaryUser);
    }

    private void getBundleData() {
        idApartment = getIntent().getIntExtra(ID_APARTMENT, 1);
    }

    private void initData() {
        requestGetData();
    }

    public void requestGetData() {
        if (!NetworkUtil.getInstance(this).isNetworkConnected()) {
            Toast.makeText(this, "Kiểm tra lại kết nối mạng...", Toast.LENGTH_SHORT).show();
            return;
        }
        GetApartmentInfoRequest request = new GetApartmentInfoRequest(this, idApartment, this);
        request.executeRequest();
        dialog.show();
    }

    @OnClick(R.id.layout_header)
    public void onImageViewClicked() {
        //TODO: open activity show image

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

    @Override
    public void onSuccess(Apartment result) {
        fillData(result);
        dialog.dismiss();
    }

    private void fillData(Apartment result) {
        StringUtil.displayText(result.getAddress(), tvAddress);
        StringUtil.displayText(result.getDistrict() + ", " + result.getCity(), tvCity);
        StringUtil.displayText(result.getKind(), tvKind);
        StringUtil.displayText(result.getStatus(), tvStatus);
        StringUtil.displayText("$" + result.getPrice(), tvPrice);
        StringUtil.displayText("Diện tích " + result.getArea() + "m2", tvSize);
        StringUtil.displayText(result.getDescribe(), tvIntro);
        primaryUser.setupWith(result.getUser());
    }

    @Override
    public void onFailed(String error) {

        dialog.dismiss();
    }
}

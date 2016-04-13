package com.qtd.muabannhadat.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.subview.DescriptionView;
import com.qtd.muabannhadat.subview.UserContactView;
import com.qtd.muabannhadat.util.NetworkUtil;
import com.qtd.muabannhadat.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApartmentDetailActivity extends AppCompatActivity implements ResultRequestCallback {
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

    @Bind(R.id.tv_content)
    TextView tvIntro;

    @Bind(R.id.layout_more)
    LinearLayout layoutMore;

    @Bind(R.id.btn_loadmore)
    Button btnLoadMore;

    DescriptionView descriptionView;

    UserContactView primaryUser;
    UserContactView user1;
    UserContactView user2;

    int idApartment;

    boolean isShowDescriptionView = false;

    boolean isShowFull = false;

    public static final String ID_APARTMENT = "ID_APARTMENT";

    ProgressDialog dialog;

    BaseRequestApi request;

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
        request = new BaseRequestApi(this, String.format("{\"ID\":%d}", idApartment), ApiConstant.METHOD_APARTMENT, this);
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
            isShowDescriptionView = false;
        } else {
            descriptionView.setVisibility(View.GONE);
            isShowDescriptionView = true;
        }
    }

    @OnClick(R.id.btn_loadmore)
    public void onButtonLoadMoreClicked() {
        if (isShowFull) {
            tvIntro.setMaxLines(2);
            btnLoadMore.setText("ĐỌC THÊM");
            isShowFull = false;
        } else {
            tvIntro.setMaxLines(10);
            btnLoadMore.setText("RÚT GỌN");
            isShowFull = true;
        }
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

    private void fillData(Apartment result) {
        StringUtil.displayText(result.getAddress(), tvAddress);
        StringUtil.displayText(result.getDistrict() + ", " + result.getCity(), tvCity);
        StringUtil.displayText(result.getKind(), tvKind);
        StringUtil.displayText(result.getStatus(), tvStatus);
        StringUtil.displayText("$" + result.getPrice(), tvPrice);
        StringUtil.displayText("Diện tích " + result.getArea() + "m2", tvSize);
        StringUtil.displayText(result.getDescribe(), tvIntro);
        descriptionView.setupWith(result);
        primaryUser.setupWith(result.getUser());
    }

    @Override
    public void onSuccess(String result) {
        handleResponse(result);
        dialog.dismiss();
    }

    @Override
    public void onFailed(String error) {
        dialog.dismiss();
    }

    private void handleResponse(String s) {
        try {
            JSONObject object = new JSONObject(s);
            Apartment response = new Apartment();
            response.setAddress(object.getString("Address"));
            response.setId(object.getInt("A_ID"));
            response.setStatus(object.getString("Status"));
            response.setKind(object.getString("Kind"));
            response.setArea(object.getLong("Size"));
            response.setCity(object.getString("City"));
            response.setDistrict(object.getString("District"));
            response.setStreet(object.getString("Street"));
            response.setPrice(object.getInt("Price"));
            response.setDescribe(object.getString("Describe"));
            response.setNumberOfRoom(object.getInt("Room"));
            response.setLatitude(object.getLong("Latitude"));
            response.setLongitude(object.getLong("Longitude"));
            response.getUser().setId(object.getInt("UserID"));
            response.getUser().setUserName(object.getString("Username"));
            response.getUser().setName(object.getString("Name"));
            response.getUser().setDateOfBirth(object.getString("DOB"));
            response.getUser().setPhone(object.getString("Telephone"));
            fillData(response);
        } catch (JSONException e) {

        }
    }

    @Override
    protected void onDestroy() {
        request.close();
        super.onDestroy();
    }
}

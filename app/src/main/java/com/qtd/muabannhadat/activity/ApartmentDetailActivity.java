package com.qtd.muabannhadat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.model.Apartment;
import com.qtd.muabannhadat.model.User;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.subview.DescriptionView;
import com.qtd.muabannhadat.subview.UserContactView;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.NetworkUtil;
import com.qtd.muabannhadat.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApartmentDetailActivity extends AppCompatActivity implements ResultRequestCallback {
    @Bind(R.id.toolbar_apartment_detail)
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

    @Bind(R.id.iv_apartment)
    ImageView ivApartment;

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

    Intent openMapActivity;

    Apartment response;

    String[] urls = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        openMapActivity = new Intent(this, MapActivity.class);
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
        addDefaultUser();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addDefaultUser() {
        User u1 = new User("Trần Hồng Quân", "01675141995", "http://www.textbookrecycling.com/images/female.png");
        User u2 = new User("Hoa Anh Tú", "0942899531", "https://cdn1.iconfinder.com/data/icons/user-pictures/100/male3-512.png");
        user1 = new UserContactView(this, u1);
        user2 = new UserContactView(this, u2);
        layoutMore.addView(user1);
        layoutMore.addView(user2);
    }

    private void getBundleData() {
        idApartment = getIntent().getIntExtra(ID_APARTMENT, 7);
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
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra(AppConstant.IMAGE1, urls);
        startActivity(intent);
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
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + response.getLatitude() + "," + response.getLongitude() + "&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @OnClick(R.id.tv_mapview)
    public void onButtonMapViewClicked() {
        openMapActivity.putExtra(AppConstant.MAP_TYPE, 1);
        startActivity(openMapActivity);
    }

    @OnClick(R.id.tv_earthview)
    public void onButtonEarthViewClicked() {
        openMapActivity.putExtra(AppConstant.MAP_TYPE, 0);
        startActivity(openMapActivity);
    }

    @OnClick(R.id.iv_love)
    public void onButtonFavoriteClicked() {

    }

    @OnClick(R.id.iv_share)
    public void onButtonShareClicked() {

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

            urls[0] = object.getString(AppConstant.IMAGE1);
            urls[1] = object.getString(AppConstant.IMAGE2);
            urls[2] = object.getString(AppConstant.IMAGE3);
            urls[3] = object.getString(AppConstant.IMAGE4);
            urls[4] = object.getString(AppConstant.IMAGE5);

            response = new Apartment();
            response.setAddress(object.getString(AppConstant.ADDRESS));
            response.setId(object.getInt(AppConstant.A_ID));
            response.setStatus(object.getString(AppConstant.STATUS));
            response.setKind(object.getString(AppConstant.KIND));
            response.setArea(object.getLong(AppConstant.SIZE));
            response.setCity(object.getString(AppConstant.CITY));
            response.setDistrict(object.getString(AppConstant.DISTRICT));
            response.setStreet(object.getString(AppConstant.STREET));
            response.setPrice(object.getInt(AppConstant.PRICE));
            response.setDescribe(object.getString(AppConstant.DESCRIBE));
            response.setNumberOfRoom(object.getInt(AppConstant.ROOM));
            response.setLatitude(object.getDouble(AppConstant.LATITUDE));
            response.setLongitude(object.getDouble(AppConstant.LONGITUDE));
            response.getUser().setId(object.getInt(AppConstant.USER_ID));
            response.getUser().setName(object.getString(AppConstant.NAME));
            response.getUser().setPhone(object.getString(AppConstant.TELEPHONE));
            response.getUser().setAvatar(object.getString(AppConstant.AVATAR));
            Glide.with(this).load(object.getString(AppConstant.IMAGE1)).into(ivApartment);
            fillData(response);
            openMapActivity.putExtra(AppConstant.PRICE, response.getPrice());
            openMapActivity.putExtra(AppConstant.LATITUDE, response.getLatitude());
            openMapActivity.putExtra(AppConstant.LONGITUDE, response.getLongitude());
            openMapActivity.putExtra(AppConstant.LONGITUDE, response.getLongitude());
        } catch (JSONException e) {
            DebugLog.e("error: " + e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        request.close();
        super.onDestroy();
    }
}

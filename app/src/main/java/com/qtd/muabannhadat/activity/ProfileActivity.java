package com.qtd.muabannhadat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.model.User;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.SharedPrefUtils;
import com.qtd.muabannhadat.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class ProfileActivity extends AppCompatActivity {
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;

    @Bind(R.id.tv_name)
    TextView tvName;

    @Bind(R.id.tv_name2)
    TextView tvName2;

    @Bind(R.id.tv_email)
    TextView tvEmail;

    @Bind(R.id.tv_phone)
    TextView tvPhone;

    @Bind(R.id.tv_gender)
    TextView tvGender;

    @Bind(R.id.tv_address)
    TextView tvAddress;

    @Bind(R.id.tv_dob)
    TextView tvDob;

    @Bind(R.id.sw_notification)
    Switch swNotification;

    @Bind(R.id.sp_kind)
    Spinner spKind;

    @Bind(R.id.edt_name2)
    EditText edtName;

    @Bind(R.id.edt_email)
    EditText edtEmail;

    @Bind(R.id.edt_phone)
    EditText edtPhone;

    @Bind(R.id.edt_address)
    EditText edtAddress;

    @Bind(R.id.edt_dob)
    EditText edtDob;

    @Bind(R.id.sp_gender)
    Spinner spGender;

    @Bind(R.id.iv_edit)
    ImageView ivEdit;

    @Bind(R.id.iv_save)
    ImageView ivSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initView();
        initDataOffline();
        BaseRequestApi requestApi = new BaseRequestApi(this, String.format("{\"%s\":%d}", AppConstant.USER_ID, SharedPrefUtils.getInt(AppConstant.ID, 3)), ApiConstant.METHOD_GET_USER, new ResultRequestCallback() {
            @Override
            public void onSuccess(String result) {
                handleResponse(result);
            }

            @Override
            public void onFailed(String error) {
                DebugLog.e(error);
            }
        });
        requestApi.executeRequest();
    }

    private void initView() {
        swNotification.setEnabled(false);
    }

    private void handleResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            User user = new User();
            user.setName(object.getString(AppConstant.NAME));
            user.setDateOfBirth(object.getString(AppConstant.DOB));
            user.setPhone(object.getString(AppConstant.TELEPHONE));
            user.setAddress(object.getString(AppConstant.ADDRESS));
            user.setEmail(object.getString(AppConstant.EMAIL));
            user.setGender(object.getString(AppConstant.GENDER));
            user.setKind(object.getString("TargetKind"));
            user.setKind(object.getString(AppConstant.AVATAR));
            fillData(user);
        } catch (JSONException e) {
            DebugLog.e(e.toString());
        }
    }

    private void fillData(User user) {
        tvName.setText(user.getName());
        tvName2.setText(user.getName());
        tvDob.setText(user.getDateOfBirth());
        tvPhone.setText(user.getPhone());
        tvAddress.setText(user.getAddress());
        tvEmail.setText(user.getEmail());
        tvGender.setText(user.getGender());
        edtEmail.setText(user.getEmail());
        edtName.setText(user.getName());
        edtDob.setText(user.getDateOfBirth().substring(0, 10));
        edtPhone.setText(user.getPhone());
        edtAddress.setText(user.getAddress());
        if (StringUtil.isEmpty(user.getKind())) {
            swNotification.setChecked(false);
        } else {
            swNotification.setChecked(true);
        }
        swNotification.setEnabled(false);
    }

    private void initDataOffline() {
        ArrayAdapter<CharSequence> kindAdapter = ArrayAdapter.createFromResource(this, R.array.item_kind, R.layout.support_simple_spinner_dropdown_item);
        kindAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spKind.setAdapter(kindAdapter);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.support_simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);
    }

    @OnClick(R.id.iv_edit)
    public void onEditClicked() {
        ivEdit.setVisibility(View.GONE);
        ivSave.setVisibility(View.VISIBLE);
        swapView(View.GONE, View.VISIBLE);
        swNotification.setEnabled(true);

    }

    private void swapView(int tv, int edt) {
        tvName2.setVisibility(tv);
        tvEmail.setVisibility(tv);
        tvPhone.setVisibility(tv);
        tvGender.setVisibility(tv);
        tvAddress.setVisibility(tv);
        tvDob.setVisibility(tv);

        edtAddress.setVisibility(edt);
        edtEmail.setVisibility(edt);
        edtPhone.setVisibility(edt);
        edtDob.setVisibility(edt);
        edtName.setVisibility(edt);
        spGender.setVisibility(edt);
    }

    @OnClick(R.id.iv_save)
    public void onSaveClicked() {
        ivEdit.setVisibility(View.VISIBLE);
        ivSave.setVisibility(View.GONE);

        swapView(View.VISIBLE, View.GONE);
        swNotification.setEnabled(false);
    }

    @OnClick(R.id.iv_avatar)
    public void changeImage() {
        if (ivSave.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, MultiImageSelectorActivity.class);
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
            intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, new ArrayList<String>());
            startActivityForResult(intent, 14);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 14 && resultCode == Activity.RESULT_OK) {
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        }
    }
}

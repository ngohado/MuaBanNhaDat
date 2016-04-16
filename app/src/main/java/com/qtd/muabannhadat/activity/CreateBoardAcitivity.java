package com.qtd.muabannhadat.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 4/16/2016.
 */
public class CreateBoardAcitivity extends AppCompatActivity implements ResultRequestCallback {
    @Bind(R.id.toolbar_create_board)
    Toolbar toolbar;

    @Bind(R.id.edt_board_name)
    EditText edtBoardName;

    @Bind(R.id.progress_bar_create_board)
    ProgressBar progressBar;

    @Bind(R.id.btn_check_board)
    ImageButton imageButton;

    private BaseRequestApi requestApi;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);
        ButterKnife.bind(this);
        initComponent();
    }

    private void initComponent() {
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        getSupportActionBar().setTitle("Tạo bảng");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(true);
        edtBoardName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    @OnClick(R.id.btn_check_board)
    void btnCheckBoardOnClick() {
        if (name != "") {
            String temp[] = name.split("\\|");
            for (int i = 1; i < temp.length; i++) {
                if (edtBoardName.getText().toString().trim().equals(temp[i])) {
                    edtBoardName.setError("Nhóm đã tồn tại");
                    return;
                }
            }
        }
        if (edtBoardName.getText().toString().trim().equals("")) {
            edtBoardName.setError("Tên nhóm không thể để trống");
        } else {
            imageButton.setVisibility(View.INVISIBLE);
            progressBar.setEnabled(true);
            progressBar.setVisibility(View.VISIBLE);
            JSONObject obj = new JSONObject();
            try {
                obj.put("ID", SharedPrefUtils.getInt("ID", -1));
                obj.put("BoardName", edtBoardName.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            requestApi = new BaseRequestApi(this, obj.toString(), ApiConstant.METHOD_ADD_BOARD, this);
            requestApi.executeRequest();
        }
    }

    @Override
    public void onSuccess(String result) {
        finish();
    }

    @Override
    public void onFailed(String error) {
        if (error.equals("response is null")) {
            new AlertDialog.Builder(this)
                    .setTitle("Tạo nhóm")
                    .setMessage("Đã có lỗi xảy ra trong quá trình xử lý. Xin thử lại!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .create().show();
        }
        DebugLog.d(error);
    }
}

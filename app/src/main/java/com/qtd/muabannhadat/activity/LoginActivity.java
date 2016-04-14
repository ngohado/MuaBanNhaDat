package com.qtd.muabannhadat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.util.DebugLog;

import org.json.JSONObject;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ResultRequestCallback {
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin;
    private TextView btnRegister;
    private ProgressDialog dialog;
    private BaseRequestApi requestLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (TextView) findViewById(R.id.tv_register);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Hãy đợi chút...");
    }

    @Override
    public void onSuccess(String result) {
        dialog.dismiss();
        try {
            JSONObject obj = new JSONObject(result);
            DebugLog.i(obj.toString());
            String json = obj.getString("Res");
            if (json.equals("None")) {
                onSigninFail();
            } else {
                onSigninSuccess();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String error) {
        dialog.dismiss();
    }

    public void requestLogin() {
        if (validate()) {
            requestLogin = new BaseRequestApi(this, toJson(new String[]{edtEmail.getText().toString(), edtPass.getText().toString()}), ApiConstant.METHOD_LOGIN, this);
            requestLogin.executeRequest();
        }
    }

    private String toJson(String[] params) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("Account", params[0]);
            obj.put("PassWord", params[1]);
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void onSigninSuccess() {
        Toast.makeText(getBaseContext(), "Đăng nhập  thành công!", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    public void onSigninFail() {
        Toast.makeText(getBaseContext(), "Đăng nhập không thành công!", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Định dạng email không đúng!");
            valid = false;
        } else {
            edtEmail.setError(null);
        }
        if (pass.isEmpty() || pass.length() < 6) {
            edtPass.setError("Mật khẩu lớn hơn 6 ký tự");
            valid = false;
        } else {
            edtPass.setError(null);
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                requestLogin();
                break;
            case R.id.tv_register:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivityForResult(intentRegister, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 & resultCode == RegisterActivity.REGISTER_RESULT_CODE) {
            edtEmail.setText(data.getStringExtra(RegisterActivity.EMAIL));
        }
    }
}



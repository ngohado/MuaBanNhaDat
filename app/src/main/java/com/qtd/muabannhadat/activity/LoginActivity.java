package com.qtd.muabannhadat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.util.DebugLog;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.UnknownHostException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin;
    private Button btnRegister;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Hãy đợi chút...");
    }

    class MyAsyncTack extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String soapAction = ApiConstant.NAME_SPACE + ApiConstant.METHOD_LOGIN;
                SoapObject request = new SoapObject(ApiConstant.NAME_SPACE, ApiConstant.METHOD_LOGIN);
                request.addProperty("json", toJson(new String[]{params[0], params[1]}));
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transport = new HttpTransportSE(ApiConstant.MAIN_URL);
                transport.call(soapAction, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                return response.toString();
            } catch (UnknownHostException u) {
                Toast.makeText(LoginActivity.this, "Hãy kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            dialog.dismiss();
            try {
                JSONObject obj = new JSONObject(response);
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
    }

    public void requestLogin() {
        if (validate()) {
            new MyAsyncTack().execute(edtEmail.getText().toString(), edtPass.getText().toString());
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
        if (email.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
            case R.id.btnRegister:
                Intent openRegister = new Intent(this, RegisterActivity.class);
                startActivity(openRegister);
                break;
        }
    }
}



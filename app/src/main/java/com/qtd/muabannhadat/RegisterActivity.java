package com.qtd.muabannhadat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.UnknownHostException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etAccount;
    private EditText etPassword;
    private Boolean validatePassword;
    private EditText etConfirmPassword;
    private Boolean validateConfirmPassword;
    private EditText etEmail;
    private Boolean validateEmail;
    private EditText etTelephone;
    private Boolean validateTelephone;

    private Button btnRegister;
    final String NAMESPACE = "http://tranhongquan.com/";
    final String URL = "http://nckhqtdh.somee.com/WebServiceNCKH.asmx";

    private ProgressDialog loading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        btnRegister.setOnClickListener(this);
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etPassword.length() < 6) {
                        validatePassword = false;
                        etPassword.setError("Mật khẩu phải dài tối thiểu 6 ký tự");
                    } else {
                        validatePassword = true;
                    }
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                        validateEmail = false;
                        etEmail.setError("Hãy nhập chính xác email của bạn");
                    } else validateEmail = true;
                }
            }
        });

        etConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
                        validateConfirmPassword = false;
                        etConfirmPassword.setError("Mật khẩu xác nhận không trùng khớp");
                    } else {
                        validateConfirmPassword = true;
                    }
                }
            }
        });

        etTelephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Patterns.PHONE.matcher(etTelephone.getText().toString()).matches()) {
                        validateTelephone = false;
                        etTelephone.setError("Hãy nhập số");
                    } else {
                        validateTelephone = true;
                    }
                }
            }
        });


    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        validatePassword = false;
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        validateConfirmPassword = false;
        etEmail = (EditText) findViewById(R.id.etEmail);
        validateEmail = false;
        etTelephone = (EditText) findViewById(R.id.etTelephone);
        validateTelephone = false;
        btnRegister = (Button) findViewById(R.id.btnRegister);
        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setTitle("Đang xử lý...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                if (!isNetworkAvailable())
                    Toast.makeText(RegisterActivity.this, "Hãy kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT).show();
                else
                    btnRegisterOnClick();
                break;
        }
    }



    private void btnRegisterOnClick() {
        if (areEmpty() | !validateAll()) {
            Toast.makeText(RegisterActivity.this, "Hãy nhập đầy đủ và chính xác thông tin", Toast.LENGTH_SHORT).show();

        } else {
            btnRegister.setEnabled(false);
            new ReceiveResponseAsyncTask().execute(etAccount.getText().toString(), etPassword.getText().toString(),
                    etEmail.getText().toString(), etTelephone.getText().toString());
        }
    }

    class ReceiveResponseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            loading.show();
        }

        @Override
        protected String doInBackground(String... param) {
            try {
                String methodName = "InsertMember";
                final String SOAP_ACTION = NAMESPACE + methodName;
                SoapObject request = new SoapObject(NAMESPACE, methodName);
                request.addProperty("info", toJson(new String[]{param[0], param[1], param[2], param[3]}));
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transportSE = new HttpTransportSE(URL);
                transportSE.call(SOAP_ACTION, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                return result.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
                return ex.toString();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(RegisterActivity.this, values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            btnRegister.setEnabled(true);
            loading.dismiss();
        }
    }

    private String toJson(String[] params) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("Account", params[0]);
            obj.put("Password", params[1]);
            obj.put("Email", params[2]);
            obj.put("Telephone", params[3]);
            return obj.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private Boolean areEmpty() {
        if (etAccount.getText().toString().equals("") | etPassword.getText().toString().equals("")
                | etConfirmPassword.getText().toString().equals("") | etEmail.getText().toString().equals("")
                | etTelephone.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    public Boolean validateAll() {
        if (etPassword.length() >= 6) validatePassword = true;
        else return false;
        if (Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches())
            validateEmail = true;
        else return false;
        if (etConfirmPassword.getText().toString().equals(etPassword.getText().toString()))
            validateConfirmPassword = true;
        else return false;
        if (Patterns.PHONE.matcher(etTelephone.getText().toString()).matches())
            validateTelephone = true;
        else return false;
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

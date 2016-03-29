package com.qtd.muabannhadat;

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
    final String NAMESPACE = "http://tempuri.org/";
    final String URL = "http://www.nckhbds.somee.com/NCKH_WebService.asmx?WSDL";

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
                        Toast.makeText(RegisterActivity.this, "Mật khẩu phải dài tối thiểu 6 ký tự", Toast.LENGTH_SHORT).show();
                    } else {
                        validatePassword = true;
                    }
                } else {
                    validatePassword = false;
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                        Toast.makeText(RegisterActivity.this, "Hãy nhập chính xác email của bạn", Toast.LENGTH_SHORT).show();
                    } else validateEmail = true;
                }else {
                    validateEmail = false;
                }
            }
        });

        etConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                    } else {
                        validateConfirmPassword = true;
                    }
                } else {
                    validateConfirmPassword = false;
                }
            }
        });

        etTelephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Patterns.PHONE.matcher(etTelephone.getText().toString()).matches()){
                        Toast.makeText(RegisterActivity.this, "Hãy nhập số", Toast.LENGTH_SHORT).show();
                    } else {
                        validateTelephone = true;
                    }
                } else {
                    validateTelephone = false;
                }
            }
        });
    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelephone = (EditText) findViewById(R.id.etTelephone);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                btnRegisterOnClick();
                break;
        }
    }

    private void btnRegisterOnClick(){
        try {
            if (etAccount.getText().toString().equals("") | etPassword.getText().toString().equals("") | etConfirmPassword.getText().toString().equals("")
                    | etEmail.getText().toString().equals("") | etTelephone.getText().toString().equals("") | !validatePassword |
                    !validateConfirmPassword | !validateEmail | !validateTelephone) {
                Toast.makeText(RegisterActivity.this, "Hãy nhập đầy đủ và chính xác thông tin", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(RegisterActivity.this, "hello", Toast.LENGTH_SHORT).show();
                String MethodName = "InsertMember";
                SoapObject request = new SoapObject(NAMESPACE,MethodName);
                request.addProperty("info", toJson(new String[]{etAccount.getText().toString(), etPassword.getText().toString(),
                        etEmail.getText().toString(), etTelephone.getText().toString()}));
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transportSE = new HttpTransportSE(URL);
                transportSE.call(NAMESPACE + MethodName, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                Toast.makeText(RegisterActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex){
            Toast.makeText(RegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
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
}

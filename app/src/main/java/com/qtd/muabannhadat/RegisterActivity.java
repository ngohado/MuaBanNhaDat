package com.qtd.muabannhadat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.UnknownHostException;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etAccount;

    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private EditText etTelephone;
    private EditText etCode;

    private Button btnRegister;
    final String NAMESPACE = "http://tranhongquan.com/";
    final String URL = "http://nckhqtdh.somee.com/WebServiceNCKH.asmx";

    private ProgressDialog loading;
    Boolean hasSentEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelephone = (EditText) findViewById(R.id.etTelephone);
        etCode = (EditText) findViewById(R.id.etCode);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setTitle("Đang xử lý...");

        etAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String et = etAccount.getText().toString();
                    if (et.contains(" ") | et.length() < 6) {
                        etAccount.setError("Tên tài khoản phải dài tối thiểu 6 ký tự và không chứa dấu cách");
                    } else
                        new CheckHasUsernameAsyncTask().execute(etAccount.getText().toString());
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etPassword.length() < 6) {
                        etPassword.setError("Mật khẩu phải dài tối thiểu 6 ký tự");
                    }
                }
            }
        });

        etConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
                        etConfirmPassword.setError("Mật khẩu xác nhận không trùng khớp");
                    }
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                        etEmail.setError("Định dạng email không hợp lệ");
                    }
                }
            }
        });

        etTelephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Patterns.PHONE.matcher(etTelephone.getText().toString()).matches()) {
                        etTelephone.setError("Định dạng số điện thoại không dúng");
                    }
                }
            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etCode.length() == 6) btnRegister.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        hasSentEmail = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                if (!isNetworkAvailable())
                    Toast.makeText(RegisterActivity.this, "Hãy kiểm tra lại kết nối mạng", Toast.LENGTH_LONG).show();
                else
                    btnRegisterOnClick();
                break;
        }
    }

    private void btnRegisterOnClick() {
        if (areEmpty() | !validateAll()) {
            Toast.makeText(RegisterActivity.this, "Hãy nhập đầy đủ và chính xác thông tin", Toast.LENGTH_LONG).show();
        } else {
            if (!hasSentEmail) {
                etCode.setVisibility(View.VISIBLE);
                btnRegister.setEnabled(false);
                sendConfirmEmail(etEmail.getText().toString(), etAccount.getText().toString(), etAccount.getText().toString());
                if (this.getCurrentFocus() != null){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                }
            } else
                new ReceiveResponseAsyncTask().execute(etAccount.getText().toString(), etPassword.getText().toString(),
                        etEmail.getText().toString(), etTelephone.getText().toString());
        }
    }


    private void sendConfirmEmail(String... params) {
        BackgroundMail.newBuilder(this)
                .withUsername("ngodoan.utc@gmail.com")
                .withPassword("031889778")
                .withMailto(params[0])
                .withSubject("EMAIL XÁC NHẬN ĐĂNG KÝ TÀI KHOẢN")
                .withBody("Xin chào " + params[1] + ",\n\nThông tin tài khoản:\n   - Tên tài khoản: " + params[2] + "\n   - Mã xác nhận: " + (100000 + new Random().nextInt(899999)) +
                        "\n\nXin cám ơn!")
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
                        alert.setTitle("Thông báo");
                        alert.setMessage("Hãy kiểm tra hòm thư để xác nhận tài khoản");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.create().show();
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
                        alert.setTitle("Thông báo");
                        alert.setMessage("Đã xảy ra lỗi trong quá trình gửi. Hãy thử lại");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.create().show();
                    }
                })
                .send();
        hasSentEmail = true;
    }

    private String toJson(String... params) {
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
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String email = etEmail.getText().toString();
        String telephone = etTelephone.getText().toString();
        if (account.equals("") | password.equals("") | confirmPassword.equals("") | email.equals("") | telephone.equals("")) {
            return true;
        }
        return false;
    }

    public Boolean validateAll() {
        if (etAccount.getError() != null) return false;
        if (etPassword.getError() != null) return false;
        if (etConfirmPassword.getError() != null) return false;
        if (etEmail.getError() != null) return false;
        if (etTelephone.getError() != null) return false;
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    class ReceiveResponseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            loading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String methodName = "InsertMember";
                final String SOAP_ACTION = NAMESPACE + methodName;
                SoapObject request = new SoapObject(NAMESPACE, methodName);
                request.addProperty("info", toJson(new String[]{params[0], params[1], params[2], params[3]}));
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
        }
    }

    class CheckHasUsernameAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String methodName = "ChecHaskUsername";
                SoapObject request = new SoapObject(NAMESPACE, methodName);
                request.addProperty("JsonUsername", toJson(params[0]));
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transportSE = new HttpTransportSE(URL);
                transportSE.call(NAMESPACE + methodName, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                return result.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("true")) {
                etAccount.setError("Tài khoản đã tồn tại");
            }
        }
    }
}

package com.qtd.muabannhadat.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.Utility;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Random;

import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ResultRequestCallback {
    public static final String EMAIL = "email";
    public static final int REGISTER_RESULT_CODE = 1;

    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private EditText etTelephone;
    private EditText etCode;

    private Button btnRegister;

    private ProgressDialog loading;
    Boolean hasSentEmail;
    private int confirmCode = 0;

    BaseRequestApi requestApi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelephone = (EditText) findViewById(R.id.etTelephone);
        etCode = (EditText) findViewById(R.id.etCode);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setTitle("Đang xử lý...");

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Utility.isNetworkAvailable(getApplicationContext(), findViewById(R.id.relativeLayout_register), true)) {

                    } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                        etEmail.setError("Email không hợp lệ");
                    } else {
                        new CheckHasEmailAsyncTask().execute(etEmail.getText().toString());
                    }
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
                if (!Utility.isNetworkAvailable(getApplicationContext(), findViewById(R.id.relativeLayout_register), true)) {

                } else
                    btnRegisterOnClick();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng ký")
                .setMessage("Mọi thông tin đăng ký sẽ bị hủy. Bạn có muốn quay lại?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create().show();
    }

    private void btnRegisterOnClick() {
        if (areEmpty() | !validateAll()) {
            Toast.makeText(RegisterActivity.this, "Hãy nhập đầy đủ và chính xác thông tin", Toast.LENGTH_LONG).show();
        } else {
            if (!hasSentEmail) {
                etCode.setVisibility(View.VISIBLE);
                btnRegister.setEnabled(false);
                sendConfirmEmail(etEmail.getText().toString());
                if (this.getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                }
            } else {
                if (etCode.getText().toString().equals(String.valueOf(confirmCode))) {
                    requestApi = new BaseRequestApi(this, toJson(etEmail.getText().toString(), etPassword.getText().toString(),
                            etTelephone.getText().toString()), ApiConstant.METHOD_REGISTER, this);
                    requestApi.executeRequest();
                } else {
                    Toast.makeText(RegisterActivity.this, "Mã xác nhận không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void sendConfirmEmail(String param) {
        confirmCode = (100000 + new Random().nextInt(899999));
        DebugLog.i(String.valueOf(confirmCode));
        BackgroundMail.newBuilder(this)
                .withUsername("ngodoan.utc@gmail.com")
                .withPassword("031889778")
                .withMailto(param)
                .withSubject("EMAIL XÁC NHẬN ĐĂNG KÝ TÀI KHOẢN")
                .withBody("Xin chào,\n\nThông tin tài khoản:\n   - Email đăng ký: " + param + "\n   - Mã xác nhận: " + confirmCode
                        + "\n\nXin cám ơn!")
                .send();
        new AlertDialog.Builder(this)
                .setTitle("Đăng ký")
                .setMessage("Hãy kiểm tra hòm thư để xác nhận tài khoản")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        RegisterActivity.this.getCurrentFocus().clearFocus();
                        etCode.requestFocus();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .create().show();
        hasSentEmail = true;
    }

    private String toJson(String... params) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("Email", params[0]);
            obj.put("Password", params[1]);
            obj.put("Telephone", params[2]);
            return obj.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private String toJson(String param) {
        try {
            JSONObject object = new JSONObject();
            object.put("Email", param);
            return object.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private Boolean areEmpty() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String telephone = etTelephone.getText().toString();
        if (email.equals("") | password.equals("") | confirmPassword.equals("") | telephone.equals("")) {
            return true;
        }
        return false;
    }

    public Boolean validateAll() {
        if (etEmail.getError() != null) return false;
        if (etPassword.getError() != null) return false;
        if (etConfirmPassword.getError() != null) return false;
        if (etTelephone.getError() != null) return false;
        return true;
    }

    @Override
    public void onSuccess(String result) {
        processResult(result);
        loading.dismiss();
    }

    @Override
    public void onFailed(String error) {
        DebugLog.e(error);
        loading.dismiss();
    }

    private void processResult(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            if (obj.getString("Res").equals("Success")) {
                new AlertDialog.Builder(this)
                        .setTitle("Đăng ký")
                        .setMessage("Đăng ký thành công")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intentResult = new Intent();
                                intentResult.putExtra(EMAIL, etEmail.getText().toString().trim());
                                setResult(REGISTER_RESULT_CODE, intentResult);
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .create().show();

            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Đăng ký")
                        .setMessage("Đã có lỗi trong quá trình xử lý, xin thử lại")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .create().show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class CheckHasEmailAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String a = "";
            try {
                SoapObject request = new SoapObject(ApiConstant.NAME_SPACE, ApiConstant.METHOD_CHECK_USER);
                request.addProperty("JsonUsername", toJson(params[0]));
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transportSE = new HttpTransportSE(ApiConstant.MAIN_URL);
                transportSE.call(ApiConstant.NAME_SPACE + ApiConstant.METHOD_CHECK_USER, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                JSONObject jsonObject = new JSONObject(result.toString());
                a = jsonObject.getString("Res");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("true")) {
                etEmail.setError("Email đã được đăng ký");
            }
        }
    }
}

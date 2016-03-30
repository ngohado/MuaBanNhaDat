package com.qtd.muabannhadat;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.SignatureException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin;
    final String NAMESPACE="http://tranhongquan.com/";
   public final static String URL="http://nckhqtdh.somee.com/WebServiceNCKH.asmx?WSDL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        assert btnLogin != null;
        btnLogin.setOnClickListener(this);
    }

    private void initView() {
        edtEmail=(EditText) findViewById(R.id.editText);
        edtPass=(EditText) findViewById(R.id.editText1);
        btnLogin=(Button) findViewById(R.id.buttonLogin);
    }
    class MyAsyncTack extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
                super.onPreExecute();
                hanlder();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                    String METHOD_NAME="GetMemberinfobyIDnPassWord";
                    String SOAP_ACTION=NAMESPACE+METHOD_NAME;
                    SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
                    request.addProperty("json", toJson(new String[]{params[0], params[1]}));
                    SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet=true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE transport=new HttpTransportSE(URL);
                    transport.call(SOAP_ACTION, envelope);
                    SoapPrimitive response=(SoapPrimitive) envelope.getResponse();
                    return response.toString();
                }catch (UnknownHostException u){
                Toast.makeText(LoginActivity.this, "Hãy kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Toast.makeText(LoginActivity.this, response,Toast.LENGTH_LONG).show();

        }
    }

    public void btnLoginClick(){
        if (!validate()){
            onSigninFail();
        }else {
            btnLogin.setEnabled(false);
            new MyAsyncTack().execute(edtEmail.getText().toString(),edtPass.getText().toString());
        }
    }

    private String toJson(String[] params){
        try {
            JSONObject obj=new JSONObject();
            obj.put("Account",params[0]);
            obj.put("PassWord",params[1]);
            return obj.toString();
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
    public void onSigninFail(){
        Toast.makeText(getBaseContext(), "Đăng nhập không thành công!", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }
    public boolean validate(){
        boolean valid=true;
        String mail=edtEmail.getText().toString();
        String pass=edtPass.getText().toString();
        if (mail.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            edtEmail.setError("Nhập địa chỉ email hợp lệ");
            valid=false;
        }
        else {
            edtEmail.setError(null);
        }
        if (pass.isEmpty()||pass.length()<1){
            edtPass.setError("Mật khẩu lớn hơn 6 ký tự");
            valid=false;
        }
        else {
            edtPass.setError(null);
        }
        return valid;
    }

    public  void hanlder(){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this,R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Hãy đợi chút...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000
        );
    }
    public void onSignupSuccess() {
        btnLogin.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
                btnLoginClick();
                break;
        }
    }
}


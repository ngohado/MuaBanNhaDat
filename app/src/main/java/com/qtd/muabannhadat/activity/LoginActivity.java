package com.qtd.muabannhadat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.request.LienTMTwitterApiClient;
import com.qtd.muabannhadat.request.RegisterToken;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.SharedPrefUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ResultRequestCallback {
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin;
    private TextView btnRegister;
    private ProgressDialog dialog;
    private BaseRequestApi requestLogin;
    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;
    private TwitterSession twitterSession;

    @Bind(R.id.btn_LoginFacebook)
    Button btnLoginFacebook;

    @Bind(R.id.btn_LoginTwitter)
    Button btnLoginTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoginFacebook();
        initLoginTwitter();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initLoginTwitter() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(ApiConstant.TWITTER_KEY, ApiConstant.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        twitterAuthClient = new TwitterAuthClient();
    }

    private void initLoginFacebook() {
        final String[] info = new String[4];
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                if (profile == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            if (currentProfile != null) {
                                info[2] = currentProfile.getFirstName() + " " + currentProfile.getMiddleName() + " " + currentProfile.getLastName();
                                info[3] = currentProfile.getProfilePictureUri(72, 72).toString();
                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                try {
                                                    info[0] = object.getString("id");
                                                    info[1] = object.getString("email");
                                                    requestLogin = new BaseRequestApi(getApplicationContext(), toJson(info, 2), ApiConstant.METHOD_LOGIN, LoginActivity.this);
                                                    requestLogin.executeRequest();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,email");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }
                        }
                    };
                } else {
                    info[2] = profile.getFirstName() + " " + profile.getMiddleName() + " " + profile.getLastName();
                    info[3] = profile.getProfilePictureUri(72, 72).toString();
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
                                        info[0] = object.getString("id");
                                        info[1] = object.getString("email");
                                        requestLogin = new BaseRequestApi(getApplicationContext(), toJson(info, 2), ApiConstant.METHOD_LOGIN, LoginActivity.this);
                                        requestLogin.executeRequest();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,email");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }

            @Override
            public void onCancel() {
                Log.d("Facebook login", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebook login", "error");
            }
        });


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
                Toast.makeText(getBaseContext(), "Đăng nhập không thành công!", Toast.LENGTH_LONG).show();
                btnLogin.setEnabled(true);
            } else {
                RegisterToken registerToken = new RegisterToken(this);
                int userId = obj.getInt("UserID");
                SharedPrefUtils.putInt("ID", userId);
                Toast.makeText(getBaseContext(), "Đăng nhập  thành công!", Toast.LENGTH_LONG).show();
                btnLogin.setEnabled(true);
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String error) {
        dialog.dismiss();
        DebugLog.d(error);
    }

    public void requestLogin() {
        if (validate()) {
            requestLogin = new BaseRequestApi(this, toJson(new String[]{edtEmail.getText().toString(), edtPass.getText().toString()}, 1), ApiConstant.METHOD_LOGIN, this);
            requestLogin.executeRequest();
        }
    }

    private String toJson(String[] params, int type) {
        JSONObject obj = new JSONObject();
        if (type == 1) {
            try {
                obj.put("Email", params[0]);
                obj.put("Password", params[1]);
                obj.put("Type", 1);
                return obj.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == 2) {
            try {
                obj.put("Id", params[0]);
                obj.put("Email", params[1]);
                obj.put("Name", params[2]);
                obj.put("UrlImage", params[3]);
                obj.put("Type", 2);
                return obj.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
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
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_LoginFacebook)
    void BtnLoginFacebookOnClick() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    @OnClick(R.id.btn_LoginTwitter)
    void BtnLoginTwitterOnClick() {
        final String[] info = new String[4];
        twitterAuthClient.authorize(LoginActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                if (result == null) {
                    DebugLog.d("result null");
                } else {
                    twitterSession = result.data;
                    info[0] = String.valueOf(result.data.getId());
                    info[1] = "";
                    Log.d("username", result.data.getUserName());
                    new LienTMTwitterApiClient(twitterSession).getUsersService().show(12L, null, true, new Callback<User>() {
                        @Override
                        public void success(Result<User> result) {
                            Log.d("name", result.data.name);
                            info[3] = result.data.profileImageUrlHttps;
                            requestLogin = new BaseRequestApi(getApplicationContext(), toJson(info, 2), ApiConstant.METHOD_LOGIN, LoginActivity.this);
                            requestLogin.executeRequest();
                        }

                        @Override
                        public void failure(TwitterException e) {
                            e.printStackTrace();
                        }
                    });

                }
            }

            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestLogin != null) {
            requestLogin.close();
        }
    }
}



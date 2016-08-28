package com.qtd.muabannhadat.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 4/17/2016.
 */
public class RegistrationIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String TAG = "RegIntentService";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        String token;
        try {
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            DebugLog.d(token);
            sendRegistrationToServer(token);

            SharedPrefUtils.putBoolean(SENT_TOKEN_TO_SERVER, true);
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            SharedPrefUtils.putBoolean(SENT_TOKEN_TO_SERVER, false);
        }

        Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(String token) {
        JSONObject object = new JSONObject();
        try {
            object.put(AppConstant.USER_ID, SharedPrefUtils.getInt("ID", -1));
            object.put("RegID", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BaseRequestApi requestApi = new BaseRequestApi(getApplicationContext(), object.toString(), ApiConstant.METHOD_ADD_REG_ID, new ResultRequestCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", result);
            }

            @Override
            public void onFailed(String error) {
                DebugLog.d(error);
            }
        });
        requestApi.executeRequest();
    }
}

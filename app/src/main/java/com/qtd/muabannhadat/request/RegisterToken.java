package com.qtd.muabannhadat.request;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.qtd.muabannhadat.service.RegistrationIntentService;

/**
 * Created by Dell on 4/18/2016.
 */
public class RegisterToken {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private AppCompatActivity activity;

    public RegisterToken(AppCompatActivity activity) {
        this.activity = activity;
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(activity, RegistrationIntentService.class);
            activity.startService(intent);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("info", "This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }
}

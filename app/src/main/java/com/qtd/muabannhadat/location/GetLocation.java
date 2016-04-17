package com.qtd.muabannhadat.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.qtd.muabannhadat.util.DebugLog;

/**
 * Created by DoanNH on 10-Mar-16.
 */
public class GetLocation implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private Context context;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private GetLocationCallback callback;
    private boolean isShowSettingDialog = true;


    public void setShowSettingDialog(boolean showSettingDialog) {
        isShowSettingDialog = showSettingDialog;
    }

    public void setCallback(GetLocationCallback callback) {
        this.callback = callback;
    }

    public GetLocation(final Context context) {
        this.context = context;

    }

    public void executeGetLocation() {
        if (isEnabledLocation()) {
            prepareForGetLocation();
        } else {
            openGpsSetting(context);
        }
    }

    public boolean isEnabledLocation() {
        boolean en_gps = false;
        boolean en_network = false;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        en_gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        en_network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return en_gps || en_network;
    }

    private void prepareForGetLocation() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation != null) {
            if (callback != null) {
                callback.onAvailableLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
            }
        } else {
            if (callback != null) {
                callback.onErrorOccur();
            }
        }
        disconnect();
    }

    private void disconnect() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        DebugLog.e("Error to get location");
        if (callback != null) {
            callback.onErrorOccur();
        }
        disconnect();
    }



    public static void openGpsSetting(Context context) {
        try {
            final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                final Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            } catch (Exception ex) {

            }
        }
    }

}

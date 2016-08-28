package com.qtd.muabannhadat.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;

/**
 * Created by Ngo Hado on 13-Apr-16.
 */
public class ServiceUtils {

    private static ServiceUtils sInstance;
    private static Context sContext;

    public ServiceUtils(Context context) {
        sContext = context;
    }

    public static synchronized ServiceUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ServiceUtils(context);
        }
        return sInstance;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (mConnectivityManager.getActiveNetworkInfo() != null && mConnectivityManager.getActiveNetworkInfo().isAvailable() && mConnectivityManager
                .getActiveNetworkInfo().isConnected());
    }

    public static boolean isLocationServiceEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false, network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            //do nothing...
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            //do nothing...
        }

        return gps_enabled || network_enabled;
    }
}

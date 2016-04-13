package com.qtd.muabannhadat.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Ngo Hado on 13-Apr-16.
 */
public class NetworkUtil {

    private static NetworkUtil sInstance;
    private static Context sContext;

    public NetworkUtil(Context context) {
        this.sContext = context;
    }

    public static synchronized NetworkUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NetworkUtil(context);
        }
        return sInstance;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (mConnectivityManager.getActiveNetworkInfo() != null && mConnectivityManager.getActiveNetworkInfo().isAvailable() && mConnectivityManager
                .getActiveNetworkInfo().isConnected());
    }
}

package com.qtd.muabannhadat;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.qtd.muabannhadat.util.SharedPrefUtils;

/**
 * Created by Ngo Hado on 14-Apr-16.
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    private static SharedPrefUtils sharedPreferences;

    public static String TAG = "VolleyRequest";
    private RequestQueue requestQueue;

    public BaseApplication() {
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = new SharedPrefUtils(getApplicationContext());
    }

    public static SharedPrefUtils getSharedPreferences() {
        return sharedPreferences;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequest(Object tag){
        if (requestQueue != null){
            requestQueue.cancelAll(tag);
        }
    }

}

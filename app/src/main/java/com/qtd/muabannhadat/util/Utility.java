package com.qtd.muabannhadat.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.qtd.muabannhadat.R;

/**
 * Created by Dell on 4/13/2016.
 */
public class Utility {
    public static Snackbar snackbar;

    public static boolean isNetworkAvailable(Context context, View view, boolean hasShow) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean result = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!result) makeSnackBar(view, hasShow);
        return result;
    }

    private static void makeSnackBar(View view, boolean hasShow){
        if (hasShow) {
            snackbar = Snackbar.make(view, "Không có kết nối Internet - xin thử lại", Snackbar.LENGTH_LONG)
                    .setAction("Đóng", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
            snackbar.setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
            snackbar.show();
        }
    }
}

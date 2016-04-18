package com.qtd.muabannhadat.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.activity.ApartmentDetailActivity;
import com.qtd.muabannhadat.activity.HomeActivity;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Dell on 4/17/2016.
 */
public class MyGcmListenerService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String collapse = data.getString("collapse_key");
        sendNotification(message);

    }

    private void sendNotification(String message) {
        JSONObject object;
        int id = 8;
        String kind = "";
        int price = 0;
        try {
            object = new JSONObject(message);
            id = object.getInt(AppConstant.A_ID);
            kind = object.getString(AppConstant.KIND);
            price = object.getInt(AppConstant.PRICE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, ApartmentDetailActivity.class);
        intent.putExtra(ApartmentDetailActivity.ID_APARTMENT, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentTitle("Mua ban nha dat")
                .setContentText("1 " + kind + " đã được đăng\n" + NumberFormat.getNumberInstance(Locale.GERMAN).format(price) + " VNĐ")
                .setAutoCancel(true)
                .setSound(soundURI)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

        if (SharedPrefUtils.getBoolean(HomeActivity.NOTIFICATION_IS_VISIBLE, false)) {
            //do something to refresh notification fragment
        }
    }
}

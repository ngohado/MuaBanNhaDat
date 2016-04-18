package com.qtd.muabannhadat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.viewholder.NotificationViewHolder;
import com.qtd.muabannhadat.model.Notification;

import java.util.ArrayList;

/**
 * Created by Dell on 4/18/2016.
 */
public class ItemNotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private ArrayList<Notification> notifications;

    public ItemNotificationAdapter(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.setupWith(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}

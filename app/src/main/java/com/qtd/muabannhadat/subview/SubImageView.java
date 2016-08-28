package com.qtd.muabannhadat.subview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ngo Hado on 14-Apr-16.
 */
public class SubImageView extends RelativeLayout {

    @Bind(R.id.iv_home)
    ImageView ivHome;
    Context context;

    public SubImageView(Context context) {
        super(context);
        this.context = context;
        inflate(context, R.layout.sub_image, this);
        ButterKnife.bind(this);
    }

    public void setupWith(String uri) {
        Glide.with(context).load(uri).asBitmap().into(ivHome);
    }

    public Bitmap getBitmap() {
        return ((BitmapDrawable) ivHome.getDrawable()).getBitmap();
    }

    @OnClick(R.id.iv_delete)
    public void deleteSelf() {
        ((ViewManager) this.getParent()).removeView(this);
    }
}

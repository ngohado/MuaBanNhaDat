package com.qtd.muabannhadat.subview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.model.User;
import com.qtd.muabannhadat.util.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ngo Hado on 13-Apr-16.
 */
public class UserContactView extends RelativeLayout {

    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;

    @Bind(R.id.tv_name)
    TextView tvName;

    @Bind(R.id.tv_phone)
    TextView tvPhone;

    User user;


    public UserContactView(Context context, User user) {
        super(context);
        inflate(context, R.layout.user_contact, this);
        ButterKnife.bind(this);
        setupWith(user);
    }

    public UserContactView(Context context) {
        super(context);
        inflate(context, R.layout.user_contact, this);
        ButterKnife.bind(this);
    }

    public void setupWith(User user) {
        this.user = user;
        StringUtil.displayText(user.getName(), tvName);
        StringUtil.displayText(user.getPhone(), tvPhone);
        Glide.with(getContext()).load(user.getAvatar()).error(R.drawable.user).placeholder(R.drawable.user).into(ivAvatar);
    }

    @OnClick(R.id.btn_call)
    public void callProvider() {
        if (user == null || StringUtil.isEmpty(user.getPhone())) {
            Toast.makeText(getContext(), "Không có số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent openDial = new Intent(Intent.ACTION_DIAL);
        openDial.setData(Uri.parse("tel:" + user.getPhone()));
        getContext().startActivity(openDial);
    }
}

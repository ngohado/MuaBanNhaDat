package com.qtd.muabannhadat.subview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        StringUtil.displayText(user.getName(), tvName);
        StringUtil.displayText(user.getPhone(), tvPhone);

    }

    @OnClick(R.id.btn_call)
    public void callProvider() {

    }
}

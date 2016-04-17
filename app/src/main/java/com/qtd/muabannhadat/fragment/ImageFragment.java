package com.qtd.muabannhadat.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.constant.AppConstant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {


    public ImageFragment() {
        // Required empty public constructor
    }

    @Bind(R.id.iv_home)
    ImageView ivHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);
        String url = getArguments().getString(AppConstant.IMAGE1, "");
        Glide.with(getActivity()).load(url).into(ivHome);
        return view;
    }

}

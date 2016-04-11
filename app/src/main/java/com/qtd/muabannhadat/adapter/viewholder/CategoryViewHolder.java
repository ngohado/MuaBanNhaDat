package com.qtd.muabannhadat.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtd.muabannhadat.R;

/**
 * Created by Dell on 4/9/2016.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TileHome tileHome0;
    private TileHome tileHome1;
    private TileHome tileHome2;
    private TileHome tileHome3;
    private TileHome tileHome4;
    private Button btnSeeAll;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        initComponent(itemView);
    }

    private void initComponent(View itemView) {
        tileHome0 = new TileHome(itemView, R.id.imv1, R.id.txtCost1,R.id.txtAddress1, R.id.txtCity1);
        tileHome0.ivHome.setOnClickListener(this);
        tileHome1 = new TileHome(itemView, R.id.imv2, R.id.txtCost2,R.id.txtAddress2, R.id.txtCity2);
        tileHome1.ivHome.setOnClickListener(this);
        tileHome2 = new TileHome(itemView, R.id.imv3, R.id.txtCost3,R.id.txtAddress3, R.id.txtCity3);
        tileHome2.ivHome.setOnClickListener(this);
        tileHome3 = new TileHome(itemView, R.id.imv4, R.id.txtCost4,R.id.txtAddress4, R.id.txtCity4);
        tileHome3.ivHome.setOnClickListener(this);
        tileHome4 = new TileHome(itemView, R.id.imv5, R.id.txtCost5,R.id.txtAddress5, R.id.txtCity5);
        tileHome4.ivHome.setOnClickListener(this);

        btnSeeAll = (Button) this.itemView.findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(this);
    }

    public TileHome getTileHome0() {
        return tileHome0;
    }

    public TileHome getTileHome1() {
        return tileHome1;
    }

    public TileHome getTileHome2() {
        return tileHome2;
    }

    public TileHome getTileHome3() {
        return tileHome3;
    }

    public TileHome getTileHome4() {
        return tileHome4;
    }

    public Button getBtnSeeAll() {
        return btnSeeAll;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv1:
                break;
            case R.id.btnSeeAll:
                onClickButtonSeeAll();
                break;
        }
    }

    private void onClickButtonSeeAll() {

    }

    public class TileHome{
        private ImageView ivHome;
        private TextView tvCost;
        private TextView tvAddress;
        private TextView tvCity;

        public TileHome(View v, int ivHome, int tvCost, int tvAddress, int tvCity) {
            this.ivHome = (ImageView) v.findViewById(ivHome);
            this.tvCost = (TextView) v.findViewById(tvCost);
            this.tvAddress = (TextView) v.findViewById(tvAddress);
            this.tvCity = (TextView) v.findViewById(tvCity);
        }

        public ImageView getIvHome() {
            return ivHome;
        }

        public TextView getTvCost() {
            return tvCost;
        }

        public TextView getTvAddress() {
            return tvAddress;
        }

        public TextView getTvCity() {
            return tvCity;
        }
    }
}

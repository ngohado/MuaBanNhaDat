package com.qtd.muabannhadat.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtd.muabannhadat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Bind(R.id.imv1)
    public ImageView ivHome1;

    @Bind(R.id.txtCost1)
    public TextView tvCost1;

    @Bind(R.id.txtAddress1)
    public TextView tvAddress1;

    @Bind(R.id.txtCity1)
    public TextView tvCity1;

    @Bind(R.id.imv2)
    public ImageView ivHome2;

    @Bind(R.id.txtCost2)
    public TextView tvCost2;

    @Bind(R.id.txtAddress2)
    public TextView tvAddress2;

    @Bind(R.id.txtCity2)
    public TextView tvCity2;

    @Bind(R.id.imv3)
    public ImageView ivHome3;

    @Bind(R.id.txtCost3)
    public TextView tvCost3;

    @Bind(R.id.txtAddress3)
    public TextView tvAddress3;

    @Bind(R.id.txtCity3)
    public TextView tvCity3;

    @Bind(R.id.imv4)
    public ImageView ivHome4;

    @Bind(R.id.txtCost4)
    public TextView tvCost4;

    @Bind(R.id.txtAddress4)
    public TextView tvAddress4;

    @Bind(R.id.txtCity4)
    public TextView tvCity4;

    @Bind(R.id.imv5)
    public ImageView ivHome;

    @Bind(R.id.txtCost5)
    public TextView tvCost;

    @Bind(R.id.txtAddress5)
    public TextView tvAddress;

    @Bind(R.id.txtCity5)
    public TextView tvCity;

    public CategoryViewHolder(View itemView) {
        super(itemView);
//        initComponent(itemView);
        ButterKnife.bind(this,itemView);
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

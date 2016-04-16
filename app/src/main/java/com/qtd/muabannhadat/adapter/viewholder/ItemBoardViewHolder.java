package com.qtd.muabannhadat.adapter.viewholder;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.model.Board;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 4/15/2016.
 */
public class ItemBoardViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.imv_board)
    ImageView imageView;

    @Bind(R.id.tv_name_board)
    TextView tvName;

    @Bind(R.id.tv_apartment_board)
    TextView tvApartment;

    View view;

    public ItemBoardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    @OnClick(R.id.imv_board)
    void imageViewOnClick() {

    }

    public void setupWith(Board board, int number) {
        tvName.setText(board.getName());
        tvApartment.setText(board.getNumberOfApartment() + " căn hộ");
        if (number == 0) {
            imageView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPink));
        } else {
            Glide.with(view.getContext()).load(Uri.parse(board.getImageFirst())).into(imageView);
        }
    }

}

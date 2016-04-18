package com.qtd.muabannhadat.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.model.Board;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ngo Hado on 18-Apr-16.
 */
public class DialogItemBoardViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.imv_board)
    ImageView imageView;

    @Bind(R.id.tv_name_board)
    TextView tvName;

    @Bind(R.id.tv_apartment_board)
    TextView tvApartment;

    private View view;
    private int boardID = -1;
    private String boardName = "";

    public DialogItemBoardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    public void setupWith(Board board, int number) {
        boardID = board.getId();
        boardName = board.getName();
        tvName.setText(board.getName());
        tvApartment.setText(board.getNumberOfApartment() + " căn hộ");
        if (number == 0) {
            imageView.setImageResource(R.drawable.board);
        } else {
            String temp = board.getImageFirst();
            Glide.with(view.getContext()).load(temp).into(imageView);
        }
    }

}
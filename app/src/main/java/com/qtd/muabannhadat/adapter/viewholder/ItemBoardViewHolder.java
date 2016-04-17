package com.qtd.muabannhadat.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.activity.BoardDetailActivity;
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

    private View view;
    private int boardID = -1;
    private String boardName = "";

    public ItemBoardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    @OnClick(R.id.imv_board)
    void imageViewOnClick() {
        Intent intent = new Intent(view.getContext(), BoardDetailActivity.class);
        intent.putExtra("BoardID", boardID);
        intent.putExtra("BoardName", boardName);
        view.getContext().startActivity(intent);
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

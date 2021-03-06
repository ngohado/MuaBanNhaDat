package com.qtd.muabannhadat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.viewholder.ItemBoardViewHolder;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.model.Board;

import java.util.ArrayList;

/**
 * Created by Dell on 4/15/2016.
 */
public class ItemBoardAdapter extends RecyclerView.Adapter<ItemBoardViewHolder> {
    private ArrayList<Board> boards;
    ResultRequestCallback callback;
    public ItemBoardAdapter(ArrayList<Board> boards) {
        this.boards = boards;
    }

    public ItemBoardAdapter(ArrayList<Board> boards, ResultRequestCallback callback) {
        this.boards = boards;
        this.callback = callback;
    }

    @Override
    public ItemBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_board, parent, false);
        return new ItemBoardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemBoardViewHolder holder, int position) {
        final Board board = boards.get(position);
        holder.setupWith(board, board.getNumberOfApartment());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onSuccess("" + board.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return boards.size();
    }
}

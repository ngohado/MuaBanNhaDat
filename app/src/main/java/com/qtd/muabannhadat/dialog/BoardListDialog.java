package com.qtd.muabannhadat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.adapter.DialogItemBoardAdapter;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.model.Board;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ngo Hado on 18-Apr-16.
 */
public class BoardListDialog extends Dialog implements ResultRequestCallback {

    @Bind(R.id.recyclerView)
    RecyclerView boardList;

    DialogItemBoardAdapter boardAdapter;
    ArrayList<Board> boards = new ArrayList<>();
    ResultRequestCallback callback;
    int idApartment;

    public BoardListDialog(Context context, int id, ResultRequestCallback callback) {
        super(context);
        this.callback = callback;
        this.idApartment = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.board_list_dialog);
        ButterKnife.bind(this);
        initBoardList();
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private void initBoardList() {
        boardAdapter = new DialogItemBoardAdapter(boards, this);
        boardList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        boardList.setAdapter(boardAdapter);

        int id = SharedPrefUtils.getInt(AppConstant.ID, -1);
        if (id != -1) {
            JSONObject object = new JSONObject();
            try {
                object.put(AppConstant.USER_ID, id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            BaseRequestApi boardRequest = new BaseRequestApi(getContext(), object.toString(), ApiConstant.METHOD_GET_BOARD_BY_ID, new ResultRequestCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        boards.clear();
                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            if (obj.getInt("NumberOfApartment") == 0) {
                                Board board = new Board(obj.getInt("BoardID"), obj.getString("BoardName"), obj.getInt("NumberOfApartment"), "");
                                boards.add(board);
                            } else {
                                Board board = new Board(obj.getInt("BoardID"), obj.getString("BoardName"), obj.getInt("NumberOfApartment"), obj.getString("ImageFirst"));
                                boards.add(board);
                            }
                        }
                        boardAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(String error) {

                }
            });
            boardRequest.executeRequest();
        }
    }

    @Override
    public void onSuccess(String result) {
        int idBoard = Integer.parseInt(result);
        BaseRequestApi requestApi = new BaseRequestApi(getContext(), toJson(idBoard), ApiConstant.METHOD_ADD_APARTMENT_TO_BOARD, new ResultRequestCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess("Success");
            }

            @Override
            public void onFailed(String error) {
                callback.onFailed(error);
            }
        });
        requestApi.executeRequest();

    }

    private String toJson(int idBoard) {
        JSONObject object = new JSONObject();
        try {
            object.put(AppConstant.USER_ID, SharedPrefUtils.getInt(AppConstant.ID, -1));
            object.put(AppConstant.A_ID, idApartment);
            object.put("BoardID", idBoard);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @Override
    public void onFailed(String error) {

    }
}

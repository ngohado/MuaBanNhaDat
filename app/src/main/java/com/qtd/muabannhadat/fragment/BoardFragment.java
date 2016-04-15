package com.qtd.muabannhadat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.activity.LoginActivity;
import com.qtd.muabannhadat.adapter.ItemBoardAdapter;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.model.Board;
import com.qtd.muabannhadat.request.RequestRepeatApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Dell on 4/12/2016.
 */
public class BoardFragment extends Fragment implements ResultRequestCallback {
    @Bind(R.id.recyclerView_fragmentBoard)
    RecyclerView recyclerView;

    @Bind(R.id.fab_add)
    FloatingActionButton fabAdd;

    private ItemBoardAdapter boardAdapter;
    private ArrayList<Board> boards;
    private View view;
    private GridLayoutManager layoutManager;
    private RequestRepeatApi requestRepeatApi;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board_favorite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
    }

    private void initComponent() {
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        boards = new ArrayList<>();
        boardAdapter = new ItemBoardAdapter(boards);
        recyclerView.setAdapter(boardAdapter);
        int id = SharedPrefUtils.getInt("ID", -1);
        if (id != -1) {
            JSONObject object = new JSONObject();
            try {
                object.put("Id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            requestRepeatApi = new RequestRepeatApi(view.getContext(), object.toString(), ApiConstant.METHOD_GET_BOARD_BY_ID, this, view);
            requestRepeatApi.executeRequest();
        }
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onFailed(String error) {
        DebugLog.d(error);
    }

    @OnClick(R.id.fab_add)
    void fabAddOnClick() {
        if (SharedPrefUtils.getInt("ID", -1) == -1) {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int id = SharedPrefUtils.getInt("ID", -1);
        if (id != -1) {
            JSONObject object = new JSONObject();
            try {
                object.put("Id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            requestRepeatApi = new RequestRepeatApi(view.getContext(), object.toString(), ApiConstant.METHOD_GET_BOARD_BY_ID, this, view);
            requestRepeatApi.executeRequest();
        }
    }
}


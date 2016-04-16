package com.qtd.muabannhadat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.activity.CreateBoardAcitivity;
import com.qtd.muabannhadat.adapter.ItemBoardAdapter;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.model.Board;
import com.qtd.muabannhadat.request.RequestRepeatApi;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 4/12/2016.
 */
public class BoardFragment extends Fragment implements ResultRequestCallback {
    @Bind(R.id.recyclerView_fragmentBoard)
    RecyclerView recyclerView;

    @Bind(R.id.fab_add)
    FloatingActionButton fabAdd;

    @Bind(R.id.layout_fragment_board)
    SwipeRefreshLayout refreshLayout;

    private ItemBoardAdapter boardAdapter;
    private ArrayList<Board> boards;
    private View view;
    private GridLayoutManager layoutManager;
    private RequestRepeatApi requestRepeatApi;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
    }

    private void initComponent() {
        boards = new ArrayList<>();
        boardAdapter = new ItemBoardAdapter(boards);
        recyclerView.setAdapter(boardAdapter);

        layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshData();
    }

    public void refreshData() {
        int id = SharedPrefUtils.getInt("ID", -1);
        if (id == -1) {
            JSONObject object = new JSONObject();
            try {
                object.put("ID", 3);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            requestRepeatApi = new RequestRepeatApi(view.getContext(), object.toString(), ApiConstant.METHOD_GET_BOARD_BY_ID, this, view);
            requestRepeatApi.executeRequest();
        }
    }

    @Override
    public void onSuccess(String result) {
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Board board = new Board(obj.getInt("BoardID"), obj.getString("BoardName"), obj.getInt("NumberOfApartment"), obj.getString("ImageFirst"));
                boards.add(board);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        boardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String error) {
//        refreshLayout.setRefreshing(false);
        DebugLog.d(error);
    }

    @OnClick(R.id.fab_add)
    void fabAddOnClick() {
//        int id = SharedPrefUtils.getInt("ID", -1);
//        if (id == -1) {
//            Intent intent = new Intent(view.getContext(), LoginActivity.class);
//            getActivity().startActivity(intent);
//        } else {
//            Intent intent = new Intent(view.getContext(), CreateBoardAcitivity.class);
//            getActivity().startActivity(intent);
//        }
        Intent intent = new Intent(view.getContext(), CreateBoardAcitivity.class);
        String s = "";
        for (int i = 0; i < boards.size(); i++) {
            s += ("|" + boards.get(i).getName());
        }
        intent.putExtra("Name", s);
        getActivity().startActivity(intent);
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


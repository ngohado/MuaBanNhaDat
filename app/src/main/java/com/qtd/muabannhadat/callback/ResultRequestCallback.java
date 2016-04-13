package com.qtd.muabannhadat.callback;

/**
 * Created by Ngo Hado on 13-Apr-16.
 */
public interface ResultRequestCallback {
    void onSuccess(String result);
    void onFailed(String error);
}

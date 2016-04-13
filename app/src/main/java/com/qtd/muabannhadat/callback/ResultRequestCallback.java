package com.qtd.muabannhadat.callback;

/**
 * Created by Ngo Hado on 13-Apr-16.
 */
public interface ResultRequestCallback<T> {
    void onSuccess(T result);
    void onFailed(String error);
}

package com.qtd.muabannhadat.location;

/**
 * Created by Ngo Hado on 10-Mar-16.
 */
public interface GetLocationCallback {
    void onAvailableLocation(double latitude, double longitude);

    void onErrorOccur();
}

package com.qtd.muabannhadat.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.qtd.muabannhadat.R;

/**
 * Created by Ngo Hado on 15-Apr-16.
 */
public abstract class BaseMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    public final int MAP_TYPE_NORMAL = GoogleMap.MAP_TYPE_NORMAL;
    public final int MAP_TYPE_HYBRID = GoogleMap.MAP_TYPE_HYBRID;

    private View locationButton;
    private FloatingActionButton btnLocation;

    public abstract int setMapType();

    public abstract float setInitLevelZoom();

    public abstract LatLng setInitLocation();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        initCamera(setInitLocation());
    }

    private final void initListeners() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
        getMap().setOnMapClickListener(this);
    }

    private final void initCamera(LatLng location) {
        if (location == null) {
            location = new LatLng(21.0031177, 105.8201408);
        }
        CameraPosition position = CameraPosition.builder()
                .target(location)
                .zoom(setInitLevelZoom())
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        getMap().animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        getMap().setMapType(setMapType());
        getMap().setTrafficEnabled(true);
        getMap().setMyLocationEnabled(true);
    }

    public abstract View setViewMarker(String title);

    public final Bitmap getBitmapFromView(String title) {
        View view = setViewMarker(title);
        if (view == null) {
            return BitmapFactory.decodeResource(getResources(),
                    R.drawable.rsz_default_mark);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public abstract void addMarkers();

    public final void drawMarker(LatLng latLng, Bitmap bm) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.icon(BitmapDescriptorFactory.fromBitmap(bm));
        getMap().addMarker(options);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

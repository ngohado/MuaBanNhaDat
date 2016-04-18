package com.qtd.muabannhadat.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.qtd.muabannhadat.BaseApplication;
import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.database.MyDatabase;
import com.qtd.muabannhadat.model.District;
import com.qtd.muabannhadat.model.Street;
import com.qtd.muabannhadat.request.BaseRequestApi;
import com.qtd.muabannhadat.subview.SubImageView;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.DialogUtil;
import com.qtd.muabannhadat.util.SharedPrefUtils;
import com.qtd.muabannhadat.util.StringUtil;
import com.qtd.muabannhadat.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class NewpaperActivity extends AppCompatActivity {

    @Bind(R.id.spFrom)
    Spinner spFrom;

    @Bind(R.id.spKind)
    Spinner spKind;

    @Bind(R.id.spCity)
    Spinner spCity;

    @Bind(R.id.spDistrict)
    Spinner spDistrict;

    @Bind(R.id.spStreet)
    Spinner spStreet;

    @Bind(R.id.edtAcrea)
    EditText edtSize;

    @Bind(R.id.edtCost)
    EditText edtCost;

    @Bind(R.id.edtDescribe)
    EditText edtDescribe;

    @Bind(R.id.edtRoom)
    EditText edtRoom;

    @Bind(R.id.edtAddress)
    EditText edtAddress;

    @Bind(R.id.layout_images)
    LinearLayout layoutImages;

    @Bind(R.id.tv_street)
    TextView tvStreet;

    @Bind(R.id.tv_district)
    TextView tvDistrict;

    private ArrayList<String> imagesPathList = new ArrayList<>();

    private MyDatabase myDatabase;

    private List<District> mDistrict;

    private List<Street> mStreet;

    private List<Street> streetsByDistrict = new ArrayList<>();

    private ArrayAdapter<District> districtAdapter;

    private ArrayAdapter<Street> streetAdapter;

    private ProgressDialog dialog;

    @Bind(R.id.toolbar_newspaper)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpaper);
        ButterKnife.bind(this);
        myDatabase = new MyDatabase(this);
        initView();
        initData();
        spinnerFromAndKind();
        spinnerCity();
    }

    public void getStreetsByDistrict(int position) {
        streetsByDistrict.clear();
        int idDistrict = mDistrict.get(position).getIdDistrict();
        for (Street str : mStreet) {
            if (str.getIdDistrict() == idDistrict) {
                streetsByDistrict.add(str);
            }
        }
    }

    private void initData() {
        streetsByDistrict.clear();
        mDistrict = myDatabase.getListDisttrict();
        mStreet = myDatabase.getListStreet();
        districtAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDistrict);
        spDistrict.setAdapter(districtAdapter);
        StringUtil.displayText(mDistrict.get(0).getDistrictName(), tvDistrict);
        getStreetsByDistrict(0);
        streetAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, streetsByDistrict);
        spStreet.setAdapter(streetAdapter);
        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getStreetsByDistrict(position);
                streetAdapter.notifyDataSetChanged();
                StringUtil.displayText(", " + mDistrict.get(position).getDistrictName(), tvDistrict);
                StringUtil.displayText(", " + streetsByDistrict.get(spStreet.getSelectedItemPosition()).getStreetName(), tvStreet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spStreet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringUtil.displayText(", " + streetsByDistrict.get(position).getStreetName(), tvStreet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng tin");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.setMessage("Đang tải...");

        edtDescribe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edtDescribe.length() > 3000) {
                        edtDescribe.setError("Tối đa chỉ 3000 ký tự");
                    }
                }
            }
        });
    }

    private void spinnerCity() {
        String arr[] = {"Hà Nội"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arr);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spCity.setAdapter(adapter);
    }

    private void spinnerFromAndKind() {
        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(this, R.array.item_from, R.layout.support_simple_spinner_dropdown_item);
        fromAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spFrom.setAdapter(fromAdapter);

        ArrayAdapter<CharSequence> kindAdapter = ArrayAdapter.createFromResource(this, R.array.item_kind, R.layout.support_simple_spinner_dropdown_item);
        kindAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spKind.setAdapter(kindAdapter);
    }

    public Boolean validate() {
        if (edtSize.getError() != null) return false;
        if (edtRoom.getError() != null) return false;
        if (edtCost.getError() != null) return false;
        if (edtDescribe.getError() != null) return false;
        return true;
    }

    private Boolean areEmpty() {
        String size = edtSize.getText().toString();
        String price = edtCost.getText().toString();
        String rooms = edtRoom.getText().toString();
        if (size.equals("") || price.equals("") || rooms.equals("")) {
            return true;
        }
        return false;
    }

    @OnClick(R.id.btnPost)
    public void onPostClicked() {
        if (Utility.isNetworkAvailable(this, findViewById(R.id.scrollView), true)) {
            if (areEmpty() || !validate() || !checkCountImage()) {
                Toast.makeText(NewpaperActivity.this, "Hãy nhập đầy đủ và chính xác thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                dialog.show();
                requestGetLatLong();
            }
        }
    }

    ArrayList<String> values = new ArrayList<>();

    @OnClick(R.id.btnSelect)
    public void onImageClicked() {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 6 - layoutImages.getChildCount());
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, values);
        startActivityForResult(intent, 13);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13 && resultCode == Activity.RESULT_OK) {
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            addImages(path);
        }
    }

    private void addImages(List<String> paths) {
        for (String url : paths) {
            SubImageView view = new SubImageView(this);
            view.setupWith(url);
            layoutImages.addView(view, layoutImages.getChildCount() - 1);
        }
    }


    public static String encodeToString(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        return Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
    }

    private boolean checkCountImage() {
        if (layoutImages.getChildCount() - 1 == 5)
            return true;
        Toast.makeText(this, "Bạn chỉ được thêm 5 ảnh", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void requestGetLatLong() {
        String address = "";
        try {
            address = URLEncoder.encode(edtAddress.getText().toString() + tvStreet.getText() + tvDistrict.getText() + ", Hà Nội, Việt Nam", "utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        String urlRequest = String.format("http://maps.google.com/maps/api/geocode/json?address=%s&sensor=false", address);
        JsonObjectRequest request = new JsonObjectRequest(urlRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        BaseApplication.getInstance().addToRequestQueue(request);
    }

    private void handleResponse(JSONObject response) {
        try {
            JSONObject location = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            double latitude = location.getDouble("lat");
            double longitude = location.getDouble("lng");
            sendDataToServer(latitude, longitude);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                sendDataToServer(0, 0);
            } catch (JSONException ex) {

            }
        }
    }

    private void sendDataToServer(double lat, double lng) throws JSONException {
        JSONObject dataRequest = new JSONObject();
        int userId = SharedPrefUtils.getInt(AppConstant.ID, 3);
        dataRequest.put(AppConstant.USER_ID, userId);
        String address = edtAddress.getText().toString() + tvStreet.getText() + tvDistrict.getText() + ", Hà Nội, Việt Nam";
        dataRequest.put(AppConstant.ADDRESS, address);
        String street = spStreet.getSelectedItem().toString();
        dataRequest.put(AppConstant.STREET, street);
        String district = spDistrict.getSelectedItem().toString();
        dataRequest.put(AppConstant.DISTRICT, district);
        String status = spFrom.getSelectedItem().toString();
        dataRequest.put(AppConstant.STATUS, status);
        String kind = spKind.getSelectedItem().toString();
        dataRequest.put(AppConstant.KIND, kind);
        String city = spCity.getSelectedItem().toString();
        dataRequest.put(AppConstant.CITY, city);
        String description = edtDescribe.getText().toString();
        dataRequest.put(AppConstant.DESCRIBE, description);
        int rooms = Integer.parseInt(edtRoom.getText().toString());
        dataRequest.put(AppConstant.ROOM, rooms);
        int price = Integer.parseInt(edtCost.getText().toString());
        dataRequest.put(AppConstant.PRICE, price);
        int size = Integer.parseInt(edtSize.getText().toString());
        dataRequest.put(AppConstant.SIZE, size);

        dataRequest.put(AppConstant.LATITUDE, lat);
        dataRequest.put(AppConstant.LONGITUDE, lng);

        getListImage();
        String image1 = imagesPathList.get(0);
        dataRequest.put(AppConstant.IMAGE1, image1);
        String image2 = imagesPathList.get(1);
        dataRequest.put(AppConstant.IMAGE2, image2);
        String image3 = imagesPathList.get(2);
        dataRequest.put(AppConstant.IMAGE3, image3);
        String image4 = imagesPathList.get(3);
        dataRequest.put(AppConstant.IMAGE4, image4);
        String image5 = imagesPathList.get(4);
        dataRequest.put(AppConstant.IMAGE5, image5);

        BaseRequestApi requestApi = new BaseRequestApi(this, dataRequest.toString(), ApiConstant.METHOD_INSERT_APARTMENT, new ResultRequestCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                DialogUtil.showDialog(NewpaperActivity.this, "Thành công", "Chúc mừng thông tin căn nhà của bạn đã được đăng thành công");
                layoutImages.removeViews(0, 5);
                edtCost.setText("");
                edtAddress.setText("");
                edtRoom.setText("");
                edtDescribe.setText("");
                edtSize.setText("");
            }

            @Override
            public void onFailed(String error) {
                dialog.dismiss();
                DebugLog.e(error);
            }
        });
        requestApi.executeRequest();
    }

    private void getListImage() {
        imagesPathList.clear();
        for (int i = 0; i < layoutImages.getChildCount() - 1; i++) {
            try {
                imagesPathList.add(encodeToString(((SubImageView) layoutImages.getChildAt(i)).getBitmap()));
            } catch (IOException e) {
                imagesPathList.add("");
            }
        }
    }
}

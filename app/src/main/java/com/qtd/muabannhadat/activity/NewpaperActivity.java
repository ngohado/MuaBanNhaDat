package com.qtd.muabannhadat.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.database.MyDatabase;
import com.qtd.muabannhadat.model.District;
import com.qtd.muabannhadat.model.Street;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewpaperActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spFrom;
    private Spinner spKind;
    private Spinner spCity;
    private Spinner spDistrict;
    private Spinner spStreet;
    private EditText edtAcrea;
    private EditText edtCost;
    private EditText edtDescribe;
    private EditText edtRoom;
    private Button btnPost;
    private Button btnSelect;
    private ImageView ivImage;
    private MyDatabase myDatabase;
    private List<District> mDistrict;
    private List<Street> mStreet;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private List<Street> streetsByDistrict = new ArrayList<>();
    private ArrayAdapter<District> districtAdapter;
    private ArrayAdapter<Street> streetAdapter;
    private Street street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpaper);
        myDatabase = new MyDatabase(this);
        initView();
        initData();
        spinnerFrom();
        spinnerCity();
        btnPost.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
    }

    private void initData() {
        streetsByDistrict.clear();
        mDistrict = myDatabase.getListDisttrict();
        mStreet = myDatabase.getListStreet();
        districtAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDistrict);
        spDistrict.setAdapter(districtAdapter);
        for (Street str : mStreet) {
            if (str.getIdDistrict() == mDistrict.get(0).getIdDistrict()) {
                streetsByDistrict.add(str);
            }
        }
        streetAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, streetsByDistrict);
        spStreet.setAdapter(streetAdapter);
        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                streetsByDistrict.clear();
                for (Street str : mStreet) {
                    if (str.getIdDistrict() == mDistrict.get(position).getIdDistrict()) {
                        streetsByDistrict.add(str);
                    }
                }
                streetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initView() {
        spFrom = (Spinner) findViewById(R.id.spFrom);
        spKind = (Spinner) findViewById(R.id.spKind);
        spCity = (Spinner) findViewById(R.id.spCity);
        spDistrict = (Spinner) findViewById(R.id.spDistrict);
        spStreet = (Spinner) findViewById(R.id.spStreet);
        edtAcrea = (EditText) findViewById(R.id.edtAcrea);
        edtCost = (EditText) findViewById(R.id.edtCost);
        edtDescribe = (EditText) findViewById(R.id.edtDescribe);
        edtRoom = (EditText) findViewById(R.id.edtRoom);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        btnPost = (Button) findViewById(R.id.btnPost);

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
        String arr[] = {"--Hà Nội--", "Hà Nội"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.support_simple_spinner_dropdown_item, arr);
        adapter.setDropDownViewResource
                (R.layout.support_simple_spinner_dropdown_item);
        spCity.setAdapter(adapter);
    }

    private void spinnerFrom() {
        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(
                this, R.array.item_from, R.layout.support_simple_spinner_dropdown_item);
        fromAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spFrom.setAdapter(fromAdapter);
        spFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> s = Arrays.asList(getResources().getStringArray(R.array.item_kind));
                if (position == 1) {
                    s = s.subList(0, 8);
                    ArrayAdapter<String> kind = new ArrayAdapter<>(NewpaperActivity.this, R.layout.support_simple_spinner_dropdown_item, s);
                    spKind.setAdapter(kind);
                } else if (position == 2) {
                    s = s.subList(9, 17);
                    ArrayAdapter<String> kind = new ArrayAdapter<String>(NewpaperActivity.this, R.layout.support_simple_spinner_dropdown_item, s);
                    spKind.setAdapter(kind);
                } else {
                    ArrayList<String> arr = new ArrayList<String>();
                    arr.add("--Phân loại--");
                    ArrayAdapter<String> kind = new ArrayAdapter<>(NewpaperActivity.this, R.layout.support_simple_spinner_dropdown_item, arr);
                    spKind.setAdapter(kind);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Boolean validate() {
        if (edtAcrea.getError() != null) return false;
        if (edtRoom.getError() != null) return false;
        if (edtCost.getError() != null) return false;
        if (edtDescribe.getError() != null) return false;
        return true;
    }

    private Boolean areEmpty() {
        String dienTich = edtAcrea.getText().toString();
        String tongTien = edtCost.getText().toString();
        String phong = edtRoom.getText().toString();
        if (dienTich.equals("") | tongTien.equals("") | phong.equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPost:
                if (!isNetworkAvailable()) {
                    Toast.makeText(NewpaperActivity.this, "Hãy kiểm tra kết nối mạng!", Toast.LENGTH_SHORT).show();
                } else {
                    btnPostOnClick();
                }
                break;
            case R.id.btnSelect:
                selectImage();
                break;
        }

    }

    private void selectImage() {
        final CharSequence[] items = {"Chụp ảnh", "Chọn từ thư mục",
                "Thoát"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewpaperActivity.this);
        builder.setTitle("Chọn ảnh!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Chụp ảnh")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Chọn từ thư mục")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Thoát")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == NewpaperActivity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ivImage.setImageBitmap(bm);
    }

    private void btnPostOnClick() {
        if (areEmpty() | !validate()) {
            Toast.makeText(NewpaperActivity.this, "Hãy nhập đầy đủ và chính xác thông tin!", Toast.LENGTH_SHORT).show();
        } else {

        }

    }

    class PostAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private String toJson(String[] params){
        try{
            JSONObject obj=new JSONObject();

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.qtd.muabannhadat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.database.MyDatabase;
import com.qtd.muabannhadat.model.District;
import com.qtd.muabannhadat.model.Street;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewpaperActivity extends AppCompatActivity {
    private Spinner spFrom;
    private Spinner spKind;
    private Spinner spCity;
    private Spinner spDistrict;
    private Spinner spStreet;
    private EditText edtAcrea;
    private EditText edtCost;
    private EditText edtMoTa;
    private Button btnDangTin;
    private MyDatabase myDatabase;
    private List<District> mDistrict;
    private List<Street> mStreet;

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
        edtMoTa = (EditText) findViewById(R.id.edtMoTa);
        btnDangTin = (Button) findViewById(R.id.btnDangTin);
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
}

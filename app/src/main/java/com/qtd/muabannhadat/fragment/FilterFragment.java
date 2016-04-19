package com.qtd.muabannhadat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.activity.ResultSearchActivity;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.database.MyDatabase;
import com.qtd.muabannhadat.model.District;
import com.qtd.muabannhadat.model.Street;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    @Bind(R.id.rgb_status)
    RadioGroup group;

    @Bind(R.id.rbt_rent)
    AppCompatRadioButton rbRent;

    @Bind(R.id.rbt_sale)
    AppCompatRadioButton rbSale;

    @Bind(R.id.sp_kind)
    Spinner spKind;

    @Bind(R.id.sp_district)
    Spinner spDistrict;

    @Bind(R.id.sp_street)
    Spinner spStreet;

    @Bind(R.id.tv_room1)
    TextView tvRoom1;

    @Bind(R.id.tv_room2)
    TextView tvRoom2;

    @Bind(R.id.tv_room3)
    TextView tvRoom3;

    @Bind(R.id.tv_room4)
    TextView tvRoom4;

    @Bind(R.id.tv_room5)
    TextView tvRoom5;

    @Bind(R.id.sp_price_low_range)
    Spinner spPriceLowRange;

    @Bind(R.id.sp_price_high_range)
    Spinner spPriceHighRange;

    @Bind(R.id.sp_area_low_range)
    Spinner spAreaLowRange;

    @Bind(R.id.sp_area_high_range)
    Spinner spAreaHighRange;

    @Bind(R.id.tv_address)
    TextView textView;

    int room = 1;
    private ArrayList<String> imagesPathList = new ArrayList<>();

    private MyDatabase myDatabase;

    private List<District> mDistrict;

    private List<Street> mStreet;

    private List<Street> streetsByDistrict = new ArrayList<>();

    private ArrayAdapter<District> districtAdapter;

    private ArrayAdapter<Street> streetAdapter;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_filter, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDatabase = new MyDatabase(getActivity());
        initComponent();
        initData();
    }

    private void initComponent() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Tìm kiếm nâng cao");
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
//        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        rbSale.setChecked(true);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbt_sale:
                        if (rbSale.isChecked()) {
                            ArrayAdapter<CharSequence> adapterSale = ArrayAdapter.createFromResource(getActivity(), R.array.price_range_for_sale, android.R.layout.simple_spinner_dropdown_item);
                            adapterSale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spPriceLowRange.setAdapter(adapterSale);
                            spPriceHighRange.setAdapter(adapterSale);
                        }
                        break;
                    case R.id.rbt_rent:
                        if (rbRent.isChecked()) {
                            ArrayAdapter<CharSequence> adapterRent = ArrayAdapter.createFromResource(getActivity(), R.array.price_range_for_rent, android.R.layout.simple_spinner_dropdown_item);
                            adapterRent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spPriceLowRange.setAdapter(adapterRent);
                            spPriceHighRange.setAdapter(adapterRent);
                        }
                        break;
                }
            }
        });

    }

    @OnClick(R.id.tv_room1)
    void onClickRoom1() {
        changeRoom(tvRoom1, 1);
    }

    @OnClick(R.id.tv_room2)
    void onClickRoom2() {
        changeRoom(tvRoom2, 2);
    }

    @OnClick(R.id.tv_room3)
    void onClickRoom3() {
        changeRoom(tvRoom3, 3);
    }

    @OnClick(R.id.tv_room4)
    void onClickRoom4() {
        changeRoom(tvRoom4, 4);
    }

    @OnClick(R.id.tv_room5)
    void onClickRoom5() {
        changeRoom(tvRoom5, 5);
    }

    @OnClick(R.id.tv_reset)
    void onlickTvReset() {
        changeRoom(tvRoom1, 1);
        spDistrict.setSelection(0);
        spStreet.setSelection(0);
        spKind.setSelection(0);
        spPriceLowRange.setSelection(0);
        spPriceHighRange.setSelection(0);
        spAreaLowRange.setSelection(0);
        spAreaLowRange.setSelection(0);
    }

    @OnClick(R.id.tv_search)
    void onClickTvSearch() {
        String status = group.getCheckedRadioButtonId() == R.id.rbt_sale ? "Bán" : "Thuê";
        String lowPrice = spPriceLowRange.getSelectedItem().toString();
        String highPrice = spPriceHighRange.getSelectedItem().toString();
        String numberOfRoom = room == 1 ? "Bất kỳ" : (room - 1 + "");
        String lowArea = spAreaLowRange.getSelectedItem().toString();
        String highArea = spAreaHighRange.getSelectedItem().toString();
        String district = spDistrict.getSelectedItem().toString();
        String street = spStreet.getSelectedItem().toString();
        String kind = spKind.getSelectedItem().toString();

        Intent intent = new Intent(getActivity(), ResultSearchActivity.class);
        intent.putExtra(AppConstant.KIND, kind);
        intent.putExtra(AppConstant.STATUS, status);
        intent.putExtra(AppConstant.PRICE_LOW_RANGE, lowPrice);
        intent.putExtra(AppConstant.PRICE_HIGH_RANGE, highPrice);
        intent.putExtra(AppConstant.ROOM, numberOfRoom);
        intent.putExtra(AppConstant.AREA_LOW_RANGE, lowArea);
        intent.putExtra(AppConstant.AREA_HIGH_RANGE, highArea);
        intent.putExtra(AppConstant.DISTRICT, district);
        intent.putExtra(AppConstant.STREET, street);
        startActivity(intent);
    }

    private void initData() {
        streetsByDistrict.clear();
        mDistrict = myDatabase.getListDisttrict();
        mStreet = myDatabase.getListStreet();
        districtAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mDistrict);
        spDistrict.setAdapter(districtAdapter);
        getStreetsByDistrict(0);
        streetAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, streetsByDistrict);
        spStreet.setAdapter(streetAdapter);
        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getStreetsByDistrict(position);
                streetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void changeRoom(TextView tvRoom, int room) {
        this.room = room;
        tvRoom.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        tvRoom.setBackgroundResource(R.drawable.background_room_selected);
        switch (room) {
            case 1:
                unselectedRoom(tvRoom2);
                unselectedRoom(tvRoom3);
                unselectedRoom(tvRoom4);
                unselectedRoom(tvRoom5);
                break;
            case 2:
                unselectedRoom(tvRoom1);
                unselectedRoom(tvRoom3);
                unselectedRoom(tvRoom4);
                unselectedRoom(tvRoom5);
                break;
            case 3:
                unselectedRoom(tvRoom2);
                unselectedRoom(tvRoom1);
                unselectedRoom(tvRoom4);
                unselectedRoom(tvRoom5);
                break;
            case 4:
                unselectedRoom(tvRoom2);
                unselectedRoom(tvRoom3);
                unselectedRoom(tvRoom1);
                unselectedRoom(tvRoom5);
                break;
            case 5:
                unselectedRoom(tvRoom2);
                unselectedRoom(tvRoom3);
                unselectedRoom(tvRoom4);
                unselectedRoom(tvRoom1);
                break;
        }
    }

    private void unselectedRoom(TextView tvRoom) {
        tvRoom.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGrayDark));
        tvRoom.setBackgroundResource(R.drawable.background_room);
    }
}

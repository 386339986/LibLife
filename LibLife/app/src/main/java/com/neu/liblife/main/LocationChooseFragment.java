package com.neu.liblife.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.neu.liblife.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationChooseFragment extends Fragment{

    @BindView(R.id.floor_1)
    Button floor1;
    @BindView(R.id.first_a)
    Button firstA;
    @BindView(R.id.first_b)
    Button firstB;
    @BindView(R.id.first_c)
    Button firstC;
    @BindView(R.id.first_d)
    Button firstD;
    @BindView(R.id.first_e)
    Button firstE;
    @BindView(R.id.first_f)
    Button firstF;
    @BindView(R.id.floor1_layout)
    GridLayout floor1Layout;
    @BindView(R.id.floor_2)
    Button floor2;
    @BindView(R.id.second_a)
    Button secondA;
    @BindView(R.id.second_b)
    Button secondB;
    @BindView(R.id.second_c)
    Button secondC;
    @BindView(R.id.second_d)
    Button secondD;
    @BindView(R.id.second_e)
    Button secondE;
    @BindView(R.id.second_f)
    Button secondF;
    @BindView(R.id.floor2_layout)
    GridLayout floor2Layout;
    @BindView(R.id.floor_3)
    Button floor3;
    @BindView(R.id.third_a)
    Button thirdA;
    @BindView(R.id.third_b)
    Button thirdB;
    @BindView(R.id.third_c)
    Button thirdC;
    @BindView(R.id.third_d)
    Button thirdD;
    @BindView(R.id.third_e)
    Button thirdE;
    @BindView(R.id.third_f)
    Button thirdF;
    @BindView(R.id.floor3_layout)
    GridLayout floor3Layout;
    @BindView(R.id.floor_4)
    Button floor4;
    @BindView(R.id.fourth_a)
    Button fourthA;
    @BindView(R.id.fourth_b)
    Button fourthB;
    @BindView(R.id.fourth_c)
    Button fourthC;
    @BindView(R.id.fourth_d)
    Button fourthD;
    @BindView(R.id.fourth_e)
    Button fourthE;
    @BindView(R.id.fourth_f)
    Button fourthF;
    @BindView(R.id.floor4_layout)
    GridLayout floor4Layout;
    @BindView(R.id.floor_5)
    Button floor5;
    @BindView(R.id.fifth_a)
    Button fifthA;
    @BindView(R.id.fifth_b)
    Button fifthB;
    @BindView(R.id.fifth_c)
    Button fifthC;
    @BindView(R.id.fifth_d)
    Button fifthD;
    @BindView(R.id.fifth_e)
    Button fifthE;
    @BindView(R.id.fifth_f)
    Button fifthF;
    @BindView(R.id.floor5_layout)
    GridLayout floor5Layout;


    private String chooseLocation;
    int click_count = 0;

    private List<Integer> buttonIDs = new ArrayList<>();

    public static LocationChooseFragment newInstance() {
        LocationChooseFragment fragment = new LocationChooseFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_location_choose, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTheme(R.style.AppThemeChoose);
        return view;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        getActivity().setTheme(R.style.AppThemeChoose);

        initView();
    }

    private void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0
        );

        floor1Layout.setLayoutParams(params);
        floor2Layout.setLayoutParams(params);
        floor3Layout.setLayoutParams(params);
        floor4Layout.setLayoutParams(params);
        floor5Layout.setLayoutParams(params);

    }



    public void onChooseClick(View v) {
        Button chooseButton = getView().findViewById(v.getId());
        chooseLocation = chooseButton.getText().toString();
        User.myLocation = chooseLocation;


        Intent intent = new Intent(getActivity(), SeatTableActivity.class);
        intent.putExtra("choose_location", chooseLocation);
        startActivity(intent);
    }

    public void onChooseFloorClick(View view) {
        click_count += 1;
        if (click_count % 2 == 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0
            );
            switch (view.getId()) {
                case R.id.floor_1:
                    floor1Layout.setLayoutParams(params);
                    break;
                case R.id.floor_2:
                    floor2Layout.setLayoutParams(params);
                    break;
                case R.id.floor_3:
                    floor3Layout.setLayoutParams(params);
                    break;
                case R.id.floor_4:
                    floor4Layout.setLayoutParams(params);
                    break;
                case R.id.floor_5:
                    floor5Layout.setLayoutParams(params);
                    break;
                default:
                    break;

            }

        } else {
             LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2
            );
            switch (view.getId()) {
                case R.id.floor_1:
                    floor1Layout.setLayoutParams(params);
                    break;
                case R.id.floor_2:
                    floor2Layout.setLayoutParams(params);
                    break;
                case R.id.floor_3:
                    floor3Layout.setLayoutParams(params);
                    break;
                case R.id.floor_4:
                    floor4Layout.setLayoutParams(params);
                    break;
                case R.id.floor_5:
                    floor5Layout.setLayoutParams(params);
                    break;
                default:
                    break;

            }
        }

    }


    @OnClick({R.id.floor_1, R.id.first_a, R.id.first_b, R.id.first_c, R.id.first_d, R.id.first_e, R.id.first_f, R.id.floor_2, R.id.second_a, R.id.second_b, R.id.second_c, R.id.second_d, R.id.second_e, R.id.second_f, R.id.floor_3, R.id.third_a, R.id.third_b, R.id.third_c, R.id.third_d, R.id.third_e, R.id.third_f, R.id.floor_4, R.id.fourth_a, R.id.fourth_b, R.id.fourth_c, R.id.fourth_d, R.id.fourth_e, R.id.fourth_f, R.id.floor_5, R.id.fifth_a, R.id.fifth_b, R.id.fifth_c, R.id.fifth_d, R.id.fifth_e, R.id.fifth_f})
    public void onViewClicked(View view) {

        int button_id = view.getId();
        if (button_id == R.id.floor_1 || button_id == R.id.floor_2 || button_id == R.id.floor_3 || button_id == R.id.floor_4 || button_id == R.id.floor_5) {
            Button button = getView().findViewById(button_id);
           // button.setOnClickListener(this::onChooseFloorClick);
            onChooseFloorClick(view);
        } else {
            Button button = getView().findViewById(button_id);
           // button.setOnClickListener(this::onChooseClick);
            onChooseClick(view);
        }
    }
}

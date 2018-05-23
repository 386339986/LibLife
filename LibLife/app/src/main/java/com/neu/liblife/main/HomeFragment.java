package com.neu.liblife.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.neu.liblife.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {


    @BindView(R.id.study_mode)
    Button studyMode;
    @BindView(R.id.seat_reserve)
    Button seatReserve;
    @BindView(R.id.book_search)
    Button bookSearch;
    @BindView(R.id.home_more_button)
    Button homeMoreButton;
    @BindView(R.id.home_login_button)
    Button homeLoginButton;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);
        ButterKnife.bind(this, view);
        //   getActivity().setTheme(R.style.AppThemeChoose);
        refreshView();
        return view;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
       // getActivity().setTheme(R.style.AppThemeChoose);
        refreshView();

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshView();
    }

    private void refreshView() {
        if (User.loginUser != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    0,
                    0
            );
            loginLayout.setLayoutParams(params);
        }

    }


    @OnClick({R.id.study_mode, R.id.seat_reserve, R.id.book_search, R.id.home_more_button, R.id.home_login_button})
    public void onViewClicked(View view) {
        Fragment clickFragment = null;
        switch (view.getId()) {
            case R.id.study_mode:

                Intent intent = new Intent(this.getActivity(), StudyModeActivity.class);
                startActivity(intent);

                break;
            case R.id.seat_reserve:

                clickFragment = LocationChooseFragment.newInstance();

                replaceFragment(clickFragment);
                break;
            case R.id.book_search:

                clickFragment = BookSearchFragment.newInstance();
                replaceFragment(clickFragment);
                break;
            case R.id.home_more_button:

                break;
            case R.id.home_login_button:
                Intent intent1 = new Intent(this.getActivity(), LoginTestActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, someFragment);
        transaction.commit();
        /*
        selectedFragment = LocationChooseFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
        */
    }

}

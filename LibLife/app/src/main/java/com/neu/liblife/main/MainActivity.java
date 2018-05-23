package com.neu.liblife.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.neu.liblife.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*
    private ReserveFragment reserveFragment;
    private LocationFragment locationFragment;
    private SlippaperFragment slippaperFragment;
    */

    private ImageView user_head;
    private TextView user_name;
    private TextView user_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user_head = findViewById(R.id.UserHead_label);
        user_name = findViewById(R.id.UserName_label);
        user_sign = findViewById(R.id.UserSign_label);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        漂浮的邮件
        */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        /**
         *  侧面的导航栏
         */

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         *  底部的导航栏
         */

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /**
         *  默认启动导航页
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();


       // InitView();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // InitView();
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     *  底部的导航栏监听事件
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // mTextMessage.setText(R.string.title_home);
                    selectedFragment = HomeFragment.newInstance();
                    break;
                case R.id.navigation_seat:
                    // mTextMessage.setText(R.string.title_home);
                    selectedFragment = LocationChooseFragment.newInstance();
                    break;
                case R.id.navigation_location:
                    //mTextMessage.setText(R.string.title_dashboard);
                    selectedFragment = BookSearchFragment.newInstance();
                    break;
                case R.id.navigation_slippaper:
                    // mTextMessage.setText(R.string.title_notifications);
                    Intent intent = new Intent(MainActivity.this, StudyModeActivity.class);
                    startActivity(intent);
                    return true;

            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    /**
     *  侧面的导航栏监听事件
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment selectedFragment = null;

        if (id == R.id.nav_seats) {
            /*
            Intent intent =new Intent(MainActivity.this, LocationChooseFragment.class);
            startActivity(intent);
            */
            selectedFragment = LocationChooseFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();


        }

        else if(id == R.id.nav_book)
        {
            /*
            Intent intent =new Intent(MainActivity.this, BookSearchFragment.class);
            startActivity(intent);
            */
            selectedFragment = BookSearchFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();

        }

        else if (id == R.id.nav_study) {

            Intent intent = new Intent(MainActivity.this, StudyModeActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_login) {
            Intent intent =new Intent(MainActivity.this, LoginTestActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*

    public void onChooseClick(View v) {
        Button chooseButton = getView().findViewById(v.getId());
        chooseLocation = chooseButton.getText().toString();
        User.myLocation = chooseLocation;


        Intent intent = new Intent(getActivity(),SeatTableActivity.class);
        intent.putExtra("choose_location",chooseLocation);
        startActivity(intent);


        // getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }


    public void onChoose1Click(View view) {
        click_count += 1;
        if(click_count%2 == 0)
        {
            Button choose1Button = getView().findViewById(view.getId());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0
            );
            switch (view.getId())
            {
                case R.id.floor_1 :  floor1Layout.setLayoutParams(params); break;
                case R.id.floor_2 :  floor2Layout.setLayoutParams(params); break;
                case R.id.floor_3 :  floor3Layout.setLayoutParams(params); break;
                case R.id.floor_4 :  floor4Layout.setLayoutParams(params); break;
                case R.id.floor_5 :  floor5Layout.setLayoutParams(params); break;
                default: break;

            }

        }
        else {
            Button choose1Button =getView().findViewById(view.getId());
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
    */
}

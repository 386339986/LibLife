package com.neu.liblife.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neu.liblife.R;
import com.neu.liblife.utils.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatTimePickerActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.people_num_label)
    TextView peopleNumLabel;
    @BindView(R.id.seat_location_label)
    TextView seatLocationLabel;
    @BindView(R.id.seat_num_label)
    TextView seatNumLabel;
    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker datePicker, timePicker;
    private String time;
    private String time2;
    private Calendar nowYear;
    private Button sureButton;
    private TextView doneText;

    private LinearLayout ok_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seattable_time);
        ButterKnife.bind(this);

        selectTime = (RelativeLayout) findViewById(R.id.selectTime);
        selectTime.setOnClickListener(this);

        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(this);

        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ok_layout = findViewById(R.id.ok_layout);
        doneText = findViewById(R.id.done_label);
        //  ok_layout.setVisibility(View.INVISIBLE);
        sureButton = findViewById(R.id.seatSure);

        doneText.setVisibility(View.INVISIBLE);

        seatNumLabel.setText(User.mySeatNum);
        seatLocationLabel.setText(User.myLocation);

        initPicker();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    private void initPicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time2 = SDF.format(new Date());
//        date = time.split(" ")[0];
        //设置当前显示的日期
        currentDate.setText(time2);
        //设置当前显示的时间
        currentTime.setText(time);

        /**
         * 设置年月日
         */
        nowYear = Calendar.getInstance();
        String year = String.valueOf(nowYear.get(Calendar.YEAR));
       // String end_year = String.valueOf(nowYear.get(Calendar.YEAR) + 1);
        String month = String.valueOf(nowYear.get(Calendar.MONTH)+1);
        String day = String.valueOf(nowYear.get(Calendar.DAY_OF_MONTH));

        Log.e("month: ", month);
        Log.e("day: ", day);

        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time2) {
                currentDate.setText(time2);
            }
        }, year +"-"+month+"-"+day +" 00:00", year +"-"+month+"-"+day +" 23:59");
        datePicker.showSpecificTime(true); //显示时和分
        datePicker.setIsLoop(false);
//        datePicker.setDayIsLoop(true);
//        datePicker.setMonIsLoop(true);

        timePicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                currentTime.setText(time);
            }
        }, year +"-"+month+"-"+day +" 00:00", year +"-"+month+"-"+day +" 23:59");//"2027-12-31 23:59"  year + "-12-31 23:59"
        timePicker.showSpecificTime(true);
        timePicker.setIsLoop(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                datePicker.show(time2);
                break;

            case R.id.selectTime:
                // 日期格式为yyyy-MM-dd HH:mm
                timePicker.show(time);
                break;
        }
    }

    public void okClick(View view) {


        ok_layout.setVisibility(View.INVISIBLE);
    }

    public void sureButtonClick(View view) {
        if (sureButton.getText().toString().equals("确认预约")) {
            doneText.setVisibility(View.VISIBLE);
            sureButton.setText("返回");
        } else if (sureButton.getText().toString().equals("返回")) {
            Intent intent = new Intent(SeatTimePickerActivity.this, MainActivity.class);
            startActivity(intent);
        }
        // ok_layout.setVisibility(View.VISIBLE);
    }
}

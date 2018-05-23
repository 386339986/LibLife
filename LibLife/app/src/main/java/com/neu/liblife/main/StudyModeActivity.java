package com.neu.liblife.main;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.neu.liblife.R;
import com.neu.liblife.function.StudyMode;
import com.neu.liblife.utils.ScreenListener;
import com.neu.liblife.utils.TimeCount;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 学习模式界面活动
 */

public class StudyModeActivity extends AppCompatActivity {

    @BindView(R.id.userid_text)
    TextView useridText;
    @BindView(R.id.time)
    TextView timeText;
    @BindView(R.id.score)
    TextView scoreText;
    @BindView(R.id.checkin_button)
    Button checkinButton;
    @BindView(R.id.paper)
    Button paperButton;
    @BindView(R.id.refresh)
    Button refreshButton;
    @BindView(R.id.user_head)
    ImageView userHead;

    private Button start_button;
    private Button pause_button;
    private TextView offnum_text;
    private TextView unlocknum_text;
    private TextView timeCountText;
    private RatingBar ratingBar;

    public static boolean isLogin = false;


    public TimeCount timeCount;    //计时器

    private int on_count = 0;       //亮屏次数
    private int off_count = 0;      //锁屏次数
    private int unlock_count = 0;   //解锁次数

    private StudyMode studyMode = new StudyMode();
    private User activeUser = new User();

    private String userName;
    private String userId;


    private CountDownTimer countDownTimer;

    private static final String FORMAT = "%02d:%02d:%02d";


    private StudyMode myStudyMode;

    private long chooseTime;
    private String chooseTimeString = "00:00:00";
    private int effctive_time;

    private boolean isInStudy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode);
        ButterKnife.bind(this);

        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void onStart() {
        super.onStart();
        getUserInfo();
        refreshButton();
        setActionListener();
      //  RefreshUI();

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


    public void RefreshUI()
    {
        if(studyMode.isChecked) checkinButton.setText("ok");
        else checkinButton.setText("打卡");

        if(studyMode == null) scoreText.setText("0");
        else scoreText.setText(String.valueOf(studyMode.getTotalScore()));
        timeCountText.setText(chooseTimeString);
        unlocknum_text.setText(String.valueOf(unlock_count));
        offnum_text.setText(String.valueOf(off_count));
        timeText.setText(String.valueOf(effctive_time));

    }


    //initial all the UI components here!
    private void initView() {
        start_button = findViewById(R.id.start);
        unlocknum_text = findViewById(R.id.activenum_show);
        offnum_text = findViewById(R.id.offnum_show);
        pause_button = findViewById(R.id.pause);

        timeCountText = findViewById(R.id.time_count);
        ratingBar = findViewById(R.id.learn_rate);

        pause_button.setEnabled(false);   //停止计时按钮初始为锁定状态，开启学习模式熄屏后第二次进入界面时激活
        if (!pause_button.isEnabled()) {
            pause_button.setBackgroundResource(R.drawable.unable_shape);
        }
    }


    private void resetView() {

        on_count = 0;
        off_count = 0;
        unlock_count = 0;


        //onnum_text.setText(String.valueOf(0));
        unlocknum_text.setText(String.valueOf(0));
        offnum_text.setText(String.valueOf(0));
        //timeCountText.setText("00:00:00");
        timeText.setText("0");
    }

    private void refreshButton() {
        if (pause_button.isEnabled()) pause_button.setBackgroundResource(R.drawable.stop_shape);
        else pause_button.setBackgroundResource(R.drawable.unable_shape);


        if (start_button.isEnabled()) start_button.setBackgroundResource(R.drawable.start_shape);
        else start_button.setBackgroundResource(R.drawable.unable_shape);



    }

    private void getUserInfo() {

        if (User.loginUser != null) {
            activeUser = User.loginUser;
            // activeUser.setUserName("Taylor");
            studyMode.setUser(activeUser);
            userHead.setImageResource(activeUser.getHeadId());
            useridText.setText(activeUser.getId());
            //onRestart();


        } else {
            Intent intent = new Intent(StudyModeActivity.this, LoginTestActivity.class);
            startActivity(intent);
        }

    }

    private void setActionListener() {

        //开始学习
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(StudyModeActivity.this, "请先锁屏手机开始学习！否则不将计算学习时长。", Toast.LENGTH_SHORT).show();

                if(!isInStudy)
                {
                    resetView();   //初始化界面

                    HandleScreenListener();         //开始监听屏幕

                    //setStudyTime();
                    TimeChoose();
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(StudyModeActivity.this).create();
                    alertDialog.setTitle("提示");
                    alertDialog.setMessage("\n请先结束学习再重新定时学习！");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "知道了",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }

            }
        });

        //结束学习
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                effctive_time = timeCount.getEffectiveTime();
                RefreshScoreView();
                ShowResult(effctive_time);

                start_button.setEnabled(true);
                isInStudy = false;

                studyMode.isStudying = false;  //单次学习结束后，立即更新学习模式的 #单次学习总时长#

                unlocknum_text.setText(String.valueOf(unlock_count));
                offnum_text.setText(String.valueOf(off_count));
                timeText.setText(String.valueOf(effctive_time));

                timeCount.stopAllTimer();
                pause_button.setEnabled(false);     //停止计时后，恢复为锁定状态
                if (pause_button.isEnabled()) {
                    pause_button.setBackgroundResource(R.drawable.stop_shape);
                }

                if (countDownTimer != null) {
                    countDownTimer.cancel();

                }

                ratingBar.setNumStars(CountRateBar(effctive_time, chooseTime));


            }
        });


        //签到按钮
        checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkinButton.setText("OK");
                checkinButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ok2_24, 0,0, 0);
               // checkinButton.setBackgroundResource(R.drawable.ok24);

                checkinButton.setEnabled(false);
                studyMode.setTotalCheckIn(studyMode.getTotalCheckIn() + 11);   //测试
                studyMode.isChecked = true;
                RefreshScoreView();
                //  checkinText.setText(String.valueOf(studyMode.getTotalCheckIn()));

            }
        });

        //刷新按钮
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RefreshScoreView();

            }
        });


        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //RefreshScoreView();

                if (!studyMode.getActiveInfo().get(0)) {
                    Toast.makeText(StudyModeActivity.this, "累积学习积分不少于1000分可开启此功能", Toast.LENGTH_SHORT).show();
                } else {
                    activeUser.setMyStudyMode(studyMode);

                    Intent intent = new Intent(StudyModeActivity.this, PaperDeliverActivity.class);
                    intent.putExtra("ActiveUser", activeUser);
                    startActivity(intent);

                }
            }
        });

    }


    private void RefreshScoreView() {
        // usernameText.setText(studyMode.getUser().getUserName());
        useridText.setText(studyMode.getUser().getId());

        //  checkinText.setText(String.valueOf(studyMode.getTotalCheckIn()));

        //正在学习模式
        if (timeCount != null && studyMode.isStudying) {

            Log.e("if 1: ", "正学习");

            studyMode.setTotalStudyTime(timeCount);                         //更新总的学习时长

            timeText.setText(String.valueOf(studyMode.getTotalStudyTime()));  //获取总的学习时长
            studyMode.countTotalScore();
            scoreText.setText(String.valueOf(studyMode.getTotalScore()));      //获取总积分
        }

        //仅打卡，未开启学习模式
        else if (studyMode.isStudying) {
            Log.e("if 2: ", "未学习");

            timeText.setText(String.valueOf(studyMode.getTotalStudyTime()));  //获取总的学习时长
            studyMode.countTotalScore();
            scoreText.setText(String.valueOf(studyMode.getTotalScore()));       //获取总积分
        }

        //学习模式结束
        else {
            Log.e("if 3: ", "结束学习");

            studyMode.isStudying = true; //
            timeText.setText(String.valueOf(studyMode.getTotalStudyTime()));  //获取总的学习时长
            studyMode.countTotalScore();
            scoreText.setText(String.valueOf(studyMode.getTotalScore()));       //获取总积分

        }

        studyMode.updateActiveInfo();
    }


    private void ShowResult(int time) {
        int score = (int) (time / 60.0 * 100);
        AlertDialog alertDialog = new AlertDialog.Builder(StudyModeActivity.this).create();
        alertDialog.setTitle("提示");
        if (score >= 0) {
            alertDialog.setMessage("\n本次学习收获" + score + "学霸积分!");
        } else alertDialog.setMessage("\n本次学习扣除了" + score + "学霸积分 ww");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "知道了",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private int CountRateBar(int real_time, long chooseTime) {
        float rate = 0;
        rate = (float) (real_time / (chooseTime / 1000.0));

        Log.e("rate: ", String.valueOf(rate));

        if (rate > 0.85) return 5;
        else if (rate > 0.7) return 4;
        else if (rate > 0.5) return 3;
        else if (rate > 0.3) return 2;
        else return 1;

    }


    private long TimeChoose() {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(StudyModeActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //  timeCountText.setText( selectedHour + ":" + selectedMinute);
                chooseTimeString = String.format(FORMAT, selectedHour, selectedMinute, 0);
                chooseTime = (selectedHour * 3600 + selectedMinute * 60) * 1000;
                timeCountText.setText(chooseTimeString);

                AlertDialog alertDialog = new AlertDialog.Builder(StudyModeActivity.this).create();
                alertDialog.setTitle("提示");

                //Toast.makeText(StudyModeActivity.this, "请先锁屏手机开始学习！否则不将计算学习时长。", Toast.LENGTH_SHORT).show();
                alertDialog.setMessage("\n请先锁屏手机开始学习, 否则不将计算学习时长!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "去锁屏",
                            new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                alertDialog.show();

            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

        return chooseTime;
    }

    private void CountDownTime(long countTime) {
        countDownTimer = new CountDownTimer(countTime, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                timeCountText.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished))),
                        (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))));
            }

            public void onFinish() {
                timeCountText.setText("学习完成！");
                pause_button.performClick();
            }
        }.start();

    }


    private void HandleScreenListener() {
        ScreenListener l = new ScreenListener(this);

        l.begin(new ScreenListener.ScreenStateListener() {

            int flag;  //设置屏幕状态标志

            //处理解锁接口事件
            @Override
            public void onUserPresent() {
                if (off_count > 0) {
                    refreshButton();//未熄屏之前，解锁事件不予处理
                    unlock_count++;
                    flag = 1;

                    timeCount.pauseAllTimer();             //停止所有的计时器

                    timeCount.start_resumeTimer(1);  //开启解锁计时
                    timeCount.handelTimeTask(1);

                    timeCount.start_resumeTimer(0);  //开启亮屏计时
                    timeCount.handelTimeTask(0);
                }
                Log.e("onUserPresent", "onUserPresent, count:" + unlock_count);

            }

            //处理亮屏接口事件
            @Override
            public void onScreenOn() {
                if (off_count > 0) {
                    refreshButton();//未熄屏之前，亮屏事件不予处理
                    pause_button.setEnabled(true);
                    on_count++;
                    flag = 0;

                    timeCount.pauseAllTimer();             //停止所以计时器

                    timeCount.start_resumeTimer(0);   //开启亮屏计时
                    timeCount.handelTimeTask(0);
                }
                Log.e("onScreenOn", "onScreenOn, count:" + on_count);

            }

            //处理熄屏接口事件
            @Override
            public void onScreenOff() {
                refreshButton();
                isInStudy = true;

                off_count++;
                if (off_count == 1) {
                    timeCount = new TimeCount();
                    CountDownTime(chooseTime);
                }
                flag = -1;

                timeCount.pauseAllTimer();            //停止所有计时器

                timeCount.start_resumeTimer(-1); //开启熄屏计时
                timeCount.handelTimeTask(-1);

                Log.e("onScreenOff", "onScreenOff,count:" + off_count);
            }
        });

    }



}

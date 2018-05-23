package com.neu.liblife.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.neu.liblife.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  登录界面活动
 */

public class LoginTestActivity extends AppCompatActivity {

    public User user = new User();
    @BindView(R.id.userid_input)
    EditText useridInput;
    @BindView(R.id.userpasswd_input)
    EditText userpasswdInput;
    @BindView(R.id.login_button)
    Button loginButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(User.loginTime>=3)
        {
            loginButton.setEnabled(false);
            showLoginInfo("密码已输错3次！两小时后再试");
            loginButton.setBackgroundResource(R.drawable.login3_shape);

        }
    }

    @OnClick(R.id.login_button)
    public void loginClick(View view) {
        if(User.loginTime>=3)
        {
            loginButton.setEnabled(false);
            showLoginInfo("密码已输错3次！两小时后再试");
            loginButton.setBackgroundResource(R.drawable.login3_shape);

        }
        else{
            String user_id = useridInput.getText().toString();
            String user_passwd = userpasswdInput.getText().toString();
            int login_result = isAccountValid(user_id,user_passwd);
            switch (login_result)
            {
                case -1: showLoginInfo("用户名密码不匹配! 请重新输入"); break;
                case 0:  showLoginInfo("用户名或密码不能为空!"); break;
                case 1:
                    user.setId(user_id);
                    user.setPassword(user_passwd);
                    user.setUserName("tanghao");
                    user.setHeadId(getResources().getIdentifier("drawable/user7",null,getPackageName()));
                    //  user.setHead(new ImageView());

                    User.loginUser = user;

                    // onDestroy();
                    Intent intent = new Intent(LoginTestActivity.this,StudyModeActivity.class);
                    String tag = "yes";
                    intent.putExtra("tag",tag);

                    finish();
                    break;
                default: break;


            }

        }

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



    private int isAccountValid(String id, String passwd) {


        if(id.equals("") || passwd.equals(""))
        {
            return 0;
        }

        else if(id.equals(passwd)) return 1;

        else
        {
            User.loginTime ++;
            return -1;
        }
    }

    private void showLoginInfo(String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginTestActivity.this).create();
        alertDialog.setTitle("提示");

        alertDialog.setMessage("\n"+message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}

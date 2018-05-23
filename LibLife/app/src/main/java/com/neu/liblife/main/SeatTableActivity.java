package com.neu.liblife.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.neu.liblife.R;
import com.neu.liblife.function.SeatTable;

public class SeatTableActivity extends AppCompatActivity {
    public SeatTable seatTableView;
    private String chooseLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seat_table);
        setChooseLocation();

        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName("图书馆"+chooseLocation);//
        seatTableView.setMaxSelected(1);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (row == 2 || (row ==0||row==1||row==3 && column<18 &&column >8)
                        || (row ==0||row==1||row==3 && column<30 &&column >24)
                        ||row == 6 || row == 10|| row ==14 || row == 18 || row == 22|| column== 8|| column== 9
                        || column==13||(column==17&&row>18)||column==18||column==19 ||column==23 ||column==27
                        || column==28 ||column==29 ||column==33 ||row ==25 || row==26 || row==27
                        || (row>24&&row<31&&column<13) ||((row==3||row==4||row==5)&&(column!=16||column!=21))
                        || (row>6&&row<18) && (column==10 || column==12|| column==15|| column==16|| column==20||
                        column==22|| column==24 || column==26|| column==31|| column==32|| column==35|| column==36)
                        ) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isDeskSeat(int row, int column) {
                if(((row==4 || row ==8 || row ==12|| row ==16|| row ==20|| row ==24|| row ==28)&&column>=0&&column<9)
                        ||(row>18 && row<23 && column==11)  ||((row==23 ||row==24)&& column==11)
                        ||(row>18 && row<23 && column==15)  ||((row==23 ||row==24)&& column==15)
                        ||(row>18 && row<23 && column==21)  ||((row==23 ||row==24)&& column==21)
                        ||(row>18 && row<23 && column==25)  ||((row==23 ||row==24)&& column==25)
                        ||(row>18 && row<23 && column==31)  ||((row==23 ||row==24)&& column==31)
                        ||(row>18 && row<23 && column==35)  ||((row==23 ||row==24)&& column==35)
                        ||((row==28 || row==29) && column==15)||((row==28 || row==29) && column==21)
                        ||((row==28 || row==29) && column==25)||((row==28 || row==29) && column==31)
                        ||((row==28 || row==29) && column==35)){
                    return true;
                }
                return false;
            }

            @Override
            public boolean isShelfSelected(int row, int column){

                if(row==seatTableView.getSelectedrow()&&column==seatTableView.getSelectedcolumn())
                    return true;
                else return false;
                //此处是一个接口，应该有一个bookFinder，输入是索书号，输出是书架的row 和column，传到这里返回True
            }

            @Override
            public boolean isShelf(int row, int column) {
                if(((row==7 ||row==8 ||row==9||row==11 ||row==12 ||row==13 ||row==15||row==16||row==17)&& column==11)
                        ||((row==7 ||row==8 ||row==9||row==11 ||row==12 ||row==13||row==15||row==16||row==17)&& column==14)
                        ||((row==7 ||row==8 ||row==9||row==11 ||row==12 ||row==13||row==15||row==16||row==17)&& column==17)
                        ||((row==7 ||row==8 ||row==9||row==11 ||row==12 ||row==13||row==15||row==16||row==17)&& column==21)
                        ||((row==7 ||row==8 ||row==9||row==11 ||row==12 ||row==13||row==15||row==16||row==17)&& column==25)
                        ||((row==7 ||row==8 ||row==9||row==11 ||row==12 ||row==13||row==15||row==16||row==17)&& column==30)
                        ||((row==7 ||row==8 ||row==9||row==11 ||row==12 ||row==13||row==15||row==16||row==17)&& column==34)
                        ||((row==3 ||row==4 ||row==5)&& column==21)
                        ||((row==4 ||row==5)&& column==16)){
                    return true;
                }
                return false;
            }

            @Override
            public boolean isSold(int row, int column) {
                if((row==17&&(column==1||column==2||column==4||column==6))||
                        (row==11&&(column==3||column==2||column==5||column==8))||
                        (row==13&&(column==1||column==2||column==4||column==7))||
                        (row==19&&(column==1||column==5||column==4||column==6||column==16||column==20))
                        ||(row==seatTableView.getCheckedrow()&&column==seatTableView.getCheckedcolumn())){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }


            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(30,37);
       // seatTableView.BookFinder("Java");
        seatTableView.getSelectedSeat();

        initSeatTable();
        User.mySeatTable = seatTableView;

    }

    private void setChooseLocation()
    {
        Intent intent = getIntent();
        chooseLocation = intent.getStringExtra("choose_location");
    }

    private void initSeatTable()
    {
        if(User.mySeatTable != null) this.seatTableView = User.mySeatTable;
    }

    public void seatOkClick(View view) {
        if(User.mySeatNum.equals(""))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(SeatTableActivity.this).create();
            alertDialog.setTitle("提示");

            alertDialog.setMessage("\n请先选择座位!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "去选座位",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else{
            Intent intent = new Intent(SeatTableActivity.this,SeatTimePickerActivity.class);
            startActivity(intent);
        }

    }
}

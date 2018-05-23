package com.neu.liblife.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import com.neu.liblife.R;
import com.neu.liblife.function.SeatTable;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookSearchFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.search_field)
    EditText searchField;
    @BindView(R.id.search_book)
    Button searchBook;
    @BindView(R.id.search_state_text)
    TextView searchStateText;
    @BindView(R.id.bookView)
    SeatTable bookView;


    public static BookSearchFragment newInstance()
    {
        BookSearchFragment fragment = new BookSearchFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_book_search, container, false);
        ButterKnife.bind(this,view);
        searchBook.setOnClickListener(this);
        return view;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        bookView.setScreenName("图书馆3层A区");//
        bookView.setMaxSelected(0);//设置最多选中

        bookView.setSeatChecker(new SeatTable.SeatChecker() {

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

                if(row==bookView.getSelectedrow()&&column==bookView.getSelectedcolumn())
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
                        ||(row==bookView.getCheckedrow()&&column==bookView.getCheckedcolumn())){
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
        bookView.setData(30,37);
        // bookView.BookFinder("Java");
        bookView.getSelectedSeat();

    }


    private void SearchBook()
    {

        String book_name = searchField.getText().toString();
        if(bookView.BookFinder(book_name) == 0) searchStateText.setText("未查询到相关书籍信息!");
        else searchStateText.setText("以下是搜索到的书籍位置:");
    }


    @Override
    public void onClick(View v) {
        SearchBook();
    }
}

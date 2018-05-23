package com.neu.liblife.main;


import android.widget.ImageView;

import com.neu.liblife.function.Library;
import com.neu.liblife.function.SeatTable;
import com.neu.liblife.function.StudyMode;

import java.io.Serializable;

/**
 *  用户类
 */

@SuppressWarnings("serial")
public class User implements Serializable {

    private String id;
    private String userName;
    private String password;
    private ImageView head;
    private StudyMode myStudyMode;
    private int headId;

    public static User loginUser;
    public static SeatTable mySeatTable;
    public static String myLocation;
    public static String mySeatNum = "";
    public static int loginTime = 0;




    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHead(ImageView head) {
        this.head = head;
    }

    public ImageView getHead() {
        return head;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addtoUsersDB()
    {

    }

    public StudyMode getMyStudyMode() {
        return myStudyMode;
    }

    public void setMyStudyMode(StudyMode myStudyMode) {
        this.myStudyMode = myStudyMode;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public int getHeadId() {
        return headId;
    }
}



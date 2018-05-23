package com.neu.liblife.function;


import com.neu.liblife.function.extend_function.ForHelp;
import com.neu.liblife.function.extend_function.PaperDeliver;
import com.neu.liblife.main.User;
import com.neu.liblife.utils.TimeCount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *   学习模式  （与图书查询、座位预约 两大基本功能并列）
 */

public class StudyMode implements Serializable{

    private User user;               //当前用户
    private int totalCheckIn = 0;    //总签到次数
    private int totalStudyTime = 0;  //总学习时长
    private int totalScore = 0;      //总积分
    public boolean isStudying = true;
    public boolean isChecked;

    private List<Boolean> activeInfo;

    private PaperDeliver myPaperDeliver;
    private ForHelp myForHelp;


    public StudyMode()
    {
        this.activeInfo = new ArrayList<>();
        Boolean isPaperActive = false;
        Boolean isForHelpActive = false;
        activeInfo.add(isPaperActive);
        activeInfo.add(isForHelpActive);
    }

    /**
     *   计算总积分， 计算规则暂定如下：
     */
    public void countTotalScore()
    {
        this.totalScore = this.totalCheckIn * 100 + (int) (this.totalStudyTime / 60.0 * 100);
    }


    /**
     *   获取当前用户
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setTotalCheckIn(int totalCheckIn) {
        this.totalCheckIn = totalCheckIn;
    }

    public int getTotalCheckIn() {
        return totalCheckIn;
    }


    public void setTotalStudyTime(TimeCount timeCount){
        this.totalStudyTime = this.totalStudyTime + timeCount.getEffectiveTime();   //有效学习时间： 熄屏时间 - 解锁亮屏时间
    }

    public int getTotalStudyTime() {
        return totalStudyTime;
    }

    public int getTotalScore(){
        return totalScore;
    }

    public void updateActiveInfo()
    {

        if(this.totalScore >= 1000) activeInfo.set(0,true);
        else if(this.totalScore >= 2000) activeInfo.set(1,true);
    }

    public List<Boolean> getActiveInfo() {
        return activeInfo;
    }

    public void setMyPaperDeliver(PaperDeliver myPaperDeliver) {
        this.myPaperDeliver = myPaperDeliver;
    }

    public PaperDeliver getMyPaperDeliver() {
        return myPaperDeliver;
    }
}
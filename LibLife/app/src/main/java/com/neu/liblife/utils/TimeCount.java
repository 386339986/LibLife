package com.neu.liblife.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 此类已封装好，无需修改
 */

public class TimeCount {

    private Timer on_timer;
    private Timer off_timer;
    private Timer unlock_timer;

    public int on_counter = 0;      //亮屏时间计数
    public int off_counter = 0;     //熄屏时间计数
    public int unlock_counter = 0;  //活动时间计数   （亮屏时间-活动时间 = 解锁等待时间）

    public ArrayList<Integer> on_time;
    public ArrayList<Integer> off_time;
    public ArrayList<Integer> unlock_time;

    public TimeCount()
    {
        on_time = new ArrayList<>();
        off_time = new ArrayList<>();
        unlock_time = new ArrayList<>();
    }

    /**
     *  处理计时器相关任务
     */
    public void handelTimeTask(int flag)
    {
        //亮屏开始计时
        if(flag == 0 && this.on_timer!= null){
            TimerTask on_timeTask = new TimerTask() {
                @Override
                public void run() {
                    on_counter++;
                    Log.e("ScreenOn ", "time: " + on_counter);
                }
            };
            this.on_timer.schedule(on_timeTask,1000,1000);
        }

        //熄屏任务开始计时
        else if(flag == -1 && this.off_timer!= null){
            TimerTask off_timeTask = new TimerTask() {
                @Override
                public void run() {
                    off_counter++;
                    Log.e("ScreenOff ", "time: " + off_counter);
                }
            };
            this.off_timer.schedule(off_timeTask,1000,1000);
        }

        //解锁任务开始计时
        else if(flag == 1 && this.unlock_timer != null){
            TimerTask unlock_timeTask = new TimerTask() {
                @Override
                public void run() {
                    unlock_counter++;
                    Log.e("ScreenUnlock ", "time: " + unlock_counter);
                }
            };
            this.unlock_timer.schedule(unlock_timeTask,1000,1000);
        }

    }


    public void puaseTimer(int type)
    {
        switch (type){
            case 0:
                if(this.on_timer != null) {
                    this.on_timer.cancel();
                    this.on_timer.purge();
                    this.on_timer = null;
                    break;
                }
                else{
                    break;
                }
            case 1:
                if(this.unlock_timer != null) {
                    this.unlock_timer.cancel();
                    this.unlock_timer.purge();
                    this.unlock_timer = null;
                    break;
                }
                else{
                    break;
                }
            case -1:
                if(this.off_timer != null) {
                    this.off_timer.cancel();
                    this.off_timer.purge();
                    this.off_timer = null;
                    break;
                }
                else{
                    break;
                }
            default: break;
        }
    }



    /**
     *  启动计时器
     */
    public void start_resumeTimer(int type)
    {
        switch (type) {
            case 0:
                if(this.on_timer == null) {
                    this.on_timer = new Timer();
                    break;
                }
                else{
                    Log.e(" on_timer start: ", "fail to start, not null!");
                    break;
                }
            case 1:
                if(this.unlock_timer == null) {
                    this.unlock_timer = new Timer();
                    break;
                }
                else{
                    Log.e(" unlock_timer start: ", "fail to start, not null!");
                    break;
                }
            case -1:
                if(this.off_timer == null){
                    this.off_timer = new Timer();
                    break;
                }
                else {
                    Log.e(" off_timer start: ", "fail to start, not null!");
                    break;
                }
            default: break;
        }
    }


    /**
     *  停止所有计时器及相关任务
     */
    public void pauseAllTimer()
    {
        if(this.on_timer != null && this.unlock_timer == null && this.off_timer != null){
            this.on_timer.cancel();
            this.on_timer.purge();
            this.on_timer = null;

            this.off_timer.cancel();
            this.off_timer.purge();
            this.off_timer = null;
        }
        else if(this.on_timer != null && this.unlock_timer != null && this.off_timer == null){
            this.on_timer.cancel();
            this.on_timer.purge();
            this.on_timer = null;

            this.unlock_timer.cancel();
            this.unlock_timer.purge();
            this.unlock_timer = null;

        }
        else if(this.on_timer == null && this.unlock_timer == null && this.off_timer != null){
            this.off_timer.cancel();
            this.off_timer.purge();
            this.off_timer = null;
        }
        else if(this.on_timer != null && this.unlock_timer == null && this.off_timer == null){
            this.on_timer.cancel();
            this.on_timer.purge();
            this.on_timer = null;
        }
        else{
            try{
                this.off_timer.cancel();
                this.on_timer.cancel();
                this.unlock_timer.cancel();
            }catch (Exception e){

            }
        }
    }


    /**
     *  关闭计时器
     */
    public void stopAllTimer()
    {
        if(this.unlock_timer != null || this.on_timer != null || this.off_timer != null)  {
            pauseAllTimer();
            this.on_counter = 0;
            this.off_counter = 0;
            this.unlock_counter = 0;
        }
    }

    public Timer getTimer(int type)
    {
        switch (type){
            case 0:
                return on_timer;
            case 1:
                return unlock_timer;
            case -1:
                return off_timer;
            default: return null;
        }

    }


    /**
     *  获取熄屏、亮屏、解锁相关时间.   格式为:  时:分:秒（00:00:00）(arraylist[0]: arraylist[1]: arraylist[2])
     */
    public ArrayList<Integer> getOff_time() {
        int hour,min,sec;
        this.off_time = new ArrayList<>();
        if(off_counter>=0 && off_counter<3600){
            hour = this.off_counter/3600;
            min = this.off_counter/60;
            sec = this.off_counter%60;
            off_time.add(0,hour);
            off_time.add(1,min);
            off_time.add(2,sec);
            return off_time;
        }
        else{
            hour = this.off_counter/3600;
            min = this.off_counter%3600/60;
            sec = this.off_counter%60;
            off_time.add(0,hour);
            off_time.add(1,min);
            off_time.add(2,sec);
            return off_time;
        }
    }

    public ArrayList<Integer> getOn_time() {
        int hour,min,sec;
        this.on_time = new ArrayList<>();
        if(on_counter>=0 && on_counter<3600){
            hour = this.on_counter/3600;
            min = this.on_counter/60;
            sec = this.on_counter%60;
            off_time.add(0,hour);
            off_time.add(1,min);
            off_time.add(2,sec);
            return on_time;
        }
        else{
            hour = this.on_counter/3600;
            min = this.on_counter%3600/60;
            sec = this.on_counter%60;
            off_time.add(0,hour);
            off_time.add(1,min);
            off_time.add(2,sec);
            return on_time;
        }
    }

    public ArrayList<Integer> getUnlock_time() {
        int hour,min,sec;
        this.unlock_time = new ArrayList<>();
        if(unlock_counter>=0 && unlock_counter<3600){
            hour = this.unlock_counter/3600;
            min = this.unlock_counter/60;
            sec = this.unlock_counter%60;
            off_time.add(0,hour);
            off_time.add(1,min);
            off_time.add(2,sec);
            return unlock_time;
        }
        else{
            hour = this.unlock_counter/3600;
            min = this.unlock_counter%3600/60;
            sec = this.unlock_counter%60;
            off_time.add(0,hour);
            off_time.add(1,min);
            off_time.add(2,sec);
            return unlock_time;
        }
    }

    public int getEffectiveTime()
    {
        int effectiveTime = this.off_counter - this.unlock_counter;
        return effectiveTime;
    }

}

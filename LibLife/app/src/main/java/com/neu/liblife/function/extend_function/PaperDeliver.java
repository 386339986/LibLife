package com.neu.liblife.function.extend_function;


import android.media.Image;
import android.widget.ImageView;

import com.neu.liblife.function.StudyMode;
import com.neu.liblife.main.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *  小功能一： 小纸条
 */

public class PaperDeliver implements Serializable{

    private User activeUser;        //当前使用小纸条的用户
    private Paper paper;            //小纸条内容
    private List<Paper> receivedPapers; //收到的小纸条
    private List<Paper> sentPapers;     //发送的小纸条


    public PaperDeliver()
    {
        receivedPapers = new ArrayList<>();
        sentPapers = new ArrayList<>();
    }


    /**
     *  内部类Paper, 记录小纸条内容和信息
     */

    public class Paper implements Serializable
    {

        public static final int TYPE_PAPER_SENT = 1;
        public static final int TYPE_PAPER_RECEIVED = 2;

        private String textContent;  //小纸条 文本 内容
        private Image imageContent;  //小纸条 图片 内容
        private User sender;         //小纸条 发送者
        private User receiver;       //小纸条 接收者
        private Date createTime;     //小纸条 创建时间
        private int status_type;

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }
        public String getTextContent() {
            return textContent;
        }

        public void setImageContent(Image imageContent) {
            this.imageContent = imageContent;
        }

        public Image getImageContent() {
            return imageContent;
        }

        public void setCreateTime(Date date) {
            this.createTime = date;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setSender(User sender) {
            this.sender = sender;
        }

        public void setReceiver(User receiver) {
            this.receiver = receiver;
        }

        public User getSender() {
            return sender;
        }

        public User getReceiver() {
            return receiver;
        }

        public int getStatus_type() {
            return status_type;
        }

        public void setStatus_type(int status_type) {
            this.status_type = status_type;
        }
    }


    /**
     *  加入小纸条到历史发送信息/历史接收信息
     */

    public void addToHistoryPapers(Paper paper)
    {
        if(paper.getSender() == this.getCurrentActiveUser()) this.sentPapers.add(paper);
        else this.receivedPapers.add(paper);
    }



    /**
     *  关于小纸条的一系列 set/get 方法
     */

    //获取当前使用小纸条的用户
    public User getCurrentActiveUser()
    {
        return this.activeUser;
    }

    public void setActiveUser(User acitiveUser) {
        this.activeUser = acitiveUser;
    }


    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public void sendPaper(Paper send_paper, User to_user)
    {
        this.addToHistoryPapers(send_paper);

        StudyMode toUserStudyMode = to_user.getMyStudyMode();
        PaperDeliver toUserPaperDeliver = toUserStudyMode.getMyPaperDeliver();

        toUserPaperDeliver.setPaper(send_paper);
        //toUserPaperDeliver.addToHistoryPapers(send_paper);

    }

    public void receivePaper(Paper receive_paper,User from_user)
    {
        this.setPaper(receive_paper);
        this.addToHistoryPapers(receive_paper);


    }

    public User getSender()
    {
        return this.getPaper().getSender();
    }


}


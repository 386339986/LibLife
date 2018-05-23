package com.neu.liblife.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neu.liblife.R;
import com.neu.liblife.function.extend_function.PaperDeliver;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 *  消息列表的适配器，数据绑定。 待测试...
 */
public class MessageListAdapter extends RecyclerView.Adapter{
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private  static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private Context mContext;
    private List<PaperDeliver> mMessageList;

    public MessageListAdapter(Context context, List<PaperDeliver> messageList) {
        this.mContext = context;
        this.mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


    /**
     *  获取当前消息是 #接收# 还是 #发送# 的状态
     */
    @Override
    public int getItemViewType(int position) {
        PaperDeliver message =  mMessageList.get(position);

        if (message.getPaper().getStatus_type() == 1) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }


    /**
     *  根据消息接发状态新建一个 消息内容泡泡
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }


    /**
     *  数据绑定到UI控件
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PaperDeliver message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }



    /**
     *  创建一个发送消息的泡泡
     */
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
        }

        void bind(PaperDeliver message) {
            messageText.setText(message.getPaper().getTextContent());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(dateFormat.format(message.getPaper().getCreateTime()));

        }
    }


    /**
     *  创建一个接收消息的泡泡
     */
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(PaperDeliver message) {

            messageText.setText(message.getPaper().getTextContent());

            timeText.setText(dateFormat.format(message.getPaper().getCreateTime()));

            nameText.setText(message.getSender().getUserName());
            profileImage.setImageResource(message.getSender().getHeadId());
        }
    }
}
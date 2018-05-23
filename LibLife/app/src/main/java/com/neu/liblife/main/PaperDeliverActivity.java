package com.neu.liblife.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neu.liblife.R;
import com.neu.liblife.function.extend_function.PaperDeliver;
import com.neu.liblife.utils.MessageListAdapter;
import com.neu.liblife.utils.RecyclerItemClickListener;
import com.neu.liblife.utils.UserListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 小纸条界面活动
 */
public class PaperDeliverActivity extends AppCompatActivity {
    @BindView(R.id.edittext_chatbox)
    EditText edittextChatbox;
    @BindView(R.id.button_chatbox_send)
    Button buttonChatboxSend;
    @BindView(R.id.chat_back)
    Button chatBack;
    @BindView(R.id.me_head)
    ImageView meHead;
    @BindView(R.id.me_name)
    TextView meName;
    @BindView(R.id.to_user_head)
    ImageView toUserHead;
    @BindView(R.id.to_user_name)
    TextView toUserName;
    @BindView(R.id.chat_layout)
    LinearLayout chatLayout;
    @BindView(R.id.chat_title)
    TextView chatTitle;


    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<PaperDeliver> messageList;
    private List<List<PaperDeliver>> usersMessageLists = new ArrayList<>();

    private RecyclerView userRecycler;
    private RecyclerView.LayoutManager userRecyclerLayoutManager;
    private RecyclerView.Adapter userAdapter;

    private User currentUser = new User();
    private User sendToUser;// = new User();

    private List<User> userLists;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_deliver);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        messageList = new ArrayList<>();
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);


        userLists = new ArrayList<>();
        userRecycler = findViewById(R.id.users_recycler_view);
        //  userRecyclerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        userRecyclerLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        userRecycler.setLayoutManager(userRecyclerLayoutManager);
        userAdapter = new UserListAdapter(this, userLists);
        userRecycler.setAdapter(userAdapter);


        chatBack.setVisibility(View.INVISIBLE);
        chatLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUserList();
        getUserInfo();          //获取用户信息
        // initSendToUser();       //初始化小纸条接收用户信息
        setActionListener();
    }

    private void getUserInfo() {
        if (User.loginUser != null) {
            currentUser = User.loginUser;
        }
        else{
            Intent intent = new Intent(PaperDeliverActivity.this, LoginTestActivity.class);
            startActivity(intent);
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


    private void initUserList() {
        for (int i = 0; i < 20; i++) {
            User user = new User();
            String name = "user" + i;
            String id = "00000" + i;
            ImageView head = new ImageView(this);
            int identifier = getResources().getIdentifier("drawable/" + "user" + i%10, null, getPackageName());
            Log.e("id:  ", String.valueOf(identifier));
            head.setImageResource(identifier);

            user.setId(id);
            user.setUserName(name);
            user.setHead(head);
            user.setHeadId(identifier);

            userLists.add(user);
        }

    }

    //用于测试，构造的一个接收用户
    private void initSendToUser() {
        sendToUser.setId("110110");
        sendToUser.setUserName("taylor");
        sendToUser.setPassword("110110");

    }


    private void setActionListener() {
        buttonChatboxSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendToUser == null)
                {
                    Toast.makeText(PaperDeliverActivity.this, "没有选择传纸条的小伙伴哦~", Toast.LENGTH_SHORT).show();

                }

                else if(edittextChatbox.getText() == null || edittextChatbox.getText().toString().equals("")){
                    Toast.makeText(PaperDeliverActivity.this, "发送消息不能为空！", Toast.LENGTH_SHORT).show();
                }
                else SendPaper();
            }
        });

        userRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, userRecycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                usersMessageLists.add(messageList);
                messageList.clear();
                User click_user = userLists.get(position);
               // Toast.makeText(PaperDeliverActivity.this, click_user.getUserName(), Toast.LENGTH_SHORT).show();
                sendToUser = click_user;
                userRecycler.setVisibility(View.INVISIBLE);
                chatBack.setVisibility(View.VISIBLE);
                chatLayout.setVisibility(View.VISIBLE);
                toUserHead.setImageResource(userLists.get(position).getHeadId());
                toUserName.setText(sendToUser.getUserName());

                meHead.setImageResource(R.drawable.user2);
                meName.setText(currentUser.getUserName());
                chatTitle.setText("正在给 "+sendToUser.getUserName()+" 传小纸条...");

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        chatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatBack.setVisibility(View.INVISIBLE);
                chatLayout.setVisibility(View.INVISIBLE);
                chatTitle.setText("附近座位正在学习的学友\n戳一戳打扰一下ta~");
                userRecycler.setVisibility(View.VISIBLE);

            }
        });

    }


    private void SendPaper() {
        //将当前UI的消息输入打包为Paper
        PaperDeliver paperDeliver = PackagePaper();

        //更新当前用户的小纸条
        //currentUser.setMyStudyMode();
        currentUser.getMyStudyMode().setMyPaperDeliver(paperDeliver);


        //当前用户发送小纸条
        //  currentUser.getMyStudyMode().getMyPaperDeliver().sendPaper(paperDeliver.getPaper(),sendToUser);


        //加入消息列表
        messageList.add(paperDeliver);          //将此条小纸条 加入消息列表
        mMessageAdapter.notifyItemInserted(messageList.size() - 1); // 当有新消息时，刷新ListView中的显示
        mMessageRecycler.scrollToPosition(messageList.size() - 1);

        ReceivePaper();    //自动接收！

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        edittextChatbox.setText("");


    }

    private void ReceivePaper() {
        // PaperDeliver receivePaper = sendToUser.getMyStudyMode().getMyPaperDeliver();
        // currentUser.getMyStudyMode().getMyPaperDeliver().receivePaper(receivePaper.getPaper(),sendToUser);
        PaperDeliver receivePaper = TestReceivePaper();
        messageList.add(receivePaper);
        mMessageAdapter.notifyItemInserted(messageList.size() - 1); // 当有新消息时，刷新ListView中的显示
        mMessageRecycler.scrollToPosition(messageList.size() - 1);


    }

    private PaperDeliver PackagePaper() {
        PaperDeliver paperDeliver = new PaperDeliver();
        PaperDeliver.Paper paper = paperDeliver.new Paper();

        paper.setTextContent(edittextChatbox.getText().toString());   //设置小纸条文本内容
        paper.setImageContent(null);                                  //设置小纸条图片内容
        paper.setSender(currentUser);                                 //设置小纸条sender为当前用户
        paper.setReceiver(sendToUser);                                //设置小纸条receiver为目标用户
        paper.setCreateTime(Calendar.getInstance().getTime());        //设置小纸条创建时间
        paper.setStatus_type(paper.TYPE_PAPER_SENT);
        paperDeliver.setActiveUser(currentUser);
        paperDeliver.setPaper(paper);
        return paperDeliver;
    }

    private PaperDeliver TestReceivePaper() {
        PaperDeliver paperDeliver = new PaperDeliver();
        PaperDeliver.Paper paper = paperDeliver.new Paper();

        paper.setTextContent("你好！");   //设置小纸条文本内容
        paper.setImageContent(null);                                  //设置小纸条图片内容
        paper.setSender(sendToUser);                                 //设置小纸条sender为当前用户
        paper.setReceiver(currentUser);                                //设置小纸条receiver为目标用户
        paper.setCreateTime(Calendar.getInstance().getTime());        //设置小纸条创建时间
        paper.setStatus_type(paper.TYPE_PAPER_RECEIVED);
        paperDeliver.setActiveUser(sendToUser);
        paperDeliver.setPaper(paper);
        return paperDeliver;
    }


}

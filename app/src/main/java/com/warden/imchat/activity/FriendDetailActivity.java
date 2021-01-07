package com.warden.imchat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.warden.imchat.R;
import com.warden.imchat.config.XmppConnection;
import com.warden.imchat.domain.ChatUser;
import com.warden.imchat.utils.DBManager;
import com.warden.imchat.utils.Util;

public class FriendDetailActivity extends BaseActivity {

    private Context context;
    private DBManager dbManager;
    private ImageView back,headIcon;
    private TextView userName,nickName,email;
    private Button deleteFriend;
    private RelativeLayout reNickName;
    private ChatUser chatUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        context = this;
        dbManager = new DBManager(context);
        Intent intent = getIntent();
        chatUser = (ChatUser) intent.getSerializableExtra("friend");
        initView();
        initData();
    }

    private void initData() {
        if (chatUser!=null){
            userName.setText(chatUser.getUserName());
            nickName.setText(chatUser.getNickName());
            email.setText(chatUser.getEmail());
        }
    }

    private void initView() {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        headIcon = findViewById(R.id.head_icon);
        userName = findViewById(R.id.userName_text);
        email = findViewById(R.id.email);
        nickName = findViewById(R.id.nickname_text);
        reNickName = findViewById(R.id.nickname_re);
        reNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNotFastClick()){
                    Intent intent = new Intent(context,ChangeNickNameActivity.class);
                    intent.putExtra("nickname",chatUser.getNickName());
                    intent.putExtra("isMySelf",false);
                    intent.putExtra("changeUser",chatUser);
                    startActivity(intent);
                }

            }
        });
        deleteFriend = findViewById(R.id.delete_btn);
        deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = XmppConnection.getInstance(context).removeUser(context,chatUser.getUserName());
                if (result){
                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                    dbManager.deleteData("user","jid=?", new String[]{chatUser.getJid()});
                    dbManager.deleteData("chatMessage","sendname=? and username=?", new String[]{chatUser.getUserName(),Util.getLoginInfo(context).getUserName()});
                    finish();
                }else {
                    Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        chatUser = dbManager.queryChatUserByName(chatUser.getUserName());
        initData();
    }
}

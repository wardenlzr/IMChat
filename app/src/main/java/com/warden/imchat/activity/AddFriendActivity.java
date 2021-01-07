package com.warden.imchat.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.warden.imchat.R;
import com.warden.imchat.adapter.SearchUserAdapter;
import com.warden.imchat.config.XmppConnection;
import com.warden.imchat.domain.ChatUser;

public class AddFriendActivity extends BaseActivity {
    private ImageView back;
    private EditText addName;
    private SearchUserAdapter adapter;
    private RecyclerView searchRecycler;
    private Context context;
    private TextView searchCommit;
    private List<ChatUser> userInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        context = this;
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        searchRecycler = findViewById(R.id.recycler_search);
        searchCommit = findViewById(R.id.search_commit);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addName = findViewById(R.id.search);
        showResult();
        searchCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = addName.getText().toString();
                if (!TextUtils.isEmpty(inputName)){
                    userInfoList = new ArrayList<>();
                    userInfoList =  XmppConnection.getInstance(context).searchUsers(context,inputName);
                    adapter = new SearchUserAdapter(context,userInfoList);
                    adapter.notifyDataSetChanged();
                    searchRecycler.setAdapter(adapter);
                }
            }
        });
    }

    /**
     * 显示搜索内容
     */
    private void showResult(){
        searchRecycler.setLayoutManager(new LinearLayoutManager(context));
        searchRecycler.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //禁止滑动，解决滑动冲突
        searchRecycler.setNestedScrollingEnabled(false);
    }
}

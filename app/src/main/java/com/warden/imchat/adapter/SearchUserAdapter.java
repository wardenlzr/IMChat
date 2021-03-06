package com.warden.imchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.warden.imchat.R;
import com.warden.imchat.config.XmppConnection;
import com.warden.imchat.domain.ChatUser;

/**
 * @author xiejinbo
 * @date 2019/9/23 0023 17:19
 */
public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    private Context mContext;
    private List<ChatUser> userInfoList;
    private View mItemView;
    public SearchUserAdapter(Context context, List<ChatUser> userInfos) {
        this.mContext = context;
        this.userInfoList = userInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mItemView = LayoutInflater.from(mContext).inflate(R.layout.search_user_adapter,parent,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ChatUser userInfo = userInfoList.get(position);
        holder.userName.setText(userInfo.getUserName());

        holder.sendFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = XmppConnection.getInstance(mContext).addUser(mContext,userInfo.getJid(),userInfo.getUserName());
                if (result){
                    holder.sendFollow.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.follow));
                    Toast.makeText(mContext,R.string.addToastSuccess,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext,R.string.addToastFail,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        ImageView sendFollow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            sendFollow = itemView.findViewById(R.id.send_follow);
        }
    }
}

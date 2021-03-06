package com.warden.imchat.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.warden.imchat.R;
import com.warden.imchat.config.XmppConnection;
import com.warden.imchat.domain.LoginUser;
import com.warden.imchat.utils.Util;

public class ChangePasswordActivity extends BaseActivity {
    private Context context;
    private ImageView back;
    private EditText oldPassword,newPassword,confirmPassword;
    private Button summit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context = this;
        back = findViewById(R.id.back);
        oldPassword = findViewById(R.id.old_pwd);
        newPassword = findViewById(R.id.new_pwd);
        confirmPassword = findViewById(R.id.new_pwd_again);
        summit = findViewById(R.id.submit_password);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPasswordStr = oldPassword.getText().toString();
                String newPasswordStr = newPassword.getText().toString();
                String confirmPasswordStr = confirmPassword.getText().toString();
                if (TextUtils.isEmpty(oldPasswordStr)||TextUtils.isEmpty(newPasswordStr)||TextUtils.isEmpty(confirmPasswordStr)){
                    Toast.makeText(context,R.string.input_password_dialog,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Util.checkOldPassword(context,oldPasswordStr)){
                    Toast.makeText(context,R.string.old_password_dialog,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Util.checkNewPassword(context,newPasswordStr,confirmPasswordStr)){
                    Toast.makeText(context,R.string.new_password_dialog,Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean result = XmppConnection.getInstance(context).changePassword(context,newPasswordStr);
                if (result){
                    LoginUser loginUser = new LoginUser();
                    loginUser.setUserName(Util.getLoginInfo(context).getUserName());
                    loginUser.setPassword(newPasswordStr);
                    Util.saveLoginStatic(context,loginUser);
                    onBackPressed();
                    finish();
                }else {
                    Toast.makeText(context,R.string.summit_password_dialog,Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}

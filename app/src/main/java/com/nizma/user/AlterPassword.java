package com.nizma.user;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nizma.bean.AppError;
import com.nizma.bean.AppSuccess;
import com.nizma.utils.RequestNet;
import com.nizma.www.R;

import java.util.Iterator;
import java.util.Map;

public class AlterPassword extends AppCompatActivity {
    private static final int ALTER_PASSWORD_SUCCESS = 0;
    private static final int ALTER_PASSWORD_ERROR = 50;
    private TextView oldpass;
    private TextView newpass;
    private TextView okpass;
    private Button checkbutton;

    private AppError apperror;
    private AppSuccess appsuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("修改密码");

        oldpass = (TextView) findViewById(R.id.oldpass);
        newpass = (TextView) findViewById(R.id.newpass);
        okpass = (TextView) findViewById(R.id.okpass);
        checkbutton = (Button) findViewById(R.id.checkbutton);

        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String oldpassword = oldpass.getText().toString();
                final String newpassword = newpass.getText().toString();
                String okpassword = okpass.getText().toString();
                if(newpassword.equals(okpassword)){
                    if(newpassword.length() > 6){
                        new Thread(){
                            @Override
                            public void run() {
                                Message msg = new Message();
                                Map<String,Object> result = RequestNet.AlterPassword(oldpassword, newpassword);
                                Iterator keys = result.keySet().iterator();
                                String key = (String) keys.next();
                                if(key.equals("error")){
                                    apperror = (AppError) result.get("error");
                                    msg.what = ALTER_PASSWORD_ERROR;
                                    mHandler.sendMessage(msg);
                                }else{
                                    appsuccess = (AppSuccess) result.get("success");
                                    msg.what = ALTER_PASSWORD_SUCCESS;
                                    mHandler.sendMessage(msg);
                                }

                            }
                        }.start();
                    }else{
                        Toast.makeText(AlterPassword.this, "密码长度应为6到32个字符", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AlterPassword.this, "新密码两次输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ALTER_PASSWORD_ERROR:
                    Toast.makeText(AlterPassword.this, apperror.getError(), Toast.LENGTH_SHORT).show();
                    break;
                case ALTER_PASSWORD_SUCCESS:
                    Toast.makeText(AlterPassword.this, appsuccess.getSuccess(), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };
}

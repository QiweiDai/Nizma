package com.nizma.www;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.nizma.utils.ParseXmlService;
import com.nizma.utils.RequestNet;
import com.nizma.utils.UpdateManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class AboutActivity extends AppCompatActivity {
    public static final int UPDATE = 1;
    private LinearLayout aboutnzm;
    private LinearLayout update;
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutnzm = (LinearLayout) findViewById(R.id.aboutnzm);
        update = (LinearLayout) findViewById(R.id.update);
        //菜单栏
        setupActionBar();

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.aboutnzm:
                        break;
                    case R.id.update:
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Message msg = new Message();
                                    InputStream inStream = RequestNet.update();
                                    // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
                                    ParseXmlService service = new ParseXmlService();
                                    mHashMap = service.parseXml(inStream);
                                    msg.what = UPDATE;
                                    mHandler.sendMessage(msg);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        break;
                }

            }
        };
        aboutnzm.setOnClickListener(click);
        update.setOnClickListener(click);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    UpdateManager manager = new UpdateManager(AboutActivity.this, mHashMap);
                    // 检查软件更新
                    manager.checkUpdate();
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("关于你在吗？");
        }
    }

}

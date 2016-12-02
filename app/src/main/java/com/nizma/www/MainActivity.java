package com.nizma.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nizma.bean.UserInfo;
import com.nizma.query.ClassQuery;
import com.nizma.query.DormitoryQuery;
import com.nizma.sign.IndexFragment;
import com.nizma.sign.StudentFragment;
import com.nizma.sign.DormitoryFragment;
import com.nizma.user.AlterPassword;
import com.nizma.utils.RequestNet;

import static com.nizma.www.R.drawable.topleft_text_up_shape;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int APP_CANCELLATION = 1;
    private int TOPBUTTON_DOWN = 1;
    private int TOPBUTTON_UP = 2;
    private int TOPLEFT_BUTTO_STATE = TOPBUTTON_UP;
    private int TOPRIGHT_BUTTO_STATE = TOPBUTTON_DOWN;
    private FragmentTransaction transaction;
    private IndexFragment indexf;
    private StudentFragment sf;
    private DormitoryFragment df;


    private TextView topright_text;
    private TextView topleft_text;
    private Button index;
    private TextView leftposition;
    private TextView leftname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        topright_text = (TextView) findViewById(R.id.topright_text);
        topleft_text = (TextView) findViewById(R.id.topleft_text);
        index = (Button) findViewById(R.id.index);
        transaction = getSupportFragmentManager().beginTransaction();
        leftposition = (TextView) headerView.findViewById(R.id.leftposition);
        leftname = (TextView) headerView.findViewById(R.id.leftname);

        if (UserInfo.StudentUser != null) {
            switch (UserInfo.StudentUser.getCadreId()){
                case 0:
                    leftposition.setText("普通学生");
                    break;
                case 1:
                    leftposition.setText("班长");
                    break;
                case 2:
                    leftposition.setText("副班长");
                    break;
            }
            leftname.setText(UserInfo.StudentUser.getName());
            // leftposition.setText();
        } else {
            leftposition.setText(UserInfo.TeacherUser.getPermissionTypeName());
            leftname.setText(UserInfo.TeacherUser.getName());
        }
        //直接加载学生点到界面
        // openStudentFragment();
        openIndexFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        /*
        * 侧滑菜单点击
        * */


        View.OnClickListener text_onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = resources = getApplicationContext().getResources();
                int color = 0;
                Drawable btnDrawable = null;
                switch (v.getId()) {
                    case R.id.topleft_text:
                        if (UserInfo.StudentUser != null) {
                            if (TOPLEFT_BUTTO_STATE != TOPBUTTON_DOWN) {
                                Log.e("TOPLEFT_BUTTO_STATE", TOPLEFT_BUTTO_STATE + "");
                                if (TOPLEFT_BUTTO_STATE == TOPBUTTON_UP) {
                                    TOPLEFT_BUTTO_STATE = TOPBUTTON_UP;
                                    btnDrawable = resources.getDrawable(R.drawable.topright_text_up_shape);
                                    color = resources.getColor(R.color.white);
                                    topright_text.setTextColor(color);
                                    topright_text.setBackgroundDrawable(btnDrawable);

                                    color = resources.getColor(R.color.yuancolor);
                                    btnDrawable = resources.getDrawable(R.drawable.topleft_text_down_shape);
                                    topleft_text.setTextColor(color);
                                    topleft_text.setBackgroundDrawable(btnDrawable);
                                } else {
                                    TOPLEFT_BUTTO_STATE = TOPBUTTON_DOWN;
                                    color = resources.getColor(R.color.white);
                                    btnDrawable = resources.getDrawable(R.drawable.topleft_text_up_shape);
                                    topleft_text.setTextColor(color);
                                    topleft_text.setBackgroundDrawable(btnDrawable);

                                    color = resources.getColor(R.color.yuancolor);
                                    btnDrawable = resources.getDrawable(R.drawable.topright_text_down_shape);
                                    topright_text.setTextColor(color);
                                    topright_text.setBackgroundDrawable(btnDrawable);

                                }
                            }
                            openStudentFragment();
                        } else {
                            Toast.makeText(getApplication(), "老师不能点到，不好意思！！", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.topright_text:
                        if (UserInfo.StudentUser != null) {
                            if (TOPLEFT_BUTTO_STATE != TOPBUTTON_DOWN) {
                                Log.e("TOPLEFT_BUTTO_STATE", TOPLEFT_BUTTO_STATE + "");
                                if (TOPRIGHT_BUTTO_STATE == TOPBUTTON_DOWN) {
                                    color = resources.getColor(R.color.yuancolor);
                                    btnDrawable = resources.getDrawable(R.drawable.topright_text_down_shape);
                                    topright_text.setTextColor(color);
                                    topright_text.setBackgroundDrawable(btnDrawable);

                                    color = resources.getColor(R.color.white);
                                    btnDrawable = resources.getDrawable(R.drawable.topleft_text_up_shape);
                                    topleft_text.setTextColor(color);
                                    topleft_text.setBackgroundDrawable(btnDrawable);

                                } else {
                                    color = resources.getColor(R.color.white);
                                    btnDrawable = resources.getDrawable(R.drawable.topright_text_up_shape);
                                    topright_text.setTextColor(color);
                                    topright_text.setBackgroundDrawable(btnDrawable);

                                    color = resources.getColor(R.color.yuancolor);
                                    btnDrawable = resources.getDrawable(R.drawable.topleft_text_down_shape);
                                    topleft_text.setTextColor(color);
                                    topleft_text.setBackgroundDrawable(btnDrawable);
                                }
                            }
                            openDormitoryFragemnt();
                        } else {
                            Toast.makeText(getApplication(), "老师不能点到，不好意思！！", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.index:
                        color = resources.getColor(R.color.white);
                        btnDrawable = resources.getDrawable(R.drawable.topright_text_up_shape);
                        topright_text.setTextColor(color);
                        topright_text.setBackgroundDrawable(btnDrawable);

                        btnDrawable = resources.getDrawable(R.drawable.topleft_text_up_shape);
                        topleft_text.setTextColor(color);
                        topleft_text.setBackgroundDrawable(btnDrawable);

                        openIndexFragment();
                        break;
                }
            }
        };
        topright_text.setOnClickListener(text_onclick);
        topleft_text.setOnClickListener(text_onclick);
        index.setOnClickListener(text_onclick);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (UserInfo.StudentUser != null) {
            if (id == R.id.studentclass) {
                openStudentFragment();
            } else {
                openDormitoryFragemnt();
            }
        } else {
            Toast.makeText(getApplication(), "老师不能点到，不好意思！！", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //
    public void openIndexFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (indexf == null) {
            indexf = new IndexFragment();
        }
        // transaction.add(R.id.main_frame_layout, sf);
        transaction.replace(R.id.main_frame_layout, indexf);
        transaction.show(indexf);
        transaction.commit();
    }

    public void openStudentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (sf == null) {
            sf = new StudentFragment();
        }
        // transaction.add(R.id.main_frame_layout, sf);
        transaction.replace(R.id.main_frame_layout, sf);
        transaction.show(sf);
        transaction.commit();
    }

    public void openDormitoryFragemnt() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (df == null) {
            df = new DormitoryFragment();
        }
        //transaction.add(R.id.main_frame_layout, df);
        transaction.replace(R.id.main_frame_layout, df);
        transaction.show(df);
        transaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(getApplication(), "程序猿正在日夜为您建设模板，请耐心等待！！", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_gallery) {
            if (UserInfo.TeacherUser != null) {
                Intent intent = new Intent(this, ClassQuery.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "你有不是老师，你查啥数据", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_slideshow) {
            if (UserInfo.TeacherUser != null) {
                Intent intent = new Intent(this, DormitoryQuery.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "你有不是老师，你查啥数据", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, AlterPassword.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
//            //实例化SharedPreferences对象（第一步）
//            SharedPreferences mySharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
//            //实例化SharedPreferences.Editor对象（第二步）
//            SharedPreferences.Editor editor = mySharedPreferences.edit();
//            //用putString的方法保存数据
//            editor.putString("studentid", "");
//            editor.putString("password", "");
//            //提交当前数据
//            editor.commit();
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_cancellation) {

            new Thread() {
                @Override
                public void run() {
                    int i = RequestNet.appCancellation();
                    if (i == 1) {
                        Message msg = new Message();
                        msg.what = APP_CANCELLATION;
                        mHandler.sendMessage(msg);
                    }
                }
            }.start();

        }
        //else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APP_CANCELLATION:
                    //            //实例化SharedPreferences对象（第一步）
                    SharedPreferences mySharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
                    //实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //用putString的方法保存数据
                    editor.putString("studentid", "");
                    editor.putString("password", "");
                    //提交当前数据
                    editor.commit();
                    Intent intent = new Intent("com.nizma.www.LoginActivity");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}

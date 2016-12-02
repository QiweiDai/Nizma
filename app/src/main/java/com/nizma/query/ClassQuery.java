package com.nizma.query;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nizma.bean.AppError;
import com.nizma.bean.AppSuccess;
import com.nizma.bean.HzClasses;
import com.nizma.bean.HzStudent;
import com.nizma.bean.UserInfo;
import com.nizma.utils.CustomDialog;
import com.nizma.utils.HzClassAdapter;
import com.nizma.utils.RequestNet;
import com.nizma.www.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClassQuery extends AppCompatActivity {


    private static final int CLASS_QUERY_ERROR = 30;
    private static final int CLASS_QUERY_SUCCESS = 1;
    private static final int STUDENT_QUERYSTARE_SUCCESS = 2;
    private static final int STUDENT_QUERYSTARE_LIST = 3;
    private static final int CHECK_STUDENT_INFO = 4;
    private ArrayList<HzClasses> class_data;
    private TextView class_query_class;
    private TextView classname;
    private LinearLayout class_query_linearlayout;
    private HzClassAdapter classadapter;


    private LinearLayout layout;
    private ListView listViews;
    private PopupWindow popupWindow;

    private ListView class_query_list;
    private List<HzStudent> student_query_data;


    private AppError apperror;
    private AppSuccess appsuccess;

    private EditText class_query_years;
    private EditText class_query_month;
    private EditText class_query_day;
    private EditText class_query_student;

    private Button class_query_button;

    private String classId = null;

    private MyQueryadapter myadapter;

    private String newtime = null;
    private List<HzStudent> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_query);
        //菜单栏
        setupActionBar();

        Calendar now = Calendar.getInstance();
        String years = now.get(Calendar.YEAR) + "";
        String month = (now.get(Calendar.MONTH) + 1) + "";
        String day = (now.get(Calendar.DAY_OF_MONTH)) + "";
        class_query_years = (EditText) findViewById(R.id.class_query_years);
        class_query_month = (EditText) findViewById(R.id.class_query_month);
        class_query_day = (EditText) findViewById(R.id.class_query_day);
        class_query_student = (EditText) findViewById(R.id.class_query_student);
        class_query_button = (Button) findViewById(R.id.class_query_button);
        classname = (TextView) findViewById(R.id.classname);
        class_query_years.setText(years);
        class_query_month.setText(month);
        class_query_day.setText(day);

        class_query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtime = class_query_years.getText().toString() + "-" + class_query_month.getText().toString() + "-" + class_query_day.getText().toString();
                if (isValidDate(newtime)) {
                    new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            String studentName = class_query_student.getText().toString();
                            if (studentName == null) {
                                studentName = "";
                            }
                            Log.e("classId", classId + "");
                            Log.e("newtime", newtime + "");
                            Log.e("studentName", studentName + "");
                            Map<String, Object> map = RequestNet.ClassCheckStudentState(classId, newtime, studentName);
                            Iterator keys = map.keySet().iterator();
                            String key = (String) keys.next();
                            if (key.equals("error")) {
                                apperror = (AppError) map.get("error");
                                msg.what = CLASS_QUERY_ERROR;
                                mHandler.sendMessage(msg);
                            } else if (key.equals("success")) {
                                appsuccess = (AppSuccess) map.get("success");
                                msg.what = STUDENT_QUERYSTARE_SUCCESS;
                                mHandler.sendMessage(msg);
                            } else {
                                student_query_data = new ArrayList<HzStudent>();
                                Map<Integer, List<HzStudent>> studentState = (Map<Integer, List<HzStudent>>) map.get("studentState");
                                if (studentState.get(1) != null)
                                    student_query_data.addAll(studentState.get(1));
                                if (studentState.get(2) != null)
                                    student_query_data.addAll(studentState.get(2));
                                if (studentState.get(3) != null)
                                    student_query_data.addAll(studentState.get(3));
                                msg.what = STUDENT_QUERYSTARE_LIST;
                                mHandler.sendMessage(msg);
                            }
                        }
                    }.start();
                } else {
                    Toast.makeText(ClassQuery.this, "日期格式有误，请检查！！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                apperror = new AppError();
                Map<String, Object> map = RequestNet.TeacherQueryClass();
                Iterator keys = map.keySet().iterator();
                String key = (String) keys.next();
                if (key.equals("error")) {
                    apperror = (AppError) map.get("error");
                    msg.what = CLASS_QUERY_ERROR;
                    mHandler.sendMessage(msg);
                } else {
                    class_data = (ArrayList<HzClasses>) map.get("classlist");
                    if (class_data != null && class_data.size() > 0) {
                        msg.what = CLASS_QUERY_SUCCESS;
                        mHandler.sendMessage(msg);
                    }
                }
            }
        }.start();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("上课点到查询");
        }
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLASS_QUERY_ERROR:
                    Toast.makeText(getApplication(), apperror.getError(), Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case CLASS_QUERY_SUCCESS:
                    class_query_class = (TextView) findViewById(R.id.class_query_class);
                    class_query_linearlayout = (LinearLayout) findViewById(R.id.class_query_linearlayout);
                    classId = class_data.get(0).getId().toString();
                    classadapter = new HzClassAdapter(getApplication(), class_data);
                    class_query_class.setText((CharSequence) classadapter.getItem(0));
                    classname.setText((CharSequence) classadapter.getItem(0) + "-未准勤学生");
                    class_query_linearlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showWindow(class_query_linearlayout, class_query_class, classadapter, class_query_linearlayout, class_data);
                        }
                    });
                    break;
                case STUDENT_QUERYSTARE_SUCCESS:
                    Toast.makeText(getApplication(), appsuccess.getSuccess(), Toast.LENGTH_LONG).show();
                    if (student_query_data != null)
                        student_query_data.clear();
                    if (myadapter == null) {
                        myadapter = new MyQueryadapter(getApplication(), student_query_data);
                    }
                    myadapter.notifyDataSetInvalidated();
                    break;
                case STUDENT_QUERYSTARE_LIST:
                    class_query_list = (ListView) findViewById(R.id.class_query_list);
                    myadapter = new MyQueryadapter(getApplication(), student_query_data);
                    class_query_list.setAdapter(myadapter);
                    class_query_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            new Thread() {
                                @Override
                                public void run() {
                                    Message msg = new Message();
                                    students = RequestNet.checkStudentInfo(newtime, student_query_data.get(position).getSno(), student_query_data.get(position).getCheckinsState().toString(), student_query_data.get(position).getClassid().toString());
                                    msg.what = CHECK_STUDENT_INFO;
                                    mHandler.sendMessage(msg);
                                }
                            }.start();
                        }
                    });
                    break;
                case CHECK_STUDENT_INFO:
                    CustomDialog.Builder builder = new CustomDialog.Builder(ClassQuery.this);
                    if (students.get(0).getCheckinsState() == 1) {
                        builder.setTitle("当日迟到详情 ----" + students.get(0).getName());
                        builder.setColor(0xFFFFFAE6);
                    } else if (students.get(0).getCheckinsState() == 2) {
                        builder.setTitle("当日旷课详情 ----" + students.get(0).getName());
                        builder.setColor(0xFFFFECF1);
                    } else if (students.get(0).getCheckinsState() == 3) {
                        builder.setTitle("当日请假详情 ----" + students.get(0).getName());
                        builder.setColor(0xFFF2FBFF);
                    }

                    builder.setStudentList(students);
                    builder.create().show();
                    break;
            }
        }
    };

    //下拉列表框框点击展开
    public void showWindow(View position, final TextView txt, HzClassAdapter adapter, LinearLayout spinnerlayout, final ArrayList<HzClasses> list2) {
        layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.myspinner_dropdown, null);
        listViews = (ListView) layout.findViewById(R.id.spinner_listView);
        listViews.setAdapter(adapter);
        popupWindow = new PopupWindow(position);
        // 设置弹框的宽度为布局文件的宽
        popupWindow.setWidth(spinnerlayout.getWidth());
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击弹框外部，弹框消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
        popupWindow.showAsDropDown(position, 0, 0);
        // listView的item点击事件
        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                txt.setText(list2.get(arg2).getClassName());// 设置所选的item作为下拉框的标题
                classId = list2.get(arg2).getId().toString();
                classname.setText(list2.get(arg2).getClassName() + "-未准勤学生");
                // 弹框消失
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
    }


    class MyQueryadapter extends BaseAdapter {
        private LinearLayout mInflater;
        List<HzStudent> mData;
        Context context;

        public MyQueryadapter(Context context, List<HzStudent> mData) {
            super();
            this.context = context;
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(context, R.layout.item_content2, null);
            }
            TextView studentname = (TextView) convertView.findViewById(R.id.studentname);
            TextView studentid = (TextView) convertView.findViewById(R.id.studentid);
            LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.item_content);
            ImageView stateimage = (ImageView) convertView.findViewById(R.id.show_state_image);
            TextView textimage = (TextView) convertView.findViewById(R.id.show_state_text);
            TextView show_state_sum = (TextView) convertView.findViewById(R.id.show_state_sum);
            TextView Authority = (TextView) convertView.findViewById(R.id.Authority);
            studentname.setText(mData.get(position).getName());
            studentid.setText("学号：" + mData.get(position).getSno());
            if (mData.size() > position) {
                if (mData.get(position).getCadreId() == 1) {
                    Authority.setText(R.string.banzhang);
                    Authority.setTextColor(0xffffffff);
                    Authority.setBackgroundResource(R.drawable.position_title);
                } else {
                    Authority.setText("");
                    Authority.setTextColor(0xffffffff);
                    Authority.setBackgroundResource(0);
                }
                if (mData.get(position).getCheckinsState() == 3) {
                    stateimage.setImageResource(R.drawable.leave);
                    textimage.setText("请假");
                    show_state_sum.setText("x" + mData.get(position).getCount());
                    textimage.setTextColor(0xFF00BAFF);
                    ll.setBackgroundColor(0xFFF2FBFF);
                    show_state_sum.setTextColor(0xFF00BAFF);
                } else if (mData.get(position).getCheckinsState() == 1) {
                    stateimage.setImageResource(R.drawable.late);
                    textimage.setText("迟到");
                    textimage.setTextColor(0xFFFFCC00);
                    ll.setBackgroundColor(0xFFFFFAE6);
                    show_state_sum.setTextColor(0xFFFFCC00);
                    show_state_sum.setText("x" + mData.get(position).getCount());
                } else if (mData.get(position).getCheckinsState() == 2) {
                    stateimage.setImageResource(R.drawable.truancy);
                    textimage.setText("旷课");
                    textimage.setTextColor(0xFFFF3266);
                    ll.setBackgroundColor(0xFFFFECF1);
                    show_state_sum.setText("x" + mData.get(position).getCount());
                    show_state_sum.setTextColor(0xFFFF3266);
                }
            }
            return convertView;
        }
    }
}

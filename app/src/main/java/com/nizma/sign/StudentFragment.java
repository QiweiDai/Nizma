package com.nizma.sign;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.nizma.bean.AppError;
import com.nizma.bean.AppSuccess;
import com.nizma.bean.HzClassCourse;
import com.nizma.bean.HzSemester;
import com.nizma.bean.HzStudent;
import com.nizma.bean.HzTeacher;
import com.nizma.bean.UserInfo;
import com.nizma.swipelayout.SwipeLayoutAdapter;
import com.nizma.swipelayout.SwipeOnTouchListener;
import com.nizma.swipelayout.SwipeViewHolder;
import com.nizma.utils.HzClassCourseAdapter;
import com.nizma.utils.HzTeacherAdapter;
import com.nizma.utils.RequestNet;
import com.nizma.www.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by WZW on 2016/9/23.
 */
public class StudentFragment extends Fragment {

    private static final int NOT_CHECK_TIME = 3;
    private static final int COURSE_LIST_SHOW = 10;
    private static final int CLASS_CHECK_ERROR = 11;
    private static final int CLASS_CHECK_SUCCESS = 1;
    private SwipeLayoutAdapter mAdapter;
    private ListView mListView;
    private ArrayList<HzStudent> studentlist;

    /*
    *
    * 课程下拉列表框
    * */
    private ArrayList<HzClassCourse> courselist;
    private TextView courseText;
    private LinearLayout spinnercourse;
    private HzClassCourseAdapter courseadapter;
    private String judgeStr;
    /*
    *老师下拉列表框
     */
    private ArrayList<HzTeacher> teacherlist;
    private TextView teacherText;
    private LinearLayout spinnerteacher;
    private HzTeacherAdapter teacheradapter;


    private LinearLayout layout;
    private ListView listViews;
    private PopupWindow popupWindow;
    Map<String, Object> map;
    private View view;
    //    private CustomProgressDialog dialog;
    private Button class_check_button;
    private TextView class_zhui;
    private TextView class_jia;
    private TextView class_chi;
    private TextView class_kuang;
    private TextView class_check_title;
    private TextView class_name;
    private Integer zhui = 0;
    private Integer jia = 0;
    private Integer chi = 0;
    private Integer kuang = 0;

    private CheckBox class_hide_zhui;
    private Boolean checkbox_state = false;

    private Toast toast;
    String son = null;
    String courseId = null;
    String section = null;
    String teacherId = null;
    String students = "";
    String classId = null;
    AppError checkerror = null;
    AppSuccess checksuccess = null;
    HzSemester semester = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_student, container, false);
        toast = new Toast(getContext());
        class_hide_zhui = (CheckBox) view.findViewById(R.id.class_hide_zhui);
        class_zhui = (TextView) view.findViewById(R.id.class_zhui);
        class_jia = (TextView) view.findViewById(R.id.class_jia);
        class_chi = (TextView) view.findViewById(R.id.class_chi);
        class_kuang = (TextView) view.findViewById(R.id.class_kuang);
        class_name = (TextView) view.findViewById(R.id.class_name);
        class_check_button = (Button) view.findViewById(R.id.class_check_button);
//        Gson gg = new Gson();
//        Log.e("student_user",gg.toJson(UserInfo.StudentUser));
//            dialog = new CustomProgressDialog(getActivity(), "正在加载中", R.animator.loading);
//            dialog.show();
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                map = RequestNet.loginClientPost();
                if (map != null && map.size() > 0) {
                    Iterator keys = map.keySet().iterator();
                    String key = (String) keys.next();
                    if (key.equals("error")) {
                        checkerror = (AppError) map.get("error");
                        msg.what = NOT_CHECK_TIME;
                        mHandler.sendMessage(msg);
                    } else {
                        semester = (HzSemester) map.get("semester");
                        courselist = (ArrayList<HzClassCourse>) map.get("courselist");
                        teacherlist = (ArrayList<HzTeacher>) map.get("hzTeacherList");
                        studentlist = (ArrayList<HzStudent>) map.get("hzStudentList");
                        judgeStr = (String) map.get("judgeStr");
                        section = judgeStr + "-" + judgeStr;
                        teacherId = teacherlist.get(0).getTeacherId();
                        courseId = courselist.get(0).getId().toString();
                        msg.what = COURSE_LIST_SHOW;
                        mHandler.sendMessage(msg);
                    }
                }
            }
        }.start();
        class_hide_zhui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (studentlist != null && studentlist.size() > 0) {
                    if (isChecked) {
                        ArrayList<HzStudent> studentlist2 = new ArrayList<HzStudent>();
                        for (int i = 0; i < studentlist.size(); i++) {
                            if (studentlist.get(i).getCheckinsState() != 0) {
                                studentlist2.add(studentlist.get(i));
                            }
                        }
                        mListView.setAdapter(null);
                        mAdapter = new MySwipeAdapater(getActivity(), R.layout.item_content, R.layout.item_action, studentlist2);
                        mListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetInvalidated();
                    } else {
                        mListView.setAdapter(null);
                        mAdapter = new MySwipeAdapater(getActivity(), R.layout.item_content, R.layout.item_action, studentlist);
                        mListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetInvalidated();
                    }
                }
            }
        });
        /*
        班级签到功能参数，
        son         //班长id
        courseId    //课程id
        section     //课程节次
        teacherId   //老师id
        students    //学生出勤状态，学生id-状态，出勤:0;迟到:1;请假:3;旷课:2
        classId     //班级id
        */
        class_check_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentlist != null) {
                    students = "";
                    son = UserInfo.StudentUser.getSno();//班长id
                    classId = UserInfo.StudentUser.getClassid().toString();
                    for (int i = 0; i < studentlist.size(); i++) {
                        if (studentlist.get(i).getCheckinsState() == 1) {
                            students += (studentlist.get(i).getSno() + "-" + studentlist.get(i).getCheckinsState() + ",");
                        } else if (studentlist.get(i).getCheckinsState() == 2) {
                            students += (studentlist.get(i).getSno() + "-" + studentlist.get(i).getCheckinsState() + ",");
                        } else if (studentlist.get(i).getCheckinsState() == 3) {
                            students += (studentlist.get(i).getSno() + "-" + studentlist.get(i).getCheckinsState() + ",");
                        }
                    }
//                    Log.e("班长id", son);
//                    Log.e("课程id", courseId);
//                    Log.e("课程节次", section);
//                    Log.e("老师id", teacherId);
//                    Log.e("学生出勤状态", students);
//                    Log.e("班级id", classId);
                    new Thread() {
                        @Override
                        public void run() {
                            checkerror = new AppError();
                            checksuccess = new AppSuccess();
                            Message msg = new Message();
                            Map<String, Object> checkLog = RequestNet.ClassCheck(son, courseId, section, teacherId, students, classId);
                            if (checkLog != null && checkLog.size() > 0) {
                                Iterator keys = checkLog.keySet().iterator();
                                String key = (String) keys.next();
                                Log.e("key", key);
                                if (key.equals("error")) {
                                    checkerror = (AppError) checkLog.get("error");
                                    msg.what = CLASS_CHECK_ERROR;
                                    mHandler.sendMessage(msg);
                                } else {
                                    checksuccess = (AppSuccess) checkLog.get("success");
                                    msg.what = CLASS_CHECK_SUCCESS;
                                    mHandler.sendMessage(msg);
                                }
                            }
                        }
                    }.start();
                } else {
                    toast.cancel();
                    toast.makeText(getActivity(), checkerror.getError(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOT_CHECK_TIME:
                    toast.cancel();
                    toast.makeText(getActivity(), checkerror.getError(), Toast.LENGTH_LONG).show();
                    //dialog.hide();
                    break;
                case COURSE_LIST_SHOW:
                    Log.e("judgeStr", judgeStr);
                    class_name.setText(UserInfo.StudentUser.getHzClasses().getClassName() + "-准勤");
                    courseText = (TextView) view.findViewById(R.id.courseText);
                    courseadapter = new HzClassCourseAdapter(getActivity(), courselist);
                    courseText.setText((CharSequence) courseadapter.getItem(0));
                    spinnercourse = (LinearLayout) view.findViewById(R.id.course);

                    class_check_title = (TextView) view.findViewById(R.id.class_check_title);
                    class_check_title.setText(semester.getName() + "第" + semester.getCurrentWeek().substring(0, semester.getCurrentWeek().lastIndexOf(".0")) + "周 " + "第" + judgeStr + "节课");
                    // 点击右侧按钮，弹出下拉框
                    spinnercourse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showWindow(spinnercourse, courseText, courseadapter, spinnercourse, courselist);
                        }
                    });

                    teacherText = (TextView) view.findViewById(R.id.teacherText);
                    teacheradapter = new HzTeacherAdapter(getActivity(), teacherlist);
                    teacherText.setText((CharSequence) teacheradapter.getItem(0));
                    spinnerteacher = (LinearLayout) view.findViewById(R.id.teacher);
                    spinnerteacher.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            teachershowWindow(spinnerteacher, teacherText, teacheradapter, spinnerteacher, teacherlist);
                        }
                    });
                    mListView = (ListView) view.findViewById(R.id.listView);
                    mAdapter = new MySwipeAdapater(getActivity(), R.layout.item_content, R.layout.item_action, studentlist);
                    mListView.setAdapter(mAdapter);
                    zhui = studentlist.size();
                    jia = 0;
                    chi = 0;
                    kuang = 0;
                    class_zhui.setText(zhui.toString());
                    class_jia.setText(jia.toString());
                    class_chi.setText(chi.toString());
                    class_kuang.setText(kuang.toString());
                    //dialog.hide();
                    break;
                case CLASS_CHECK_ERROR:
                    toast.cancel();
                    toast.makeText(getActivity(), checkerror.getError(), Toast.LENGTH_LONG).show();
                    break;
                case CLASS_CHECK_SUCCESS:
                    toast.cancel();
                    toast.makeText(getActivity(), checksuccess.getSuccess(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    //下拉列表框框点击展开
    public void teachershowWindow(View position, final TextView txt, HzTeacherAdapter adapter, LinearLayout spinnerlayout, final ArrayList<HzTeacher> list2) {

        layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.myspinner_dropdown, null);
        listViews = (ListView) layout.findViewById(R.id.spinner_listView);
        listViews.setAdapter(adapter);
        popupWindow = new PopupWindow(position);
        // 设置弹框的宽度为布局文件的宽
        popupWindow.setWidth(spinnerlayout.getWidth());
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击弹框外部，弹框消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
        popupWindow.showAsDropDown(position, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                // spinnerlayout.setBackgroundColor(0xFFFFEC6d);
            }

        });
        // listView的item点击事件
        listViews.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                HzTeacher hccc = (HzTeacher) list2.get(arg2);
                txt.setText(hccc.getName());// 设置所选的item作为下拉框的标题
                teacherId = hccc.getTeacherId();
                // 弹框消失
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

    }

    //下拉列表框框点击展开
    public void showWindow(View position, final TextView txt, HzClassCourseAdapter adapter, LinearLayout spinnerlayout, final ArrayList<HzClassCourse> list2) {

        layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.myspinner_dropdown, null);
        listViews = (ListView) layout.findViewById(R.id.spinner_listView);
        listViews.setAdapter(adapter);
        popupWindow = new PopupWindow(position);
        // 设置弹框的宽度为布局文件的宽
        popupWindow.setWidth(spinnerlayout.getWidth());
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击弹框外部，弹框消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
        popupWindow.showAsDropDown(position, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                // spinnerlayout.setBackgroundColor(0xFFFFEC6d);
            }

        });
        // listView的item点击事件
        listViews.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                HzClassCourse hccc = (HzClassCourse) list2.get(arg2);
                txt.setText(hccc.getCourse());// 设置所选的item作为下拉框的标题
                courseId = hccc.getId().toString();
                // 弹框消失
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

    }

    //适配器
    class MySwipeAdapater extends SwipeLayoutAdapter<Object> {
        private List<HzStudent> _data;
        LinearLayout item_content;
        ImageView show_state_image;
        TextView show_state_text;
        private HorizontalScrollView _currentActiveHSV = null;

        public MySwipeAdapater(Activity context, int contentViewResourceId, int actionViewResourceId, ArrayList<HzStudent> objects) {
            super(context, contentViewResourceId, actionViewResourceId, objects);
            this._data = objects;
        }

        //实现setContentView方法
        @Override
        public void setContentView(View contentView, int position, HorizontalScrollView parent) {

            TextView studentname = (TextView) contentView.findViewById(R.id.studentname);
            TextView authority = (TextView) contentView.findViewById(R.id.Authority);
            TextView studenid = (TextView) contentView.findViewById(R.id.studentid);
            item_content = (LinearLayout) contentView.findViewById(R.id.item_content);
            show_state_image = (ImageView) contentView.findViewById(R.id.show_state_image);
            show_state_text = (TextView) contentView.findViewById(R.id.show_state_text);

            studentname.setText(_data.get(position).getName());
            studenid.setText("学号：" + _data.get(position).getSno());
            if (_data.get(position).getClassCadre().equals("班长")) {
                authority.setText(R.string.banzhang);
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(R.drawable.position_title);
            } else {
                authority.setText("");
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(0);
            }

            //旷课2 迟到1 请假3
            if (_data.get(position).getCheckinsState() == 1) {//迟到
                item_content.setBackgroundColor(0xFFFFFAE6);
                show_state_image.setImageResource(R.drawable.late);
                show_state_text.setText(R.string.classlate);
                show_state_text.setTextColor(0xFFFFCC00);
            } else if (_data.get(position).getCheckinsState() == 2) {//旷课
                item_content.setBackgroundColor(0xFFFFECF1);
                show_state_image.setImageResource(R.drawable.truancy);
                show_state_text.setText(R.string.classtruancy);
                show_state_text.setTextColor(0xFFFF3266);
            } else if (_data.get(position).getCheckinsState() == 3) {//请假
                item_content.setBackgroundColor(0xFFF2FBFF);
                show_state_image.setImageResource(R.drawable.leave);
                show_state_text.setText(R.string.classleave);
                show_state_text.setTextColor(0xFF00BAFF);
            } else {
                item_content.setBackgroundColor(0xFFFFFFF);
                show_state_image.setImageResource(0);
                show_state_text.setText("");
                show_state_text.setTextColor(0xFFFFFFF);
            }
//            studenid.setText("学号：" + _data.get(position).getSno());
//            Gson gson = new Gson();
//            Log.e("student",gson.toJson(_data.get(position)));
        }

        private void updateItem(int position) {
            if (mListView == null) {
                return;
            }

            // 获取当前可以看到的item位置
            int visiblePosition = mListView.getFirstVisiblePosition();
            // 如添加headerview后 firstview就是hearderview
            // 所有索引+1 取第一个view
            // View view = listview.getChildAt(index - visiblePosition + 1);
            // 获取点击的view
            View view = mListView.getChildAt(position - visiblePosition);
            TextView studentname = (TextView) view.findViewById(R.id.studentname);
            TextView authority = (TextView) view.findViewById(R.id.Authority);
            TextView studenid = (TextView) view.findViewById(R.id.studentid);
            item_content = (LinearLayout) view.findViewById(R.id.item_content);
            show_state_image = (ImageView) view.findViewById(R.id.show_state_image);
            show_state_text = (TextView) view.findViewById(R.id.show_state_text);
            studentname.setText(_data.get(position).getName());
            studenid.setText("学号：" + _data.get(position).getSno());
            if (_data.get(position).getClassCadre().equals("班长")) {
                authority.setText(R.string.banzhang);
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(R.drawable.position_title);
            }else if (_data.get(position).getClassCadre().equals("副班长")) {
                authority.setText(R.string.fubanzhang);
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(R.drawable.position_title2);
            }
            else {
                authority.setText("");
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(0);
            }

            //旷课2 迟到1 请假3
            if (_data.get(position).getCheckinsState() == 1) {//迟到
                item_content.setBackgroundColor(0xFFFFFAE6);
                show_state_image.setImageResource(R.drawable.late);
                show_state_text.setText(R.string.classlate);
                show_state_text.setTextColor(0xFFFFCC00);
            } else if (_data.get(position).getCheckinsState() == 2) {//旷课
                item_content.setBackgroundColor(0xFFFFECF1);
                show_state_image.setImageResource(R.drawable.truancy);
                show_state_text.setText(R.string.classtruancy);
                show_state_text.setTextColor(0xFFFF3266);
            } else if (_data.get(position).getCheckinsState() == 3) {//请假
                item_content.setBackgroundColor(0xFFF2FBFF);
                show_state_image.setImageResource(R.drawable.leave);
                show_state_text.setText(R.string.classleave);
                show_state_text.setTextColor(0xFF00BAFF);
            } else {
                item_content.setBackgroundColor(0xFFFFFFF);
                show_state_image.setImageResource(0);
                show_state_text.setText("");
                show_state_text.setTextColor(0xFFFFFFF);
            }
        }

        //实现setActionView方法
        @Override
        public void setActionView(final View actionView, final int position, final HorizontalScrollView parent) {

            actionView.findViewById(R.id.leave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // mAdapter.setSelectedPosition(position,0xFFF2FBFF,R.drawable.leave,R.string.classleave,0xFF00BAFF);
                    if (_data.get(position).getCheckinsState() == 0) {
                        //   Toast.makeText(getActivity(),"请假star item - " + position, Toast.LENGTH_SHORT).show();
                        jia++;
                        Integer temp = zhui - jia;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(3);
                        studentlist.get(position).setCheckinsState(3);
                        updateItem(position);
                    } else if (_data.get(position).getCheckinsState() == 1) {//迟到
                        chi--;
                        jia++;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(3);
                        studentlist.get(position).setCheckinsState(3);
                        updateItem(position);
                    } else if (_data.get(position).getCheckinsState() == 2) {//旷课
                        kuang--;
                        jia++;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(3);
                        studentlist.get(position).setCheckinsState(3);
                        updateItem(position);
                    } else if (_data.get(position).getCheckinsState() == 3) {//请假
                        jia--;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(0);
                        studentlist.get(position).setCheckinsState(0);
                        updateItem(position);
                    }
                    parent.smoothScrollTo(0, 0);
                }
            });

            actionView.findViewById(R.id.late).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//迟到1

                    if (_data.get(position).getCheckinsState() == 0) {
                        //   Toast.makeText(getActivity(),"请假star item - " + position, Toast.LENGTH_SHORT).show();
                        chi++;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(1);
                        studentlist.get(position).setCheckinsState(1);
                        updateItem(position);
                    } else if (_data.get(position).getCheckinsState() == 1) {//迟到
                        chi--;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(0);
                        studentlist.get(position).setCheckinsState(0);
                        updateItem(position);
                    } else if (_data.get(position).getCheckinsState() == 2) {//旷课
                        kuang--;
                        chi++;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(1);
                        studentlist.get(position).setCheckinsState(1);
                        updateItem(position);
                    } else if (_data.get(position).getCheckinsState() == 3) {//请假
                        jia--;
                        chi++;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(1);
                        studentlist.get(position).setCheckinsState(1);
                        updateItem(position);
                    }
                    parent.smoothScrollTo(0, 0);
                }
            });

            actionView.findViewById(R.id.truancy);
            actionView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {//旷课3
                    if (_data.get(position).getCheckinsState() == 0) {
                        //   Toast.makeText(getActivity(),"请假star item - " + position, Toast.LENGTH_SHORT).show();
                        kuang++;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(2);
                        studentlist.get(position).setCheckinsState(2);
                        updateItem(position);

                    } else if (_data.get(position).getCheckinsState() == 1) {//迟到
                        chi--;
                        kuang++;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(2);
                        studentlist.get(position).setCheckinsState(2);
                        updateItem(position);
                    } else if (_data.get(position).getCheckinsState() == 2) {//旷课
                        kuang--;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(0);
                        studentlist.get(position).setCheckinsState(0);
                        updateItem(position);
                    } else if (_data.get(position).getCheckinsState() == 3) {//请假
                        jia--;
                        kuang++;
                        Integer temp = zhui - chi - jia - kuang;
                        class_zhui.setText(temp.toString());
                        class_jia.setText(jia.toString());
                        class_chi.setText(chi.toString());
                        class_kuang.setText(kuang.toString());
                        _data.get(position).setCheckinsState(2);
                        studentlist.get(position).setCheckinsState(2);
                        updateItem(position);
                    }
                    parent.smoothScrollTo(0, 0);
                }
            });
        }
    }
}

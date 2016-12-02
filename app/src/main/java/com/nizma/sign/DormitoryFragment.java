package com.nizma.sign;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nizma.bean.AppError;
import com.nizma.bean.AppSuccess;
import com.nizma.bean.HzDormMember;
import com.nizma.bean.HzStudent;
import com.nizma.bean.UserInfo;
import com.nizma.swipelayout.DormSwipeLayoutAdapter;
import com.nizma.utils.RequestNet;
import com.nizma.www.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class DormitoryFragment extends Fragment {

    private static final int DORMCHECK_INFO_ERROR = 20;
    private static final int DORMCHECK_STUDENT_SHOW = 21;
    private static final int DORMCHECK_SUCCESS = 22;

    private View view;
    private DormSwipeLayoutAdapter mAdapter;
    private ListView mListView;
    private List<String> mData;
    private AppError appEroor;
    private AppSuccess appSuccess;
    private ArrayList<HzDormMember> dormmemberlist;

    private TextView dormitory_zhui;
    private TextView dormitory_jia;
    private TextView dormitory_shi;
    private Button dorcheck_button;
    private CheckBox dorm_hide_zhui;
    private Integer sum = 0;
    private Integer jia = 0;
    private Integer shi = 0;
    String students = "";

    public DormitoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dormitory, container, false);
        dorcheck_button = (Button) view.findViewById(R.id.dorcheck_button);
        dormitory_zhui = (TextView) view.findViewById(R.id.dormitory_zhui);
        dormitory_jia = (TextView) view.findViewById(R.id.dormitory_jia);
        dormitory_shi = (TextView) view.findViewById(R.id.dormitory_shi);
        dorm_hide_zhui = (CheckBox) view.findViewById(R.id.dorm_hide_zhui);

        sum = 0;
        jia = 0;
        shi = 0;
        appEroor = new AppError();
        appSuccess = new AppSuccess();
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                Map<String, Object> map = RequestNet.DormCheckInfo(UserInfo.StudentUser.getSno());
                if (map != null && map.size() > 0) {
                    Iterator keys = map.keySet().iterator();
                    String key = (String) keys.next();
                    if (key.equals("error")) {
                        appEroor = (AppError) map.get("error");
                        msg.what = DORMCHECK_INFO_ERROR;
                        mHandler.sendMessage(msg);
                    } else {
                        dormmemberlist = (ArrayList<HzDormMember>) map.get("dormmember");
                        sum = dormmemberlist.size();
                        msg.what = DORMCHECK_STUDENT_SHOW;
                        mHandler.sendMessage(msg);
                    }
                }

            }
        }.start();
        dorcheck_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < dormmemberlist.size(); i++) {
                    if (dormmemberlist.get(i).getDcstate() != null) {
                        if (dormmemberlist.get(i).getDcstate().equals("1")) {
                            students += dormmemberlist.get(i).getStudentId() + "-" + dormmemberlist.get(i).getDcstate() + ",";
                        } else if (dormmemberlist.get(i).getDcstate().equals("2")) {
                            students += dormmemberlist.get(i).getStudentId() + "-" + dormmemberlist.get(i).getDcstate() + ",";
                        }
                    }
                }
                new Thread() {
                    @Override
                    public void run() {
                        Log.e("students", students);
                        Map<String, Object> result = RequestNet.DormCheck(students);
                        Message msg = new Message();
                        Iterator keys = result.keySet().iterator();
                        String key = (String) keys.next();
                        if (key.equals("error")) {
                            appEroor = (AppError) result.get("error");
                            msg.what = DORMCHECK_INFO_ERROR;
                            mHandler.sendMessage(msg);
                        } else {
                            appSuccess = (AppSuccess) result.get("success");
                            msg.what = DORMCHECK_SUCCESS;
                            mHandler.sendMessage(msg);
                        }
                    }
                }.start();
            }
        });

        dorm_hide_zhui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dormmemberlist != null && dormmemberlist.size() > 0) {
                    if (isChecked) {
                        ArrayList<HzDormMember> dormmemberlist2 = new ArrayList<HzDormMember>();
                        for (int i = 0; i < dormmemberlist.size(); i++) {
                            if (dormmemberlist.get(i).getDcstate() != null) {
                                dormmemberlist2.add(dormmemberlist.get(i));
                            }
                        }
                        mAdapter = new MyAdapater(getActivity(), R.layout.item_content, R.layout.item_action2, dormmemberlist2);
                        mListView.setAdapter(mAdapter);
                    } else {
                        mAdapter = new MyAdapater(getActivity(), R.layout.item_content, R.layout.item_action2, dormmemberlist);
                        mListView.setAdapter(mAdapter);
                    }
                }
            }
        });
        return view;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DORMCHECK_INFO_ERROR:
                    Toast.makeText(getActivity(), appEroor.getError(), Toast.LENGTH_LONG).show();
                    break;
                case DORMCHECK_STUDENT_SHOW:
                    TextView dormitory_id = (TextView) view.findViewById(R.id.dormitory_id);

                    dormitory_zhui.setText(sum + "");
                    dormitory_jia.setText(jia + "");
                    dormitory_shi.setText(shi + "");
                    //                   dormitory_id.setText(dormmemberlist.get(0).getQuarter() + dormmemberlist.get(0).getBuilding() + "幢" + dormmemberlist.get(0).getDormNum() + "室" + "-回寝");
                    dormitory_id.setText(UserInfo.StudentUser.getDoridName() + "-回寝");
                    mListView = (ListView) view.findViewById(R.id.dormitory_list);
                    mAdapter = new MyAdapater(getActivity(), R.layout.item_content, R.layout.item_action2, dormmemberlist);
                    mListView.setAdapter(mAdapter);
                    break;
                case DORMCHECK_SUCCESS:
                    Toast.makeText(getActivity(), appSuccess.getSuccess(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    //适配器
    class MyAdapater extends DormSwipeLayoutAdapter<Object> {
        private List<HzDormMember> _data;
        LinearLayout item_content;
        ImageView show_state_image;
        TextView show_state_text;

        public MyAdapater(Activity context, int contentViewResourceId, int actionViewResourceId, ArrayList<HzDormMember> objects) {
            super(context, contentViewResourceId, actionViewResourceId, objects);
            this._data = objects;
        }

        //实现setContentView方法
        @Override
        public void setContentView(View contentView, int position, HorizontalScrollView parent) {

            TextView studentname = (TextView) contentView.findViewById(R.id.studentname);
            TextView studentid = (TextView) contentView.findViewById(R.id.studentid);
            TextView authority = (TextView) contentView.findViewById(R.id.Authority);
            studentname.setText(_data.get(position).getStudentName());
            studentid.setText("学号：" + _data.get(position).getStudentId());
            if (_data.get(position).getCadreId() == 1) {
                authority.setText(R.string.banzhang);
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(R.drawable.position_title);
            } else {
                authority.setText("");
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(0);
            }
            item_content = (LinearLayout) contentView.findViewById(R.id.item_content);
            show_state_image = (ImageView) contentView.findViewById(R.id.show_state_image);
            show_state_text = (TextView) contentView.findViewById(R.id.show_state_text);

            if (_data.get(position).getDcstate() == null) {
                item_content.setBackgroundColor(0xFFFFFFF);
                show_state_image.setImageResource(0);
                show_state_text.setText("");
                show_state_text.setTextColor(0xFFFFFFF);
            } else {
                if (_data.get(position).getDcstate().equals("2")) {

                    item_content.setBackgroundColor(0xFFFFECF1);
                    show_state_image.setImageResource(R.drawable.truancy);
                    show_state_text.setText(R.string.dormitorytruancy);
                    show_state_text.setTextColor(0xFFFF3266);
                } else {
                    item_content.setBackgroundColor(0xFFF2FBFF);
                    show_state_image.setImageResource(R.drawable.leave);
                    show_state_text.setText(R.string.classleave);
                    show_state_text.setTextColor(0xFF00BAFF);
                }
            }
        }

        private void updateItem(int position) {
            if (mListView == null) {
                return;
            }
            // 获取当前可以看到的item位置
            int visiblePosition = mListView.getFirstVisiblePosition();
            View view = mListView.getChildAt(position - visiblePosition);
            TextView studentname = (TextView) view.findViewById(R.id.studentname);
            TextView authority = (TextView) view.findViewById(R.id.Authority);
            TextView studenid = (TextView) view.findViewById(R.id.studentid);
            item_content = (LinearLayout) view.findViewById(R.id.item_content);
            show_state_image = (ImageView) view.findViewById(R.id.show_state_image);
            show_state_text = (TextView) view.findViewById(R.id.show_state_text);
            studentname.setText(_data.get(position).getStudentName());
            studenid.setText("学号：" + _data.get(position).getStudentId());
            if (_data.get(position).getCadreId() == 1) {
                authority.setText(R.string.banzhang);
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(R.drawable.position_title);
            } else {
                authority.setText("");
                authority.setTextColor(0xffffffff);
                authority.setBackgroundResource(0);
            }
            if (_data.get(position).getDcstate() == null) {
                item_content.setBackgroundColor(0xFFFFFFF);
                show_state_image.setImageResource(0);
                show_state_text.setText("");
                show_state_text.setTextColor(0xFFFFFFF);
            } else {
                if (_data.get(position).getDcstate().equals("2")) {

                    item_content.setBackgroundColor(0xFFFFECF1);
                    show_state_image.setImageResource(R.drawable.truancy);
                    show_state_text.setText(R.string.dormitorytruancy);
                    show_state_text.setTextColor(0xFFFF3266);
                } else {
                    item_content.setBackgroundColor(0xFFF2FBFF);
                    show_state_image.setImageResource(R.drawable.leave);
                    show_state_text.setText(R.string.classleave);
                    show_state_text.setTextColor(0xFF00BAFF);
                }
            }
        }

        //实现setActionView方法
        @Override
        public void setActionView(View actionView, final int position, final HorizontalScrollView parent) {

            actionView.findViewById(R.id.dormitory_leave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mAdapter.setSelectedPosition(position, 0xFFF2FBFF, R.drawable.leave, R.string.dormitoryleave, 0xFF00BAFF);
                    if (_data.get(position).getDcstate() == null) {
                        //   Toast.makeText(getActivity(),"请假star item - " + position, Toast.LENGTH_SHORT).show();
                        jia++;
                        Integer temp = sum - jia - shi;
                        dormitory_zhui.setText(temp.toString());
                        dormitory_jia.setText(jia + "");
                        dormitory_shi.setText(shi + "");
                        dormmemberlist.get(position).setDcstate(1 + "");
                        updateItem(position);
                    } else if (_data.get(position).getDcstate().equals("1")) {//请假
                        jia--;
                        Integer temp = sum - shi - jia;
                        dormitory_zhui.setText(temp.toString());
                        dormitory_jia.setText(jia.toString());
                        dormitory_shi.setText(shi + "");
                        dormmemberlist.get(position).setDcstate(null);
                        updateItem(position);
                    } else if (_data.get(position).getDcstate().equals("2")) {//失联
                        shi--;
                        jia++;
                        Integer temp = sum - shi - jia;
                        dormitory_zhui.setText(temp.toString());
                        dormitory_jia.setText(jia.toString());
                        dormitory_shi.setText(shi + "");
                        dormmemberlist.get(position).setDcstate(1 + "");
                        updateItem(position);
                    }
                    parent.smoothScrollTo(0, 0);
                    return;
//                    Toast.makeText(getActivity(), v.getId() + "star item - " + position, Toast.LENGTH_SHORT).show();
                }
            });
            actionView.findViewById(R.id.dormitory_truancy).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mAdapter.setSelectedPosition(position, 0xFFFFECF1, R.drawable.truancy, R.string.dormitorytruancy, 0xFFFF3266);
                    if (_data.get(position).getDcstate() == null) {
                        //   Toast.makeText(getActivity(),"请假star item - " + position, Toast.LENGTH_SHORT).show();
                        shi++;
                        Integer temp = sum - jia - shi;
                        dormitory_zhui.setText(temp.toString());
                        dormitory_jia.setText(jia.toString());
                        dormitory_shi.setText(shi.toString());
                        dormmemberlist.get(position).setDcstate(2 + "");
                        updateItem(position);
                    } else if (_data.get(position).getDcstate().equals("1")) {//请假
                        shi++;
                        jia--;
                        Integer temp = sum - shi - jia;
                        dormitory_zhui.setText(temp.toString());
                        dormitory_jia.setText(jia.toString());
                        dormitory_shi.setText(shi.toString());
                        dormmemberlist.get(position).setDcstate(2 + "");
                        updateItem(position);
                    } else if (_data.get(position).getDcstate().equals("2")) {//失联
                        shi--;
                        Integer temp = sum - shi - jia;
                        dormitory_zhui.setText(temp.toString());
                        dormitory_jia.setText(jia.toString());
                        dormitory_shi.setText(shi.toString());
                        dormmemberlist.get(position).setDcstate(null);
                        updateItem(position);
                    }
                    parent.smoothScrollTo(0, 0);
                    return;
//                    Toast.makeText(getActivity(), v.getId() + "star item - " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

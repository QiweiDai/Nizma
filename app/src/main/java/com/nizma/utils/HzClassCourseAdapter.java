package com.nizma.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nizma.bean.HzClassCourse;
import com.nizma.www.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by WZW on 2016/9/23.
 */
public class HzClassCourseAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<HzClassCourse> list;

    public HzClassCourseAdapter(Context context, ArrayList<HzClassCourse> list) {
        super();
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position).getCourse();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.myspinner_dropdown_item, null);
            viewHolder = new ViewHolder();
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.myspinner_dropdown_layout);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.myspinner_dropdown_txt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.layout.setBackgroundColor(0xffffffff);
        if(list.size() == position+1){
            // viewHolder.layout.setBackgroundColor(0xfff2fbff);
        }else{
            //viewHolder.layout.setBackgroundColor(0xFFFFFAE6);
        }
        HzClassCourse hcc = (HzClassCourse)list.get(position);
        viewHolder.textView.setText(hcc.getCourse());
        return convertView;
    }

    public class ViewHolder {
        LinearLayout layout;
        TextView textView;
    }
/*
    public void refresh(List<String> l) {
        this.list.clear();
        list.addAll(l);
        notifyDataSetChanged();
    }

    public void add(String str) {
        list.add(str);
        notifyDataSetChanged();
    }

    public void add(ArrayList<String> str) {
        list.addAll(str);
        notifyDataSetChanged();

    }*/
}

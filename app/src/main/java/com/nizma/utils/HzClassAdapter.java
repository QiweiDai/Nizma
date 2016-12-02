package com.nizma.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nizma.bean.HzClasses;
import com.nizma.bean.HzTeacher;
import com.nizma.www.R;

import java.util.ArrayList;

/**
 * Created by WZW on 2016/9/23.
 */
public class HzClassAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<HzClasses> list;

    public HzClassAdapter(Context context, ArrayList<HzClasses> list) {
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
        return list.get(position).getClassName();
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
        HzClasses hcc = list.get(position);

        viewHolder.textView.setText(hcc.getClassName());
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

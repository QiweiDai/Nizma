package com.nizma.swipelayout;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nizma.bean.HzStudent;
import com.nizma.www.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShyBoooy on 14/12/5.
 */
public abstract class SwipeLayoutAdapter<Object> extends ArrayAdapter
{
    private static final int _resourceId = R.layout.item_swipe;
    int _contentViewResourceId;   //item的内容区 的id
    int _actionViewResourceId; //item操作区(滑动显示区域)的
    private int _itemWidth = 0;
    private int selectedPosition = -1;
    private int selectedColor = -1;
    private int selectedTextcolor = -1;
    private int selectedDrawable = -1;
    private int selectedText = -1;
    /**
     * 构造函数
     * @param context
     * @param contentViewResourceId
     * @param actionViewResourceId
     * @param objects
     */
    public SwipeLayoutAdapter(Context context,int contentViewResourceId,int actionViewResourceId,ArrayList<HzStudent> objects)
    {
        super(context,R.layout.item_swipe,objects);
        _contentViewResourceId = contentViewResourceId;
        _actionViewResourceId = actionViewResourceId;
    }

    /**
     * 构造函数
     * @param context
     * @param contentViewResourceId
     * @param actionViewResourceId
     * @param objects
     */
    public SwipeLayoutAdapter(Context context,int contentViewResourceId,int actionViewResourceId,List<String> objects)
    {
        super(context,R.layout.item_swipe,objects);
        _contentViewResourceId = contentViewResourceId;
        _actionViewResourceId = actionViewResourceId;
    }

    public void setSelectedPosition(int position,int color,int drawable,int text,int textColor) {
        selectedPosition = position;
        selectedColor = color;
        selectedDrawable = drawable;
        selectedText = text;
        selectedTextcolor = textColor;
    }
    /**
     * 用户设置自定义的View(item展示的内容区)
     * @param contentView
     * @param position
     * @param parent
     */
    public abstract void setContentView(View contentView,int position,HorizontalScrollView parent);

    /**
     * 用户设置自定义的View(隐藏的操作区)
     * @param actionView
     * @param position
     * @param parent
     */
    public abstract void setActionView(View actionView,int position,HorizontalScrollView parent);
    @Override
    public View getView(int position,View convertView, ViewGroup parent)
    {
        final SwipeViewHolder viewHolder;

        if(convertView == null)
        {
            //获取Item
            convertView = LayoutInflater.from(getContext()).inflate(_resourceId,parent,false);
            //获取内容显示区域的View
            View contentView = LayoutInflater.from(getContext()).inflate(_contentViewResourceId,parent,false);
            //获取操作区的View
            View actionView = LayoutInflater.from(getContext()).inflate(_actionViewResourceId,parent,false);

            viewHolder = new SwipeViewHolder();
            //获取item组件
            viewHolder.hSView = (HorizontalScrollView)convertView.findViewById(R.id.hsv);
            viewHolder.viewContainer = (LinearLayout)convertView.findViewById(R.id.item_view_container);
            //把内容区和操作区添加到item
            viewHolder.viewContainer.addView(contentView);
            viewHolder.viewContainer.addView(actionView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (SwipeViewHolder)convertView.getTag();
            viewHolder.hSView.scrollTo(0,0);
        }
        //获取item的宽度
        ViewTreeObserver vto = viewHolder.hSView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(_itemWidth == 0)
                {
                    _itemWidth = viewHolder.hSView.getMeasuredWidth();
                    notifyDataSetChanged();
                }
            }
        });
        //设置默认宽度
        ViewGroup.LayoutParams lpContent =viewHolder.viewContainer.getChildAt(0).getLayoutParams();
        lpContent.width = _itemWidth;
        //设置默认宽度
        ViewGroup.LayoutParams lpAction = viewHolder.viewContainer.getChildAt(1).getLayoutParams();
        lpAction.width = _itemWidth /2;
        //定义item显示的内容区
        setContentView(viewHolder.viewContainer.getChildAt(0),position,viewHolder.hSView);
        //定义item隐藏的操作区域
        setActionView(viewHolder.viewContainer.getChildAt(1),position,viewHolder.hSView);
        //设置滑动事件监听
        convertView.setOnTouchListener(new SwipeOnTouchListener());
        return convertView;
    }
}

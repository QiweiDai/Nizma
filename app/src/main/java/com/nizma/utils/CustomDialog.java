package com.nizma.utils;

/**
 * Created by w-w on 2016/11/2.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nizma.bean.HzStudent;
import com.nizma.www.R;

import java.util.List;

public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        p.height = d.getWidth()/2;
        getWindow().setAttributes(p);
    }
    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private DialogAdapte adapte;
        private ListView listView;
        private List<HzStudent> studentList;
        private int color;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public List<HzStudent> getStudentList() {
            return studentList;
        }

        public void setStudentList(List<HzStudent> studentList) {
            this.studentList = studentList;
        }



        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         * @paramtitle
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);

            listView = (ListView) layout.findViewById(R.id.studentinfo);
            adapte = new DialogAdapte(context,studentList);
            listView.setAdapter(adapte);

            if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }

        class DialogAdapte extends BaseAdapter {
            private LinearLayout mInflater;
            List<HzStudent> studentList;
            Context context;

            public DialogAdapte(Context context, List<HzStudent> mData) {
                this.context = context;
                this.studentList = mData;
            }


            @Override
            public int getCount() {
                return studentList.size();
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
                    convertView = mInflater.inflate(context, R.layout.dialog_item, null);
                }
                TextView classText = (TextView) convertView.findViewById(R.id.classText);
                TextView snoText = (TextView) convertView.findViewById(R.id.snoText);
                TextView courseNameText = (TextView) convertView.findViewById(R.id.courseNameText);
                TextView teacherNameText = (TextView) convertView.findViewById(R.id.teacherNameText);
                TextView timeText = (TextView) convertView.findViewById(R.id.timeText);
                TextView sectionText = (TextView) convertView.findViewById(R.id.sectionText);
                convertView.setBackgroundColor(color);
                classText.setText(studentList.get(position).getClassName());
                snoText.setText(studentList.get(position).getSno());
                courseNameText.setText(studentList.get(position).getCourseName());
                teacherNameText.setText(studentList.get(position).getTeacherName());
                timeText.setText(studentList.get(position).getCourseDateStr());
                sectionText.setText(studentList.get(position).getSection());
                return convertView;
            }
        }
    }
}

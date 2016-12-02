package com.nizma.sign;import android.content.res.Resources;import android.graphics.Color;import android.os.Bundle;import android.os.Handler;import android.os.Message;import android.support.v4.app.Fragment;import android.view.LayoutInflater;import android.view.Menu;import android.view.MenuInflater;import android.view.MenuItem;import android.view.View;import android.view.ViewGroup;import android.widget.TextView;import android.widget.Toast;import com.nizma.utils.RequestNet;import com.nizma.www.R;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.Arrays;import java.util.Calendar;import java.util.List;import java.util.Map;import lecho.lib.hellocharts.gesture.ZoomType;import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;import lecho.lib.hellocharts.model.Axis;import lecho.lib.hellocharts.model.AxisValue;import lecho.lib.hellocharts.model.Column;import lecho.lib.hellocharts.model.ColumnChartData;import lecho.lib.hellocharts.model.SubcolumnValue;import lecho.lib.hellocharts.util.ChartUtils;import lecho.lib.hellocharts.view.Chart;import lecho.lib.hellocharts.view.ColumnChartView;/** * A simple {@link Fragment} subclass. */public class IndexFragment extends Fragment {    public static final int QUERY_ALL = 1;    private List<Map<String, Integer>> queryall;    private List<Map<String, Integer>> todaylist;    private ColumnChartView chart;    //声明所需变量    public static String[] months = new String[]{};    ColumnChartData columnData;    private View view;    private TextView class_check_late;    private TextView class_check_lose;    private TextView dormitory_check_leave;    private TextView dormitory_check_lose;    public IndexFragment() {        // Required empty public constructor    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {        // Inflate the layout for this fragment        view = inflater.inflate(R.layout.fragment_index, container, false);        class_check_late = (TextView) view.findViewById(R.id.class_check_late);        class_check_lose = (TextView) view.findViewById(R.id.class_check_lose);        dormitory_check_leave = (TextView) view.findViewById(R.id.dormitory_check_leave);        dormitory_check_lose = (TextView) view.findViewById(R.id.dormitory_check_lose);//        chart = (ColumnChartView) view.findViewById(R.id.column_chart);//        chart.setZoomEnabled(false);        new Thread() {            @Override            public void run() {//                queryall = RequestNet.queryweek();                todaylist = RequestNet.todaycheck();                Message msg = new Message();                msg.what = QUERY_ALL;                mHandler.sendMessage(msg);            }        }.start();        return view;    }    private Handler mHandler = new Handler() {        @Override        public void handleMessage(Message msg) {            switch (msg.what) {                case QUERY_ALL:  //                  generateStackedData();                    class_check_late.setText(todaylist.get(0).get("belatefornum").toString());                    class_check_lose.setText(todaylist.get(0).get("cutaclassnum").toString());                    dormitory_check_leave.setText(todaylist.get(0).get("dormitryleave").toString());                    dormitory_check_lose.setText(todaylist.get(0).get("dormitrylose").toString());                    break;            }        }    };    /**     * Generates columns with stacked subcolumns.     */    private void generateStackedData() {        int numSubcolumns = 3;        List<String> time = getPassedSevenDays();        int size = time.size();        months = (String[]) time.toArray(new String[size]);        int numColumns = months.length;        Resources resources = getActivity().getResources();        int chidao = resources.getColor(R.color.latebgcolor);        int shilian = resources.getColor(R.color.truancybgcolor);        int qingjia = resources.getColor(R.color.leavebgcolor);        int colors[] = {qingjia, chidao, shilian};        List<AxisValue> axisValues = new ArrayList<AxisValue>();        List<Column> columns = new ArrayList<Column>();        List<SubcolumnValue> values;        if (queryall.size() > 0) {            for (int i = 0; i < numColumns; ++i) {                values = new ArrayList<SubcolumnValue>();                for (int j = 0; j < numSubcolumns; ++j) {                    Integer belatefornum = (Integer) queryall.get(i).get((j + 1) + "");                    values.add(new SubcolumnValue((float) belatefornum,                            colors[j]));                }// 点击柱状图就展示数据量                axisValues.add(new AxisValue(i).setLabel(months[i]));                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));            }        }        columnData = new ColumnChartData(columns);        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true).setTextColor(Color.BLACK).setTextSize(13));        columnData.setAxisYLeft(new Axis().setHasLines(false).setTextColor(Color.BLACK).setMaxLabelChars(2));        chart.setColumnChartData(columnData);        // Set value touch listener that will trigger changes for chartTop.        //columnChart.setOnValueTouchListener(new ValueTouchListener());        // Set selection mode to keep selected month column highlighted.        chart.setValueSelectionEnabled(true);        chart.setZoomType(ZoomType.HORIZONTAL);    }    /**     * 获取过去7天     */    private List<String> getPassedSevenDays() {        SimpleDateFormat fomater = new SimpleDateFormat("MM-dd");        Calendar calendar = Calendar.getInstance();        List<String> timelist = new ArrayList<String>();        // 测试跨年的情况        // calendar.set(2016, 12, 4);        int year = calendar.get(Calendar.YEAR);        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);        int j = 1;        for (int i = 0; i < 7; i++) {            calendar.set(Calendar.DAY_OF_YEAR, dayOfYear - j);            if (calendar.get(Calendar.YEAR) < year) {                // 跨年了                j = 1;                // 更新 标记年                year--;                // 重置日历                calendar.set(year, Calendar.DECEMBER, 31);                // 重新获取dayOfYear                dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);            } else {                j++;            }            timelist.add(fomater.format(calendar.getTime()));        }        return timelist;    }}
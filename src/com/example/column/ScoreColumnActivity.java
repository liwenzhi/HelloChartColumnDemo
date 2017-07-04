package com.example.column;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.lwz.chart.hellocharts.listener.ColumnChartOnValueSelectListener;
import com.lwz.chart.hellocharts.model.*;
import com.lwz.chart.hellocharts.util.ChartUtils;
import com.lwz.chart.hellocharts.view.ColumnChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * hellochart
 * 成绩和人数关系的柱状图
 * <p/>
 * 有个比较坑的地方就是柱状图的高度，他会根据最长的那个数值来确定单位长度!
 */
public class ScoreColumnActivity extends Activity {


    private ColumnChartView chart;            //柱状图的自定义View
    private ColumnChartData data;             //存放柱状图数据的对象
    private boolean hasAxes = true;            //是否有坐标轴
    private boolean hasAxesNames = true;       //是否有坐标轴的名字
    private boolean hasLabels = true;          //柱子上是否显示标识文字
    private boolean hasLabelForSelected = true;    //柱子被点击时，是否显示标识的文字

    //一个班的分数数据
    private int[] scores = {1, 11, 22, 33, 44, 55, 66, 77, 88, 99, 100,
            26, 65, 54, 54, 98, 32, 23, 32, 55, 88, 94,
            56, 88, 89, 78, 95, 99, 96, 94, 93, 56, 55};

    private int[] scoreRange = new int[10];
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();   //x轴方向的坐标数据
    private List<AxisValue> mAxisYValues = new ArrayList<AxisValue>();            //y轴方向的坐标数据

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_column_activity);
        String title = getIntent().getStringExtra("title");
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("" + title);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        chart = (ColumnChartView) findViewById(R.id.columnChart);

    }

    private void initData() {
        generateDefaultData();
    }

    private void initEvent() {
        chart.setOnValueTouchListener(new ValueTouchListener());
    }

    /**
     * 默认显示的数据
     */
    private void generateDefaultData() {


        int numSubcolumns = 1;
        int numColumns = 10;
        int maxColumns = 0;
        //判断每个范围内的个数
        for (int i = 0; i < scores.length; i++) {

            for (int j = 0; j < numColumns; j++) {
                if (scores[i] < (j + 1) * 10) {
                    scoreRange[j]++;
                    if (scoreRange[j] > maxColumns) {
                        maxColumns = scoreRange[j];
                    }
                    break;
                }
            }

        }


        //设置x轴坐标 ，显示的是时间0-10,11-20,21-30...
        mAxisXValues.clear();
        for (int i = 0; i < 10; i++) {      //mClockNumberLength
            mAxisXValues.add(new AxisValue(i).setLabel(10 * i + "-" + (10 * i + 10)));
        }

        //设置y轴坐标，显示的是数值0、1、2、3.。。。  这里的长度好像没啥效果，框架会根据最长那段数据做处理
        mAxisYValues.clear();
        for (int i = 0; i <= maxColumns + 1; i++) {
            mAxisYValues.add(new AxisValue(i).setLabel("" + i));
        }


        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(scoreRange[i], ChartUtils.pickColor()).setLabel("" + scoreRange[i]));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("成绩分布");
                axisY.setName("人数");
            }

            //对x轴，数据和属性的设置
            axisX.setTextSize(8);//设置字体的大小
            axisX.setHasTiltedLabels(false);//x坐标轴字体是斜的显示还是直的，true表示斜的
            axisX.setTextColor(Color.GRAY);//设置字体颜色
            axisX.setHasLines(true);//x轴的分割线
            axisX.setValues(mAxisXValues); //设置x轴各个坐标点名称

            //对Y轴 ，数据和属性的设置
            axisY.setTextSize(10);
            axisY.setHasTiltedLabels(false);//true表示斜的
            axisY.setTextColor(Color.GRAY);//设置字体颜色
            axisY.setValues(mAxisYValues); //设置y轴各个坐标点名称

            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }


    /**
     * 图像的监听
     */
    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            showToast("Selected: " + value);
        }

        @Override
        public void onValueDeselected() {

        }

    }


    Toast toast;

    public void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }


}

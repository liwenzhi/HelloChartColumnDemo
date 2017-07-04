package com.example.column;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.lwz.chart.hellocharts.listener.ColumnChartOnValueSelectListener;
import com.lwz.chart.hellocharts.model.*;
import com.lwz.chart.hellocharts.view.ColumnChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * hellochart
 * 几个班级的成绩分布的柱状图（一堆堆的）
 * 三个班级A，B，C,分别对比三个阶段的分数小于60，60到80，80以上
 */
public class ScoreHeapColumnActivity extends Activity {


    private ColumnChartView chart;            //柱状图的自定义View
    private ColumnChartData data;             //存放柱状图数据的对象
    private boolean hasAxes = true;            //是否有坐标轴
    private boolean hasAxesNames = true;       //是否有坐标轴的名字
    private boolean hasLabels = false;          //柱子上是否显示标识文字
    private boolean hasLabelForSelected = true;    //柱子被点击时，是否显示标识的文字

    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();   //x轴方向的坐标数据
    private List<AxisValue> mAxisYValues = new ArrayList<AxisValue>();  //y轴方向的坐标数据

    //班级ABC的柱状图颜色
    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN};

    private int[] score1 = {88, 77, 99, 56, 48, 51, 33, 62, 56, 89, 98, 88, 97, 95, 81, 86, 94, 75, 86, 44};   //第一个班级的分数
    private int[] score2 = {98, 77, 89, 86, 48, 51, 13, 82, 58, 89, 98, 88, 87, 95, 81, 86, 94, 85, 86, 44};   //第二个班级的分数
    private int[] score3 = {88, 97, 99, 56, 98, 51, 33, 22, 56, 99, 98, 88, 97, 95, 81, 86, 24, 75, 26, 44};   //第三个班级的分数

    //三个班级各阶段分数的数量
    private int[] scoreNum1 = new int[3];
    private int[] scoreNum2 = new int[3];
    private int[] scoreNum3 = new int[3];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_column_activity);
        String title = getIntent().getStringExtra("title");
        TextView tv_title = (TextView) findViewById(R.id.tv_title);

        findViewById(R.id.tv1).setVisibility(View.VISIBLE);
        findViewById(R.id.tv2).setVisibility(View.VISIBLE);
        findViewById(R.id.tv3).setVisibility(View.VISIBLE);

        tv_title.setText("" + title);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        chart = (ColumnChartView) findViewById(R.id.columnChart);

    }

    private void initData() {
        //计算三个阶段的数据，计算每个阶段的总和即可
        calculate(score1, scoreNum1);
        calculate(score2, scoreNum2);
        calculate(score3, scoreNum3);

        //设计x轴和y轴的坐标
        initXY();

        generateSubcolumnsData();  //一堆堆的柱状图

    }

    private void initXY() {
        //设置x轴上面的数据
        mAxisXValues.add(new AxisValue(0).setLabel("<60"));
        mAxisXValues.add(new AxisValue(1).setLabel("60-80"));
        mAxisXValues.add(new AxisValue(2).setLabel("80-100"));

        //设置Y轴上面的数据
        for (int i = 0; i < 20; i++) {
            mAxisYValues.add(new AxisValue(i).setLabel("" + i));
        }


    }

    private void initEvent() {
        chart.setOnValueTouchListener(new ValueTouchListener());
    }


    /**
     * 计算各个阶段的数量
     */
    private void calculate(int[] score, int[] scoreNum) {
        for (int i = 0; i < score.length; i++) {
            int s = score[i];
            if (s < 60) {
                scoreNum[0]++;
            } else if (s < 80) {
                scoreNum[1]++;
            } else if (s <= 100) {
                scoreNum[2]++;
            }
        }

    }

    /**
     * 堆堆的列
     */
    private void generateSubcolumnsData() {
        int numColumns = 3;    //有多少堆数据
        // Column can have many subcolumns, here I use 4 subcolumn in each of 4 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;


        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();

            //第一堆，小于60部分
            if (i == 0) {
                //班级A的数量
                values.add(new SubcolumnValue(scoreNum1[0], colors[0]));
                //班级B的数量
                values.add(new SubcolumnValue(scoreNum2[0], colors[1]));
                //班级C的数量
                values.add(new SubcolumnValue(scoreNum3[0], colors[2]));
            }

            //第二堆，60到80部分
            if (i == 1) {
                //班级A的数量
                values.add(new SubcolumnValue(scoreNum1[1], colors[0]));
                //班级B的数量
                values.add(new SubcolumnValue(scoreNum2[1], colors[1]));
                //班级C的数量
                values.add(new SubcolumnValue(scoreNum3[1], colors[2]));
            }

            //第三堆，80以上部分
            if (i == 2) {
                //班级A的数量
                values.add(new SubcolumnValue(scoreNum1[2], colors[0]));
                //班级B的数量
                values.add(new SubcolumnValue(scoreNum2[2], colors[1]));
                //班级C的数量
                values.add(new SubcolumnValue(scoreNum3[2], colors[2]));
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
                axisX.setName("各个阶段的数据");
                axisY.setName("人数");
            }

            //设置x轴的坐标
            axisX.setValues(mAxisXValues);

            //设置Y轴的坐标
            axisY.setValues(mAxisYValues);

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

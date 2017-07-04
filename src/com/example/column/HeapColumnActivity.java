package com.example.column;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.lwz.chart.hellocharts.listener.ColumnChartOnValueSelectListener;
import com.lwz.chart.hellocharts.model.Axis;
import com.lwz.chart.hellocharts.model.Column;
import com.lwz.chart.hellocharts.model.ColumnChartData;
import com.lwz.chart.hellocharts.model.SubcolumnValue;
import com.lwz.chart.hellocharts.util.ChartUtils;
import com.lwz.chart.hellocharts.view.ColumnChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * hellochart
 * 一堆堆的简单柱状图
 */
public class HeapColumnActivity extends Activity {


    private ColumnChartView chart;            //柱状图的自定义View
    private ColumnChartData data;             //存放柱状图数据的对象
    private boolean hasAxes = true;            //是否有坐标轴
    private boolean hasAxesNames = true;       //是否有坐标轴的名字
    private boolean hasLabels = false;          //柱子上是否显示标识文字
    private boolean hasLabelForSelected = true;    //柱子被点击时，是否显示标识的文字


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
        generateSubcolumnsData();  //一堆堆的柱状图

    }


    private void initEvent() {
        chart.setOnValueTouchListener(new ValueTouchListener());
    }


    /**
     * 堆堆的列
     */
    private void generateSubcolumnsData() {

        int numSubcolumns = 3; //每一堆有多少列 ,3个班级
        int numColumns = 3;    //有多少堆数据，三个阶段
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;


        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
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
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
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

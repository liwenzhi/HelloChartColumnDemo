#hellochart详细讲解（三）柱状图


之前有介绍HelloChart图形绘制框架的使用，还有各种效果图：

http://blog.csdn.net/wenzhi20102321/article/details/73133718


之前没有对各个图形设计做详细介绍。

本文重点hellochart柱状图的设计。


 



##总览图：

 ![](http://i.imgur.com/DPEC8Zl.png)
 

一共显示4种基本图形，其他的一些样式感觉没啥用处，就不一一演示了！

第一种简单饼状图，设置很少的属性：



##简单柱状图


设置几个随机数字进去，然后按照数值的大小显示柱状图。


 ![1](http://i.imgur.com/jhRiNBQ.png)



##一堆堆的柱状图

 
![2](http://i.imgur.com/9O7azXA.png)





##成绩和人数关系的柱状图

   //一个班的分数数据
   
```
    private int[] scores = {1, 11, 22, 33, 44, 55, 66, 77, 88, 99, 100,
            26, 65, 54, 54, 98, 32, 23, 32, 55, 88, 94,
            56, 88, 89, 78, 95, 99, 96, 94, 93, 56, 55};
```

  //求出每个范围内的数量，并显示
  
![3](http://i.imgur.com/lJEmDkL.png)



##三个班级各阶段对比的柱状图

//对比三个班级的各阶段的人数

![4](http://i.imgur.com/7xLbQQk.png)



 

#柱状图使用讲解

##（一）依赖hellochart，或导入jar包

依赖和jar包都可以上官网找，我的项目中也有jar包（在后面）。


##（二）布局文件

```
//这是我自己打包的jar包，包名和官网不一样，但是类名完全一样的
  <com.lwz.chart.hellocharts.view.ColumnChartView
            android:layout_height="match_parent"
            android:layout_width="match_parent"
            android:id="@+id/columnChart"
            />


```


##（三）代码

//通过简单柱状图代码来学会使用

```
 
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
 * 简单柱状图
 */
public class SimpleColumnActivity extends Activity {


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
        int numColumns = 8;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
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




```


上面有些属性的意思看，上面的注解就差不多知道了。

但是有些人对分成多少列，每列的数据如何分配，分成多少堆会有些疑问

以及x轴，y轴坐标的显示

但是这些知识和hellochart前面介绍的知识是一样的，大家可以看看折线图参考


 
也可以先运行下，我的代码看看效果，再替换下数据。

  

#共勉：有些事情，即使你不愿意看到，但是他却是会时常发生的，勇敢去面对吧！

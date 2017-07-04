package com.example.column;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * hellochart
 * 柱状图使用示例
 */
public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    /**
     * 跳转到简单的柱状图中
     *
     * @param view
     */
    public void simpleColumn(View view) {
        startActivity(new Intent(this, SimpleColumnActivity.class).putExtra("title", "简单柱状图"));
    }

    /**
     * 跳转到一堆堆的柱状图中
     *
     * @param view
     */
    public void heapColumn(View view) {
        startActivity(new Intent(this, HeapColumnActivity.class).putExtra("title", "一堆堆的柱状图"));
    }

    /**
     * 成绩分数关系的柱状图中
     *
     * @param view
     */
    public void scoreColumn(View view) {
        startActivity(new Intent(this, ScoreColumnActivity.class).putExtra("title", "成绩-分数柱状图"));
    }

    /**
     * 三个班级各阶段成绩对比
     *
     * @param view
     */
    public void scoreHeapColumn(View view) {
        startActivity(new Intent(this, ScoreHeapColumnActivity.class).putExtra("title", "三个班级各阶段成绩对比柱状图"));
    }


}

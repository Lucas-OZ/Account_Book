package com.per.note.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.per.note.R;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    private LineChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**绘制折线图*/
    private void showLineChart() {
        chart.setDrawBorders(false);// 是否在折线图上显示边框，false不显示边框
        chart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
        chart.getAxisLeft().setEnabled(false);// 隐藏左边坐标轴
        chart.setDrawGridBackground(false); // 是否显示表格颜色
        chart.setDescription("");// 设置无图表描述
        chart.fitScreen();// 设置自适应屏幕
        // 设置间距
        chart.setExtraOffsets(20, 20, 20, 20);

        // X轴样式
        XAxis xAxis = chart.getXAxis();
        // 不显示表格线
        xAxis.setDrawGridLines(true);
        // 不显示文字
        xAxis.setDrawLabels(true);
        // 不显示x轴线
        xAxis.setDrawAxisLine(true);

        List<Entry> list = new ArrayList<Entry>();
        //显示的每一个点，参数中分别是对应的值和器所在的x轴的坐标
        Entry entry = new Entry(10.5f, 0);
        Entry entry1 = new Entry(12.5f, 1);
        Entry entry2 = new Entry(8.5f, 2);
        Entry entry3 = new Entry(9.5f, 3);
        list.add(entry3);
        list.add(entry2);
        list.add(entry1);
        list.add(entry);
        //第一个参数是数据点，第二个是图例说明
        LineDataSet dataSet = new LineDataSet(list, "");

        // 设置线条粗细
        dataSet.setLineWidth(3f);
        // 线条颜色
        dataSet.setColor(Color.parseColor("#000000"));
        // 设置表示数据点的小圆圈
        dataSet.setDrawCircles(false);//图标上的数据点不用小圆圈表示
        dataSet.setCircleSize(3f);//显示的圆形的大小
        dataSet.setCircleColor(Color.WHITE);//圆形的颜色

        //dataSet.setHighLightColor(Color.WHITE);//高亮的线的颜色

        dataSet.setDrawCubic(true);//设置允许曲线平滑
        dataSet.setCubicIntensity(0.2f);// 设置画曲线的平滑度

        //dataSet.setDrawFilled(true);//设置填充，在折线和X轴之间填充颜色，true允许填充
        //dataSet.setFillColor(Color.rgb(0, 255, 255));//设置填充的颜色
        //dataSet.setFillAlpha(85);

        // 设置曲线上数据的字体颜色
        dataSet.setValueTextColor(Color.parseColor("#000000"));

        List<Entry> list1 = new ArrayList<Entry>();
        //显示的每一个点，参数中分别是对应的值和器所在的x轴的坐标
        Entry ent = new Entry(7.5f, 0);
        Entry ent1 = new Entry(5.5f, 1);
        Entry ent2 = new Entry(4.5f, 2);
        Entry ent3 = new Entry(6.5f, 3);
        list1.add(ent3);
        list1.add(ent2);
        list1.add(ent1);
        list1.add(ent);
        LineDataSet dataSet2 = new LineDataSet(list1, "y显示的label");

        //X轴数据
        List<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(dataSet);
        dataSets.add(dataSet2);

        List<String> xlist = new ArrayList<String>();
        xlist.add("值0");
        xlist.add("值1");
        xlist.add("值2");
        xlist.add("值3");

        //包含了显示的数据和介绍  参数一：显示的x坐标的集合对象   参数二：包含了xy对应的数据以及显示内容的集合信息
        LineData data = new LineData(xlist, dataSets);

        chart.setData(data);
    }
    /**初始化方法*/
    private void init(){
        chart = (LineChart) findViewById(R.id.linechart);
    }
}

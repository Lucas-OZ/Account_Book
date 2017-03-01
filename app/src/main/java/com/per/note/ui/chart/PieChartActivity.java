package com.per.note.ui.chart;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.per.note.R;
import com.per.note.bean.Count;
import com.per.note.db.SqliteManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PieChartActivity extends AppCompatActivity{

    List<Count> countList = new ArrayList<>();

    private PieChart pieChart;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart = (PieChart) findViewById(R.id.piechart1);
        countList = queryCount();
        showPieChart();
    }

    public List<Count> queryCount(){
        SqliteManage.QueryResult result = SqliteManage.getInstance(this).query("count", null, null);
        SQLiteDatabase db = result.db;
        Cursor cursor = result.cursor;
        if(cursor.moveToFirst()){
            do{
                String count = cursor.getString(cursor.getColumnIndex("count"));
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                Count countItem = new Count();
                countItem.setCount(count);
                countItem.setNumber(money);
                countList.add(countItem);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return countList;
    }

    /**绘制饼状图*/
    private void showPieChart() {
        //设置饼中心是否透明(类似环状),只有设置为true，才能设置透明度
        //pieChart.setDrawHoleEnabled(true);//是否设置中空
        //pieChart.setHoleColorTransparent(false);
        //环内侧半径占整个半径的百分比
        //pieChart.setHoleRadius(70);//默认是百分之五十
        pieChart.setHoleRadius(0);//实心圆
        //设置半透明圈的半径
        pieChart.setTransparentCircleRadius(0);
        pieChart.setDescription("账户饼状图");
        pieChart.setDrawCenterText(true);//饼状图中间可以添加文字
        pieChart.setRotationEnabled(true);//是否可以手动旋转
        pieChart.setRotationAngle(90);//设置初始旋转角度
        pieChart.setUsePercentValues(true);//显示成百分比
        pieChart.setTouchEnabled(true);//可以触摸的
        pieChart.setCenterText("账户");// 饼状图中间的文字

        // 设置图的数据
        // xValues用来表示每个饼块上的内容
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < countList.size(); i++) {
            xValues.add((i+1)+"."+countList.get(i).getCount());
        }
        // yValues表示每个饼块上实际显示的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        // 将一个饼图分为4部分，4部分的数值比例分为14：14：34：38
//        float quarterly1 = 14f;
//        float quarterly2 = 14f;
//        float quarterly3 = 34f;
//        float quarterly4 = 38f;
//        yValues.add(new Entry(quarterly1, 0));
//        yValues.add(new Entry(quarterly2, 1));
//        yValues.add(new Entry(quarterly3, 2));
//        yValues.add(new Entry(quarterly4, 3));
        double totalMoney = 0;
        for (int i = 0; i < countList.size(); i++) {
            totalMoney += countList.get(i).getNumber();
        }
        for (int i = 0; i < countList.size(); i++) {
            float quarterly = (float) (countList.get(i).getNumber()/totalMoney*100);
            yValues.add(new Entry(quarterly,i));
        }

        // y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues,
                "我的账户");
        pieDataSet.setSliceSpace(0f);// 设置饼状图之间的距离
        // 饼图的颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
//        colors.add(Color.rgb(205, 205, 205));
//        colors.add(Color.rgb(114, 188, 223));
//        colors.add(Color.rgb(255, 123, 124));
//        colors.add(Color.rgb(57, 135, 200));
        for (int i = 0; i < countList.size(); i++) {
            colors.add(randomColor());
        }
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(xValues, pieDataSet);
        pieChart.setData(pieData);

        // 设置图例
        Legend mLegend = pieChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);// 最右边显示
        mLegend.setForm(Legend.LegendForm.LINE);// 设置比例图的形状，默认是方形
        pieChart.animateXY(1000, 1000);//设置动画


    }

    /**
     * 随机颜色
     */
    public Integer randomColor() {

        String r,g,b;
        Random random = new Random();
        r = Integer.toString(random.nextInt(256));
        g = Integer.toString(random.nextInt(256));
        b = Integer.toString(random.nextInt(256));

        return Color.rgb(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b));
    }
}

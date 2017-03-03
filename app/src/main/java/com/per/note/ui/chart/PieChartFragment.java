package com.per.note.ui.chart;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.per.note.R;
import com.per.note.bean.Count;
import com.per.note.bean.MsgYear;
import com.per.note.db.SqliteManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PieChartFragment extends Fragment{

    List<Count> countList;
    List<MsgYear.ClassMsg> classMsgList;

    private PieChart pieChart1;
    private PieChart pieChart2;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pie_chart);
//
//        countList = queryCount();
//        showPieChart();
//    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_pie_chart, null);
        initView(view);

        countList =   new ArrayList<Count>();
        countList = queryCount();

        classMsgList = new ArrayList<MsgYear.ClassMsg>();
        classMsgList = queryClass();

        return view;
    }

    private void initView(View view) {
        pieChart1 = (PieChart) view.findViewById(R.id.piechart1);
        pieChart2 = (PieChart) view.findViewById(R.id.piechart2);
    }

    public void onResume() {
        super.onResume();
        Log.i("-----","onResume");
        showPieChart1();
        showPieChart2();
    }

    public List<Count> queryCount(){
        SqliteManage.QueryResult result = SqliteManage.getInstance(getActivity()).query("count", null, null);
        SQLiteDatabase db = result.db;
        Cursor cursor = result.cursor;
        if(cursor.moveToFirst()){
            do{
                String count = cursor.getString(cursor.getColumnIndex("count"));
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                Count countItem = new Count();
                countItem.setCount(count);
                countItem.setNumber(money);
                Log.i("----","==="+count+"==="+money);
                countList.add(countItem);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return countList;
    }

    public List<MsgYear.ClassMsg> queryClass(){
        SqliteManage.QueryResult result = SqliteManage.getInstance(getActivity()).query("inout", "inout<0", null);
        SQLiteDatabase db = result.db;
        Cursor cursor = result.cursor;
        if(cursor.moveToFirst()){
            do{
                String classes = cursor.getString(cursor.getColumnIndex("class"));
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                MsgYear.ClassMsg countItem = new MsgYear().new ClassMsg();
                countItem.setClassName(classes);
                countItem.setMoney(money);
                classMsgList.add(countItem);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return classMsgList;
    }

    /**绘制账户饼状图*/
    private void showPieChart1() {

        //设置饼中心是否透明(类似环状),只有设置为true，才能设置透明度
        //pieChart.setDrawHoleEnabled(true);//是否设置中空
        //pieChart.setHoleColorTransparent(false);
        //环内侧半径占整个半径的百分比
        //pieChart.setHoleRadius(70);//默认是百分之五十

        pieChart1.setHoleRadius(0);//实心圆
        //设置半透明圈的半径
        pieChart1.setTransparentCircleRadius(0);
        pieChart1.setDescription("");
        pieChart1.setDrawCenterText(true);//饼状图中间可以添加文字
        pieChart1.setRotationEnabled(true);//是否可以手动旋转
        pieChart1.setRotationAngle(90);//设置初始旋转角度
        pieChart1.setUsePercentValues(true);//显示成百分比
        pieChart1.setTouchEnabled(true);//可以触摸的
        pieChart1.setCenterText("账户");// 饼状图中间的文字

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
//传入各个账户的钱数，在饼图中会自动转换成百分数显示
        for (int i = 0; i < countList.size(); i++) {
            float quarterly = (float) (countList.get(i).getNumber());
            yValues.add(new Entry(quarterly,i));
        }

        // y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "账户余额");
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
        pieChart1.setData(pieData);

        // 设置图例
        Legend mLegend = pieChart1.getLegend();
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);// 设置比例图在最右边显示
        mLegend.setForm(Legend.LegendForm.SQUARE);//设置比例图的形状为方形
        pieChart1.animateXY(1000, 1000);//设置动画
       // pieChart.invalidate();

    }

    /**绘制账户饼状图*/
    private void showPieChart2() {

        //设置饼中心是否透明(类似环状),只有设置为true，才能设置透明度
        //pieChart.setDrawHoleEnabled(true);//是否设置中空
        //pieChart.setHoleColorTransparent(false);
        //环内侧半径占整个半径的百分比
        //pieChart.setHoleRadius(70);//默认是百分之五十

        pieChart2.setHoleRadius(0);//实心圆
        //设置半透明圈的半径
        pieChart2.setTransparentCircleRadius(0);
        pieChart2.setDescription("");
        pieChart2.setDrawCenterText(true);//饼状图中间可以添加文字
        pieChart2.setRotationEnabled(true);//是否可以手动旋转
        pieChart2.setRotationAngle(90);//设置初始旋转角度
        pieChart2.setUsePercentValues(true);//显示成百分比
        pieChart2.setTouchEnabled(true);//可以触摸的
        pieChart2.setCenterText("分类");// 饼状图中间的文字

        // 设置图的数据
        // xValues用来表示每个饼块上的内容
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < classMsgList.size(); i++) {
            xValues.add((i+1)+"."+classMsgList.get(i).getClassName());
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
//传入各个账户的钱数，在饼图中会自动转换成百分数显示
        for (int i = 0; i < classMsgList.size(); i++) {
            float quarterly = (float) (classMsgList.get(i).getMoney());
            yValues.add(new Entry(quarterly,i));
        }

        // y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "支出分类");
        pieDataSet.setSliceSpace(0f);// 设置饼状图之间的距离
        // 饼图的颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
//        colors.add(Color.rgb(205, 205, 205));
//        colors.add(Color.rgb(114, 188, 223));
//        colors.add(Color.rgb(255, 123, 124));
//        colors.add(Color.rgb(57, 135, 200));
        for (int i = 0; i < classMsgList.size(); i++) {
            colors.add(randomColor());
        }
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(xValues, pieDataSet);
        pieChart2.setData(pieData);

        // 设置图例
        Legend mLegend = pieChart2.getLegend();
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);// 设置比例图在最右边显示
        mLegend.setForm(Legend.LegendForm.SQUARE);//设置比例图的形状为方形
        pieChart2.animateXY(1000, 1000);//设置动画
        // pieChart.invalidate();

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

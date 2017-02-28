package com.per.note.bean;

import android.database.Cursor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liu on 2017/2/27.
 */
public class MsgDay implements Serializable {
    private int day, month, year, week, inout;
    private long time;
    private double money;
    private String classes, count, other;
    private String[] weeks = new String[]{"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public MsgDay(Cursor cursor) {
        this.day = cursor.getInt(cursor.getColumnIndex("day"));//日期
        this.month = cursor.getInt(cursor.getColumnIndex("month"));//月份
        this.year = cursor.getInt(cursor.getColumnIndex("year"));//年份
        this.week = cursor.getInt(cursor.getColumnIndex("week"));//星期
        this.inout = cursor.getInt(cursor.getColumnIndex("inout"));//收入或支出
        this.time = cursor.getLong(cursor.getColumnIndex("time"));//时间
        this.money = cursor.getDouble(cursor.getColumnIndex("money"));//金额
        this.classes = cursor.getString(cursor.getColumnIndex("class"));//类别
        this.count = cursor.getString(cursor.getColumnIndex("count"));//账本
        this.other = cursor.getString(cursor.getColumnIndex("other"));//备注
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getInout() {
        return inout;
    }

    public void setInout(int inout) {
        this.inout = inout;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return year +
                "," + month +
                "," + day +
                "," + weeks[week - 1] +
                "," + (inout == 1 ? "收入" : "支出") +
                "," + simpleTime(time) +
                "," + money +
                "," + classes +
                "," + count +
                ", " + other;
    }

    private String simpleTime(Long lo) {
        SimpleDateFormat time = new SimpleDateFormat(/*"yyyy-MM-dd*/" hh:mm:ss");
        Date date = new Date(lo);
        return time.format(date);
    }
}

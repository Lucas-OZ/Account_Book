package com.per.note.bean;

import android.database.Cursor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 22762 on 2016/1/7.
 */
public class MsgDay implements Serializable {
    private int day, month, year, week, inout;
    private long time;
    private double money;
    private String classes, count, other;
    private String[] weeks = new String[]{"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public MsgDay(Cursor cursor) {
        this.day = cursor.getInt(cursor.getColumnIndex("day"));
        this.month = cursor.getInt(cursor.getColumnIndex("month"));
        this.year = cursor.getInt(cursor.getColumnIndex("year"));
        this.week = cursor.getInt(cursor.getColumnIndex("week"));
        this.inout = cursor.getInt(cursor.getColumnIndex("inout"));
        this.time = cursor.getLong(cursor.getColumnIndex("time"));
        this.money = cursor.getDouble(cursor.getColumnIndex("money"));
        this.classes = cursor.getString(cursor.getColumnIndex("class"));
        this.count = cursor.getString(cursor.getColumnIndex("count"));
        this.other = cursor.getString(cursor.getColumnIndex("other"));
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

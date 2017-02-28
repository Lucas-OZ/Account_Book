package com.per.note.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 2017/2/27.
 */
public class MsgMonth implements Serializable {
    private double totalin, totalout;
    private int year, month;
    private List<ClassMsg> classMsgList;
    private List<CountMsg> countMsgList;

    public MsgMonth() {
        classMsgList = new ArrayList<>();
        countMsgList = new ArrayList<>();
    }

    public class ClassMsg {
        public String className;
        public double money;
    }

    public class CountMsg {
        public String countName;
        public double money;
    }
    //总收入
    public double getTotalin() {
        return totalin;
    }

    public void setTotalin(double totalin) {
        this.totalin = totalin;
    }
    //总支出
    public double getTotalout() {
        return totalout;
    }

    public void setTotalout(double totalout) {
        this.totalout = totalout;
    }
    //年份
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    //月份
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    //类别信息列表
    public List<ClassMsg> getClassMsgList() {
        return classMsgList;
    }

    public void setClassMsgList(List<ClassMsg> classMsgList) {
        this.classMsgList = classMsgList;
    }
    //账本信息列表
    public List<CountMsg> getCountMsgList() {
        return countMsgList;
    }

    public void setCountMsgList(List<CountMsg> countMsgList) {
        this.countMsgList = countMsgList;
    }
}

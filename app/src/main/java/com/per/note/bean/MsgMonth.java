package com.per.note.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22762 on 2016/1/7.
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

    public double getTotalin() {
        return totalin;
    }

    public void setTotalin(double totalin) {
        this.totalin = totalin;
    }

    public double getTotalout() {
        return totalout;
    }

    public void setTotalout(double totalout) {
        this.totalout = totalout;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<ClassMsg> getClassMsgList() {
        return classMsgList;
    }

    public void setClassMsgList(List<ClassMsg> classMsgList) {
        this.classMsgList = classMsgList;
    }

    public List<CountMsg> getCountMsgList() {
        return countMsgList;
    }

    public void setCountMsgList(List<CountMsg> countMsgList) {
        this.countMsgList = countMsgList;
    }
}

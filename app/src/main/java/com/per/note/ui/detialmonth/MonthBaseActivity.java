package com.per.note.ui.detialmonth;

import android.os.Bundle;

import com.per.note.bean.MsgDay;
import com.per.note.bean.MsgMonth;
import com.per.note.ui.detialday.DayBaseActivity;
import com.per.note.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MonthBaseActivity extends DayBaseActivity {

    protected List<MsgMonth> mMsgMonthList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        mMsgMonthList = new ArrayList<>();
        for (MsgDay msgDay : mMsgDayList) {
            double money;
            if (msgDay.getInout() == -1) {
                money = -1 * msgDay.getMoney();
            } else {
                money = msgDay.getMoney();
            }
            boolean isExist = false;
            for (MsgMonth msgMonth : mMsgMonthList) {
                if (msgDay.getYear() == msgMonth.getYear() && msgDay.getMonth() == msgMonth.getMonth()) {
                    if (msgDay.getInout() == -1) {
                        msgMonth.setTotalout(msgMonth.getTotalout() + msgDay.getMoney());
                    } else {
                        msgMonth.setTotalin(msgMonth.getTotalin() + msgDay.getMoney());
                    }
                    boolean isExistCount = false;
                    boolean isExistClass = false;
                    for (MsgMonth.CountMsg countMsg : msgMonth.getCountMsgList()) {
                        if (msgDay.getCount().equals(countMsg.countName)) {
                            countMsg.money += money;
                            isExistCount = true;
                        }
                    }
                    for (MsgMonth.ClassMsg classMsg : msgMonth.getClassMsgList()) {
                        if (msgDay.getClasses().equals(classMsg.className)) {
                            classMsg.money += money;
                            isExistClass = true;
                        }
                    }
                    if (!isExistCount) {
                        MsgMonth.CountMsg countMsg = msgMonth.new CountMsg();
                        countMsg.money = money;
                        countMsg.countName = msgDay.getCount();
                        msgMonth.getCountMsgList().add(countMsg);
                    }
                    if (!isExistClass) {
                        MsgMonth.ClassMsg classMsg = msgMonth.new ClassMsg();
                        classMsg.money = money;
                        classMsg.className = msgDay.getClasses();
                        msgMonth.getClassMsgList().add(classMsg);
                    }
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                MsgMonth msgMonth = new MsgMonth();
                msgMonth.setYear(msgDay.getYear());
                msgMonth.setMonth(msgDay.getMonth());
                if (msgDay.getInout() == -1) {
                    msgMonth.setTotalout(msgDay.getMoney());
                } else {
                    msgMonth.setTotalin(msgDay.getMoney());
                }

                MsgMonth.CountMsg countMsg = msgMonth.new CountMsg();
                countMsg.money = money;
                countMsg.countName = msgDay.getCount();

                msgMonth.getCountMsgList().add(countMsg);
                //
                MsgMonth.ClassMsg classMsg = msgMonth.new ClassMsg();
                classMsg.money = money;
                classMsg.className = msgDay.getClasses();

                msgMonth.getClassMsgList().add(classMsg);
                mMsgMonthList.add(msgMonth);
            }
        }
    }
}
package com.per.note.ui.detialyear;

import android.os.Bundle;

import com.per.note.bean.MsgDay;
import com.per.note.bean.MsgYear;
import com.per.note.ui.detialday.DayBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class YearBaseActivity extends DayBaseActivity {

    protected List<MsgYear> mMsgYearList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        mMsgYearList = new ArrayList<>();
        for (MsgDay msgDay : mMsgDayList) {
            double money;
            if (msgDay.getInout() == -1) {
                money = -1 * msgDay.getMoney();
            } else {
                money = msgDay.getMoney();
            }
            boolean isExist = false;
            for (MsgYear msgYear : mMsgYearList) {
                if (msgDay.getYear() == msgYear.getYear()) {
                    if (msgDay.getInout() == -1) {
                        msgYear.setTotalout(msgYear.getTotalout() + msgDay.getMoney());
                    } else {
                        msgYear.setTotalin(msgYear.getTotalin() + msgDay.getMoney());
                    }
                    boolean isExistCount = false;
                    boolean isExistClass = false;
                    for (MsgYear.CountMsg countMsg : msgYear.getCountMsgList()) {
                        if (msgDay.getCount().equals(countMsg.countName)) {
                            countMsg.money += money;
                            isExistCount = true;
                        }
                    }
                    for (MsgYear.ClassMsg classMsg : msgYear.getClassMsgList()) {
                        if (msgDay.getClasses().equals(classMsg.className)) {
                            classMsg.money += money;
                            isExistClass = true;
                        }
                    }
                    if (!isExistCount) {
                        MsgYear.CountMsg countMsg = msgYear.new CountMsg();
                        countMsg.money = money;
                        countMsg.countName = msgDay.getCount();
                        msgYear.getCountMsgList().add(countMsg);
                    }
                    if (!isExistClass) {
                        MsgYear.ClassMsg classMsg = msgYear.new ClassMsg();
                        classMsg.money = money;
                        classMsg.className = msgDay.getClasses();
                        msgYear.getClassMsgList().add(classMsg);
                    }
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                MsgYear msgYear = new MsgYear();
                msgYear.setYear(msgDay.getYear());
                if (msgDay.getInout() == -1) {
                    msgYear.setTotalout(msgDay.getMoney());
                } else {
                    msgYear.setTotalin(msgDay.getMoney());
                }

                MsgYear.CountMsg countMsg = msgYear.new CountMsg();
                countMsg.money = money;
                countMsg.countName = msgDay.getCount();

                msgYear.getCountMsgList().add(countMsg);
                //
                MsgYear.ClassMsg classMsg = msgYear.new ClassMsg();
                classMsg.money = money;
                classMsg.className = msgDay.getClasses();

                msgYear.getClassMsgList().add(classMsg);
                mMsgYearList.add(msgYear);
            }
        }
    }
}
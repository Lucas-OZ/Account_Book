package com.per.note.utils;

import com.per.note.bean.MsgDay;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 22762 on 2016/1/23.
 */
public class OrderUtils {
    public static void orderDay(List list) {
        Comparator<MsgDay> mOrderDay = new Comparator<MsgDay>() {
            @Override
            public int compare(MsgDay lhs, MsgDay rhs) {
                if (lhs.getYear() > rhs.getYear()) {
                    return -1;
                } else if (lhs.getYear() < rhs.getYear()) {
                    return 1;
                } else {
                    if (lhs.getMonth() > rhs.getMonth()) {
                        return -1;
                    } else if (lhs.getMonth() < rhs.getMonth()) {
                        return 1;
                    } else {
                        if (lhs.getDay() > rhs.getDay()) {
                            return -1;
                        } else if (lhs.getDay() < rhs.getDay()) {
                            return 1;
                        } else {
                            if (lhs.getTime() > rhs.getTime()) {
                                return -1;
                            } else if (lhs.getTime() < rhs.getTime()) {
                                return 1;
                            }
                        }
                    }
                }
                return 0;
            }
        };
        Collections.sort(list, mOrderDay);
    }

    public static void orderMoney(List list) {
        Comparator<MsgDay> mOrderMoney = new Comparator<MsgDay>() {
            @Override
            public int compare(MsgDay lhs, MsgDay rhs) {
                if (lhs.getMoney() > rhs.getMoney()) {
                    return -1;
                } else if (lhs.getMoney() < rhs.getMoney()) {
                    return 1;
                }
                return 0;
            }
        };
        Collections.sort(list, mOrderMoney);
    }
}

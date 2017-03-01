package com.per.note.utils;

import android.content.Context;
import android.widget.TextView;

import com.per.note.R;

import java.text.DecimalFormat;

/**
 * 数据格式化工具类
 * 把double型数据格式化为两位小数
 */
public class FormatUtils {
    public static String format2d(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    public static void setText(TextView tv, double values, Context context) {
        if (values > 0) {
            tv.setTextColor(context.getResources().getColor(R.color.text_in_color));
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.text_out_color));
        }
        tv.setText(FormatUtils.format2d(values));
    }
}


package com.per.note.ui.load;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.per.note.R;
import com.per.note.bean.Version;
import com.per.note.db.SqliteManage;
import com.per.note.ui.main.MainActivity;
import com.per.note.utils.AppUtils;
import com.per.note.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoadingActivity extends Activity {
    public static final String VCONFNAME = "config.sys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startWelcome();
            }
        }, 3500);
    }

    private void initView() {
        TextView tv_t = (TextView) findViewById(R.id.load_time_t);
        TextView tv_b = (TextView) findViewById(R.id.load_time_b);
        String[] weeks = new String[]{"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance(Locale.CHINA);//创建一个日历对象
        tv_t.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
                + calendar.get(Calendar.DAY_OF_MONTH) + "日" + weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

        SqliteManage.QueryResult result = SqliteManage.getInstance(this).query("time", "time=?", new String[]{"firsttime"});
        if (result.cursor.getCount() != 0) {
            result.cursor.moveToFirst();
            long start = result.cursor.getLong(result.cursor.getColumnIndex("value"));
            long end = System.currentTimeMillis();
            int d = getCount(new Date(start), new Date(end));
            tv_b.setText("你的记账第" + d + "天");
        }
    }

    private int getCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
       /* toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);*/

        return 1 + (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }


    private void startWelcome() {
        Intent intent = new Intent();
        if (isNewVersion()) {
            intent.setClass(LoadingActivity.this, WelcomeActivity.class);
        } else {
            intent.setClass(LoadingActivity.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private boolean isNewVersion() {
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(this.getFilesDir() + File.separator + "config" + File.separator + VCONFNAME);
            if (!file.exists()) return true;
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            Version v = (Version) ois.readObject();
            LogUtils.i("LoadingActivity", v.versioncode + ":" + AppUtils.getVersionCode(this));
            if (v.versioncode < AppUtils.getVersionCode(this)) {
                return true;
            }
        } catch (FileNotFoundException e) {
            LogUtils.i("LoadingActivity", "error1");
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            LogUtils.i("LoadingActivity", "error2");
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.i("LoadingActivity", "error3");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LogUtils.i("LoadingActivity", "error4");
            e.printStackTrace();
        }
        return false;
    }
}

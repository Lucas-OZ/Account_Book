package com.per.note.ui.detialday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.per.note.R;
import com.per.note.bean.MsgDay;
import com.per.note.db.SqliteManage;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class DayBaseActivity extends AppCompatActivity {
    protected List<MsgDay> mMsgDayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initParentData();

    }

    protected void initParentData() {
        mMsgDayList = new ArrayList<>();
        SqliteManage.QueryResult result = SqliteManage.getInstance(this).query("inout", null, null);
        while (result.cursor.moveToNext()) {
            MsgDay msgDay = new MsgDay(
                    result.cursor
                    /*result.cursor.getInt(result.cursor.getColumnIndex("day")),
                    result.cursor.getInt(result.cursor.getColumnIndex("month")),
                    result.cursor.getInt(result.cursor.getColumnIndex("year")),
                    result.cursor.getInt(result.cursor.getColumnIndex("week")),
                    result.cursor.getInt(result.cursor.getColumnIndex("inout")),
                    result.cursor.getLong(result.cursor.getColumnIndex("time")),
                    result.cursor.getFloat(result.cursor.getColumnIndex("money")),
                    result.cursor.getString(result.cursor.getColumnIndex("class")),
                    result.cursor.getString(result.cursor.getColumnIndex("count")),
                    result.cursor.getString(result.cursor.getColumnIndex("other"))*/
            );
            mMsgDayList.add(msgDay);
        }
        ((ImageView) findViewById(R.id.dayactivity_ib_return)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (mMsgDayList.size() == 0) {
            Toast.makeText(this, "还没有记过账", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

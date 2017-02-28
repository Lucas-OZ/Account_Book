package com.per.note.ui.record;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.per.note.R;
import com.per.note.db.SqliteManage;
import com.per.note.ui.input.InputActivity;
import com.per.note.ui.main.BaseActivity;
import com.per.note.ui.selectclass.SelectClassActivity;
import com.per.note.ui.selectcount.SelectCountActivity;
import com.per.note.utils.SqliteUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RecordActivity extends BaseActivity {
    private Calendar mCalendar;
    private LinearLayout mLlMoney, mLlClass, mLlCount, mLlTime, mLlInOrOut;
    private TextView mTvMoney, mTvClass, mTvCount, mTvTime, mTvInOrOut;
    private ImageButton mBtOk;
    private EditText mOther;
    private int mYear, mMonth, mDay, mWeek;
    private int isOut = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_record);
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mCalendar = Calendar.getInstance(Locale.CHINA);//创建一个日历对象
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
    }

    private void initView() {
        ((TextView) findViewById(R.id.baseactivity_tv_title)).setText("添加收入/支出");
        mLlMoney = (LinearLayout) findViewById(R.id.recordin_ll_money);
        mLlClass = (LinearLayout) findViewById(R.id.record_ll_class);
        mLlCount = (LinearLayout) findViewById(R.id.record_ll_count);
        mLlTime = (LinearLayout) findViewById(R.id.record_ll_time);
        mLlInOrOut = (LinearLayout) findViewById(R.id.recordin_ll_inorout);
        mTvCount = (TextView) findViewById(R.id.record_tv_count);
        mTvMoney = (TextView) findViewById(R.id.record_tv_money);
        mTvClass = (TextView) findViewById(R.id.record_tv_class);
        mTvTime = (TextView) findViewById(R.id.record_tv_time);
        mTvTime.setText(mYear + "年" + mMonth + "月" + mDay + "日");
        mTvInOrOut = (TextView) findViewById(R.id.record_tv_inorout);
        mTvInOrOut.setText("支出");
        mBtOk = (ImageButton) findViewById(R.id.baseactivity_ib_ok);
        mOther = (EditText) findViewById(R.id.record_et_note);
        mTvMoney.setTextColor(getResources().getColor(R.color.text_out_color));
        mLlClass.setOnClickListener(mListener);
        mLlMoney.setOnClickListener(mListener);
        mLlCount.setOnClickListener(mListener);
        mLlTime.setOnClickListener(mListener);
        mLlInOrOut.setOnClickListener(mListener);
        mBtOk.setOnClickListener(mListener);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) return;
            Intent intent;
            switch (v.getId()) {
                case R.id.recordin_ll_money:
                    startActivityForResult(new Intent(RecordActivity.this, InputActivity.class), 0);
                    break;
                case R.id.record_ll_class:
                    intent = new Intent(RecordActivity.this, SelectClassActivity.class);
                    if (isOut == -1) {
                        intent.putExtra("flag", 1);
                        startActivityForResult(intent, 1);
                    } else {
                        if ("工资收入".equals(mTvClass.getText())) {
                            mTvClass.setText("其他收入");
                        } else {
                            mTvClass.setText("工资收入");
                        }
                    }
                    break;
                case R.id.record_ll_count:
                    intent = new Intent(RecordActivity.this, SelectCountActivity.class);
                    startActivityForResult(intent, 2);
                    break;
                case R.id.record_ll_time:
                    new DatePickerDialog(RecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            //月份默认从0开始
                            mYear = year;
                            mMonth = monthOfYear + 1;
                            mDay = dayOfMonth;
                            Calendar c = Calendar.getInstance(Locale.CHINA);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                c.setTime(format.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
                                mWeek = c.get(Calendar.DAY_OF_WEEK);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            mTvTime.setText(mYear + "年" + mMonth + "月" + mDay + "日");
                        }
                    },
                            mCalendar.get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.recordin_ll_inorout:
                    isOut = -1 * isOut;
                    if (isOut == -1) {
                        mTvInOrOut.setText("支出");
                        mTvClass.setText("早午晚餐");
                        mTvMoney.setTextColor(getResources().getColor(R.color.text_out_color));
                    } else {
                        mTvInOrOut.setText("收入");
                        mTvClass.setText("工资收入");
                        mTvMoney.setTextColor(getResources().getColor(R.color.text_in_color));
                    }
                    break;
                case R.id.baseactivity_ib_ok:
                    if (mTvCount.getText().toString().length() == 0) {
                        Toast.makeText(RecordActivity.this, "请选择账户！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ("00.00".equals(mTvMoney.getText().toString())) {
                        Toast.makeText(RecordActivity.this, "金额不能为0", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        insertData();
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void insertData() {
        ContentValues values = new ContentValues();
        values.put("year", mYear);
        values.put("month", mMonth);
        values.put("day", mDay);
        values.put("week", mWeek);
        values.put("money", mTvMoney.getText().toString());
        values.put("inout", isOut);
        values.put("class", mTvClass.getText().toString());
        values.put("count", mTvCount.getText().toString());
        values.put("time", System.currentTimeMillis() + "");
        values.put("other", mOther.getText().toString());
        SqliteManage.getInstance(this).insertItem("inout", values);

        SqliteUtils.updata(RecordActivity.this, mTvCount.getText().toString(),
                isOut * Double.parseDouble(mTvMoney.getText().toString()));
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    String msgmoney = data.getStringExtra("msgmoney");
                    if (msgmoney != null && msgmoney.length() != 0)
                        mTvMoney.setText(msgmoney);
                    break;
                case 1:
                    mTvClass.setText(data.getStringExtra("msgclass"));
                    break;
                case 2:
                    mTvCount.setText(data.getStringExtra("msgcount"));
                    break;
                default:
                    break;
            }
        }
    }

}